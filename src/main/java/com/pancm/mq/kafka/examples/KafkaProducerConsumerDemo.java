package com.pancm.mq.kafka.examples;

public class KafkaProducerConsumerDemo {

	public static final String KAFKASTR = "master:9092";

	public static void main(String[] args) {
		new Producer(KAFKASTR, "pcm_test1").start(); // args[0] 为要发送的 topic
//		new Consumer(KAFKASTR, "pcm_test1").start(); // args[0] 为要接收的 topic
	}
}