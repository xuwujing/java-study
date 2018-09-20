package com.pancm.mq.kafka.examples;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class DataProducer {
	private static Random random = new Random(93285);
	private static Producer<String, String> producer;

	public static void main(String args[]) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.125.172:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
		int count = 0;
		long startTime = System.nanoTime(); // 获取开始时间
		int tdata = 0;
		double hdata = 0.0;
		while (true) {
			int choise = 0 + random.nextInt(10000);
			switch (choise) {
			case 0:
				tdata = createRandom(-30, -20);
				hdata = createRandom(0.0, 4.9);
				break;
			case 9999:
				tdata = createRandom(60, 70);
				hdata = createRandom(96.0, 100.0);
				break;
			default:
				tdata = createRandom(-19, 59);
				hdata = createRandom(5.0, 95.9);
				break;
			}
			producer.send(new ProducerRecord<String, String>(args[0], "temper:" + tdata + "," + "humi:" + hdata));
			long endTime = System.nanoTime();
			count++;
			int a = (int) ((endTime - startTime) * Math.pow(10, -9));
			if (a == 1) {
				System.out.println(args[0] + "每秒发送：" + count + "条数据");
				count = 0;
				startTime = System.nanoTime();
			}
		}

	}

	private static int createRandom(int min, int max) {
		return min + random.nextInt(max - min);
	}

	private static double createRandom(double min, double max) {
		if (min == 0) {
			return max - random.nextDouble();
		}
		return max - random.nextDouble() * min;

	}

}
