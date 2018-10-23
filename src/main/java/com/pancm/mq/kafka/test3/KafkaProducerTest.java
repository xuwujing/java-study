package com.pancm.mq.kafka.test3;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.alibaba.fastjson.JSONObject;

/**
 * 
* Title: KafkaProducerTest
* Description: kafka生产者的消息测试
* Version:1.0.0  
* @author pancm
* @date 2018年1月9日
 */
public class KafkaProducerTest implements Runnable {

	private final KafkaProducer<String, String> producer;
	private final String topic;
	private int k=10;
   private final String servers="master:9092,slave1:9092,slave2:9092";
	/**
	 * @param topic 消息名称
	 * @param
	 */
	public KafkaProducerTest(String topicName) {
		Properties props = new Properties();
		props.put("bootstrap.servers", servers);
		//acks=0：如果设置为0，生产者不会等待kafka的响应。
		//acks=1：这个配置意味着kafka会把这条消息写到本地日志文件中，但是不会等待集群中其他机器的成功响应。
		//acks=all：这个配置意味着leader会等待所有的follower同步完成。这个确保消息不会丢失，除非kafka集群中所有机器挂掉。这是最强的可用性保证。
		props.put("acks", "all");
		//配置为大于0的值的话，客户端会在消息发送失败时重新发送。
		props.put("retries", 0);
		//当多条消息需要发送到同一个分区时，生产者会尝试合并网络请求。这会提高client和生产者的效率
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", StringSerializer.class.getName());
		props.put("value.serializer", StringSerializer.class.getName());
		this.producer = new KafkaProducer<String, String>(props);
		this.topic = topicName;
	}

	@Override
	public void run() {
		int messageNo = 0;
		long k=0L;
		try {
			while (true) {
				JSONObject json=new JSONObject();
				k=239386111508899430L+messageNo;
				String messageStr="INSERT INTO   MT_TASK_HH  ( ECID , PTMSGID ,  USERID ,  DTTYPE ,  "
						+ "MSGTYPE ,  USERUID ,  SPMSGID ,  ERRORCODE ,  RECVMTTIME ,  SENDTIME ,  RECVTIME ,  "
						+ "PHONE ,  SPGATESEND ,  SPNUMBER ,  MOBILEAREA ,  MOBILETYPE ,  MOBILECOUNTRY ,  "
						+ "NEXTGATETYPE ,  GATEIDBIND ,  SPGATEBIND ,  CPNOBIND ,  CPNO ,  ORDERCPNO ,  PROTYPE ,  "
						+ "RCHGTYPE ,  SVRTYPE ,  FEEFLAG ,  RETFLAG ,  PASSTHROUGH ,  JTYPE ,  SENDSTATUS ,  "
						+ "SENDLEVEL ,  SENDRESULT ,  RESENDCNT ,  TPUDHI ,  TPPID ,  PKTOTAL ,  PKNUMBER ,  MSGFMT ,"
						+ "  LONGMSGSEQ ,  SENDERRCODE ,  GATEIDSEND ,  SPID ,  PACKNUM ,  PACKPOS ,  NETERRCNT , "
						+ " ERRRESENDCNT ,  ERRORCODE2 ,  RPTEXFLAG ,  SENDFLAG ,  SUPPSNDCNT ,  SENDRPTTIME ,  "
						+ "TRANSMTTIME ,  MTSUBMITTIME ,  TRANSRPTTIME ,  MTSENDTIME ,  SUBMITTIME ,  DONETIME , "
						+ " PUSHRPTTIME ,  PRETRANSMTTM ,  ENDTRANSRPTTM ,  SUBMITDATE ,  DONEDATE ,  JPTCODE ,  "
						+ "PTCODE ,  LOGINUID ,  DESTUID ,  USERMSGID ,  SUPPMSGID ,  PREGATENO ,  LOCALGATENO , "
						+ " NEXTGATENO ,  SRCGATENO ,  NETWORKCODE ,  NETWORKID ,  CHARGETYPE ,  PRICE ,  CUSTID , "
						+ " USEREXDATA ,  USERSVRTYPE ,  SEQID ,  AGENTLOGINUID ,  MSGSRCIP ,  TMPLID ,  SPTMPLID ,"
						+ "  MSGTYPE1 ,  ACCTTYPE ,  PTRCHGID ,  CHARGOBJ ,  CHGRADE ,  VALIDTM ,  ERRORCODE3 , "
						+ " ERRORCODE4 ,  FIRSTDOWNTM ,  ENDDOWNTM ,  RDNRPTOKTM ,  RDNTRANSRPTTM ,  RECVRDNRPTTM , "
						+ " MESSAGE ) VALUES ('101034', '"+k+"', 'qian01', '1', '1', "
						+ "'100032', '2393861115088994305', 'DELIVRD', '2018-02-02 14:11:16', "
						+ "'2018-02-02 14:09:49', '2018-02-02 14:09:49', '13475676880', '2017022701', "
						+ "'20170227011', '30', '0', '86', '0', '901', '2017022701', '1', '', '', '0', "
						+ "'1', '', '2', '1', '0', '0', '0', '3', '0', '0', '0', '0', '1', '1', '0', '0', '0', "
						+ "'901', 'qianzi', '1', '1', '0', '0', 'DELIVRD', '0', '0', '0', '2018-02-02 14:11:16', "
						+ "'2018-02-02 14:09:49', '2018-02-02 14:09:49', '2018-02-02 14:11:16', "
						+ "'2018-02-02 14:09:49', '2018-02-02 14:11:49', '2018-02-02 14:11:49', "
						+ "'2018-02-02 14:11:16', '2018-02-02 14:11:16', '2018-02-02 14:09:49', '1802021411', "
						+ "'1802021411', '1050973', '16', '105692', '104473', '0', '0', '0', '2397', '0', '0', "
						+ "'0', '0', '0', '0.000000', '', '', '', '128', '105692', '192.169.1.32:2232', '0', '',"
						+ " '0', '0', '0', '0', '0', '4', '', '', '2000-01-01 00:00:00', '2000-01-01 00:00:00', "
						+ "'2000-01-01 00:00:00', '2000-01-01 00:00:00', '2000-01-01 00:00:00', 'AGEAcwBkAGEAcwBkAGEAcwBkAGEAcwBkAGEAcwBkAFsAbwBlAGUAcgBd');";
				
//				String messageStr="222222222222222222222222222222222222222222222222222222222222222222";
				json.put("SQL", messageStr);
				json.put("TIME", System.currentTimeMillis());
				json.put("PTMSGID", messageNo);
				
				producer.send(new ProducerRecord<String, String>(topic, "Message"+messageNo, json.toJSONString()));
				messageNo++;
				//生产了100条就打印
				if(messageNo%10000==0){
//					System.out.println("Send:" + messageStr);
				}
				//生产100条就退出
				if(messageNo%500000==0){
					System.out.println(topic+"成功发送了"+messageNo+"条");
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
	
	public static void main(String args[]) {
		KafkaProducerTest test = new KafkaProducerTest("1002_INSERT");
		KafkaProducerTest test1 = new KafkaProducerTest("1005_INSERT");
		KafkaProducerTest test2 = new KafkaProducerTest("1001_INSERT");
		KafkaProducerTest testd = new KafkaProducerTest("TEST_INSERT1");
		Thread thread = new Thread(test);
		Thread thread1 = new Thread(test1);
		Thread thread2 = new Thread(test2);
		Thread threadD = new Thread(testd);
		thread.start();
//		Utils.sleep(100);
//		thread1.start();
//		Utils.sleep(100);
//		thread2.start();
//		threadD.start();
	}
	

}