package com.pancm.mq.kafka.test3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;


/**
 * 
* Title: KafkaConsumerTest
* Description: 
* kafka消费者 demo
* 手动提交测试 指定分区和offset
* Version:1.0.0  
* @author pancm
* @date 2018年1月26日
 */
public class KafkaConsumerTest3 implements Runnable {

	private KafkaConsumer<String, String> consumer;
	private ConsumerRecords<String, String> msgList;
	private  String topic;
	private static final String GROUPID = "groupF";
	
	/**用于存放 分区所对应的offset */
	private  ConcurrentHashMap<Integer, Long> map=new ConcurrentHashMap<Integer, Long>();
	
	/**分区编号 */
	private  int partId=0;
	/**分区个数 */
	private  int partSize=1;
	private  long offset=-1L;
	
	/**初始化标志*/
    private boolean flag = true;
	
	public KafkaConsumerTest3(String topicName) {
		this.topic = topicName;
		init();
	}
	
	@Override
	public void run() {
		System.out.println("---------开始消费---------");
		int messageNo = 1;
		List<String> list=new ArrayList<String>();
		List<Long> list2=new ArrayList<Long>();
		TopicPartition p = new TopicPartition(topic,0);
		consumer.assign(Arrays.asList(p));
		//指定分区和offset进行消费
		consumer.seek(p, 0);
		try {
			for (;;) {
					 msgList = consumer.poll(100);
					if(null!=msgList&&msgList.count()>0){
					int tmpPartId=0; 
					for (ConsumerRecord<String, String> record : msgList) {
						if(messageNo%10==0){
							System.out.println(messageNo+"=======receive: partId ="+tmpPartId +", key = " + record.key() + ", value = " + record.value()+" offset==="+record.offset());
						}
					}
					  // 手动提交
//					   consumer.commitSync();
				}else{	
					Thread.sleep(1000);
					System.out.println("...");
				}
			}		
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			consumer.close();
		}
	}
	
	private void saveOffset(int partId,long offset) {
		 map.put(partId, offset);
	}
	
	
	private long getOffset(int partId) {
		return map.containsKey(partId)?map.get(partId):offset;
	}
	
	private void init() {
		Properties props = new Properties();
		//kafka消费的的地址
		props.put("bootstrap.servers", "master:9092,slave1:9092,slave2:9092");
		//组名 不同组名可以重复消费
		props.put("group.id", GROUPID);
		//是否自动提交
		props.put("enable.auto.commit", "false");
		//超时时间
		props.put("session.timeout.ms", "30000");
		//一次最大拉取的条数
		props.put("max.poll.records", 10);
//		earliest当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费 
//		latest 
//		当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据 
//		none 
//		topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
		props.put("auto.offset.reset", "earliest");
		//序列化
		props.put("key.deserializer", StringDeserializer.class.getName());
		props.put("value.deserializer", StringDeserializer.class.getName());
		this.consumer = new KafkaConsumer<String, String>(props);
		//订阅主题列表topic
//		this.consumer.subscribe(Arrays.asList(topic));
		
		if(consumer.partitionsFor(topic)!=null){
			this.partSize = consumer.partitionsFor(topic).size();
        }
		
		System.out.println("初始化!");
	}
	
	
   
	public static void main(String args[]) {
		KafkaConsumerTest3 test1 = new KafkaConsumerTest3("TEST_INSERT");
		Thread thread1 = new Thread(test1);
		thread1.start();
	}
}
