package com.pancm.nio.mina.demo1;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author ZERO
 * @version 2017-3-27 下午5:59:54
 * mina 客户端
 */
public class MinaClient {
    private static Logger logger = Logger.getLogger(MinaClient.class);
    private static String HOST = "127.0.0.1";
    private static int PORT = 3305;
    
   /* 
    * 测试服务端与客户端程序！
    a. 启动服务端，然后再启动客户端（客户端发送的消息是"why are you so diao "）
    b. 服务端接收消息并处理成功;
    */
    public static void main(String[] args) {
        // 创建一个非阻塞的客户端程序
        IoConnector connector = new NioSocketConnector();
        // 设置链接超时时间
        connector.setConnectTimeout(30000);
        ProtocolCodecFilter pf=new ProtocolCodecFilter((new MyTextLineCodecFactory(Charset .forName("utf-8"), "\r\n")));
        // 添加过滤器
        connector.getFilterChain().addLast("codec", pf);
        // 添加业务逻辑处理器类
        connector.setHandler(new MinaClientHandler());
        IoSession session = null;
        try {
            ConnectFuture future = connector.connect(new InetSocketAddress(
                    HOST, PORT));// 创建连接
            future.awaitUninterruptibly();// 等待连接创建完成
            session = future.getSession();// 获得session
            String msg="hello \r\n";
            session.write(msg);// 发送消息
            logger.info("客户端与服务端建立连接成功...发送的消息为:"+msg);
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("客户端链接异常...", e);
        }
        session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
        connector.dispose();
    }
}
