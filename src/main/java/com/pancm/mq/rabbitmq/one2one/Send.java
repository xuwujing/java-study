package com.pancm.mq.rabbitmq.one2one;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
//生产者 ( Producer:数据的发送方)
//单发送单接收 //单发送单接收  Send.java和Recv.java类
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {
    
  private final static String QUEUE_NAME = "header_exchange"; //消息队列名

  public static void main(String[] argv) throws Exception {
           Map map=new HashMap();  
           map.put("aa", 11);
           map.put("bb", 22);
           map.put("cc", 33);
           map.put("dd", 44);
           map.put("ff", 1);
	 System.out.println("你好啊！");
      //创建连接连接到MabbitMQ 
    ConnectionFactory factory = new ConnectionFactory();
    // 设置MabbitMQ所在主机ip或者主机名  
    // factory.setHost("localhost");
    //factory.setHost("127.0.0.1");
    
  factory.setUri("amqp://guest:guest@172.26.129.3:5672");//获取url
    // 创建一个连接  
    Connection connection = factory.newConnection();
    // 创建一个频道 
    Channel channel = connection.createChannel();
    // 指定一个队列  
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    //发送的消息
    String message = JSON.toJSONString(map); 
    // 往队列中发出一条消息 
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes()); //发送
    System.out.println(" [x] Sent '" + message + "'");
    // 关闭频道和连接  
    channel.close();
    connection.close();
    
    
    
  }
}