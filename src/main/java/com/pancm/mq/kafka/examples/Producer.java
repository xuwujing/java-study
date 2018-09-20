package com.pancm.mq.kafka.examples;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * 
* Title: Producer
* Description: kafka生产者 由于生产消息 
* Version:1.0.0  
* @author pancm
* @date 2017年12月29日
 */
public class Producer extends Thread {

	private final KafkaProducer<String, String> producer;
	private final String topic;
   
	/**
	 * 
	 * @param kafkaStr   kafka地址
	 * @param topic      消息名称
	 * @param
	 */
	public Producer(String kafkaStr, String topic) {
		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaStr);
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

	@Override
	public void run() {
		int messageNo = 1;
		try {
			while (true) {
				String messageStr = "Message_" + messageNo;
				//生产了100条就打印
				if(messageNo%100==0){
					System.out.println("Send:" + messageStr);
				}
				//生产1000条就退出
//				if(messageNo%1000==0){
//					break;
//				}
				producer.send(new ProducerRecord<String, String>(topic, "Message", messageStr));
				messageNo++;
				sleep(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}

}
