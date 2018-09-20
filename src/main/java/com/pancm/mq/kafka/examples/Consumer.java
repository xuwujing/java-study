package com.pancm.mq.kafka.examples;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * 
* Title: Consumer
* Description: kafka消费者 
* Version:1.0.0  
* @author pancm
* @date 2017年12月29日
 */
public class Consumer extends Thread {

	private final KafkaConsumer<String, String> consumer;
	private final String topic;
	private static final String GROUPID = "test-consumer-group";

	public Consumer(String kafkaStr, String topic) {
		Properties props = new Properties();
		props.put("bootstrap.servers", kafkaStr);
		props.put("group.id", GROUPID);
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", StringDeserializer.class.getName());
		props.put("value.deserializer", StringDeserializer.class.getName());
		this.consumer = new KafkaConsumer<String, String>(props);
		this.topic = topic;
	}

	@Override
	public void run() {
		this.consumer.subscribe(Arrays.asList(topic));
		int messageNo = 1;
		System.out.println("消费开始---------");
		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					//消费100条就打印 
					//打印的数据不一定是这个规律的
					if(messageNo%100==0){
						System.out.println("receive: key = " + record.key() + ", value = " + record.value());
					}
				}
				//当消费了1000条就退出
				if(messageNo%1000==0){
					break;
				}
				messageNo++;
			}
		} finally {
			consumer.close();
		}
	}

}
