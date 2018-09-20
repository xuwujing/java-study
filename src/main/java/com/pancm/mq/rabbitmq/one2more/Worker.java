package com.pancm.mq.rabbitmq.one2more;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
  //消费者 (Consumer:数据的接收方)
 //单发送多接收  Worker.java和NewTask.java
public class Worker {

  private static final String TASK_QUEUE_NAME = "task_queue";
//  private static final String TASK_QUEUE_NAME = "tsk.hybris.productbrand.tsk";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    //factory.setHost("localhost");
  	factory.setUri("amqp://guest:guest@172.26.129.3:5672");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);//queue的持久化需要在声明时指定durable=True
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    //保证在接收端一个消息没有处理完时不会接收另一个消息
   channel.basicQos(1);
  //  channel.basicQos(0, 1, false); //这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message。换句话说，在接收到该Consumer的ack前，他它不会将新的Message分发给它。
    
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    
    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      
      System.out.println(" [x] Received '" + message + "'");
      doWork(message);
      System.out.println(" [x] Done");

      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }         
  }
  
  private static void doWork(String task) throws InterruptedException {
    for (char ch: task.toCharArray()) {
      if (ch == '.') {
		Thread.sleep(1000);
	}
    }
  }
}