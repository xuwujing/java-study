package com.pancm.mq.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * 
* Title: kafkaTest
* Description:
* kafka测试 
* Version:1.0.0  
* @author pancm
* @date 2018年1月11日
 */
public class KafkaProducerTest {
	
	private static KafkaProducer<String, String> producer;
	private final String topic;
	private int k=10;
	
	/**
	 * @param topic      消息名称
	 * @param
	 */
	public KafkaProducerTest(String topic) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "master:9092,slave1:9092,slave2:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", StringSerializer.class.getName());
		props.put("value.serializer", StringSerializer.class.getName());
		this.producer = new KafkaProducer<String, String>(props);
		this.topic = topic;
	}
	
	
	private  void start(){
		int messageNo = 0;
		try {
			while (true) {
				String messageStr = "insert into t_user(name,age) values ('李四',"+messageNo*10+1+")" ;
//				StringBuffer sb=new StringBuffer();
//				for(int i=1;i<=k;i++){
//					int count=k*messageNo+i;
//					messageStr="insert into t_user(id,name,age) values ("+count+",'李四',"+count+10+")" ;
//					sb.append(messageStr);
//					sb.append(";");
//				}
//				sb.deleteCharAt(sb.lastIndexOf(";"));
//				producer.send(new ProducerRecord<String, String>(topic, "Message", sb.toString()));
				producer.send(new ProducerRecord<String, String>(topic, "Message", messageStr));
				messageNo++;
				//生产了100条就打印
				if(messageNo%100==0){
					System.out.println("Send:" + messageStr);
				}
				//生产100条就退出
				if(messageNo%100==0){
					System.out.println("成功发送了"+messageNo+"条");
					break;
				}
//				Utils.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
	
	
	public static void main(String[] args) {
		KafkaProducerTest test =new KafkaProducerTest("INSERT_TOPIC11");
		test.start();
	}

}
