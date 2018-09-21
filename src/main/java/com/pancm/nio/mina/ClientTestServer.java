package com.pancm.nio.mina;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.pancm.nio.mina.demo.MinaClientHandler;


/**
 * @author ZERO
 * @Data 2017-5-12 上午10:25:57
 * @Description 
 */
public class ClientTestServer {  
    
    public IoConnector creatClient(){  
        IoConnector connector=new NioSocketConnector();   
        connector.setConnectTimeoutMillis(30000);   
        connector.getFilterChain().addLast("codec",   
        new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));  
        connector.setHandler(new MinaClientHandler());  
        return connector;  
    }  
    public IoSession getIOSession(IoConnector connector){  
        ConnectFuture future = connector.connect(new InetSocketAddress("192.168.2.55", 1255));   
        // 等待是否连接成功，相当于是转异步执行为同步执行。   
        future.awaitUninterruptibly();   
        // 连接成功后获取会话对象。 如果没有上面的等待， 由于connect()方法是异步的， session可能会无法获取。   
        IoSession session = null;  
        try{  
            session = future.getSession();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return session;  
    }  
    public void sendMsg(IoSession session,String msg){  
    	
            HashMap<String,String> sy=new HashMap<String, String>();           
              sy.put("channel", "android");
              sy.put("deviceId", "12-ab");
              sy.put("device", "1456");
              sy.put("appVersion", "Version 1.5.1");
              sy.put("account", "This is test message");
            
              sy.put("ss","client_bind");
         //     logger.info("SentBody:"+sy);
           //   session.setAttribute("account","hello world");
              session.write(sy);// 发送消息 
    }  
    public static void main(String[] args) {   
        for(int i=0;i<4000;i++){  
            ClientTestServer  client = new ClientTestServer();  
            IoConnector connector = client.creatClient();  
            IoSession session = client.getIOSession(connector);  
            client.sendMsg(session,Arrays.toString(new byte[1000])+":"+System.currentTimeMillis());  
        }  
  
    }  

    }  
