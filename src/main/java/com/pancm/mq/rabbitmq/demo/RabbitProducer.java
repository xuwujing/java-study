package com.pancm.mq.rabbitmq.demo;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//生产者
public class RabbitProducer {
	private final static String QUEUE_NAME = "RabbitMQ_Hello"; //消息队列名

	  public static void main(String[] argv) throws Exception {
	     //创建连接连接到RabbitMQ 
	    ConnectionFactory factory = new ConnectionFactory();
	    // 设置ip
	    factory.setHost("127.0.0.1");
	    /*   //设置端口
	    factory.setPort(15672);
	    //设置用户名
	    factory.setUsername("guest");
	    //设置密码
	    factory.setPassword("guest");
	   //设置url(包括ip、端口、用户名、密码)
	    factory.setUri("amqp://guest:guest@localhost:15672");
	  */	
	    // 创建一个连接  
	    Connection connection = factory.newConnection();
	    // 创建一个频道 
	    Channel channel = connection.createChannel();
	    // 指定一个队列  
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    Map<String,Object> map=new HashMap<String,Object>();  
	      map.put("java", "hello");
	      map.put("RabbitMQ", "Hello");
	    //发送的消息
	    String message = JSON.toJSONString(map); 
	    // 往队列中发出一条消息 
	    channel.basicPublish("", QUEUE_NAME, null, message.getBytes()); 
	    System.out.println(" [x] Sent '" + message + "'");
	    // 关闭频道和连接  
	    channel.close();
	    connection.close();   
	  }
}
