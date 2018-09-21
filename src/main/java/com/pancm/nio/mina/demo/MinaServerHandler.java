package com.pancm.nio.mina.demo;

import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;


/**
 * @author ZERO:
 * @version 2017-3-27 下午3:59:33
 * 业务逻辑实现
 */
public class MinaServerHandler extends IoHandlerAdapter {
	public static Logger logger=Logger.getLogger(MinaServerHandler.class);
	
	@Override
	public void sessionCreated(IoSession iosession) throws Exception{ 
		InetSocketAddress sa=(InetSocketAddress)iosession.getRemoteAddress();
		String address=sa.getAddress().getHostAddress(); //访问的ip
		logger.info("服务端与客户端创建连接..."+"访问的IP:"+address);
	}
    
	
	@Override
	public void sessionOpened(IoSession iosession) throws Exception{ 
		logger.info("服务端与客户端连接打开...");
	}
    
	@Override
	public void messageReceived(IoSession session,Object message) throws Exception{
		 String msg=message.toString();
		 logger.info("服务端收到的数据为:"+msg);
		 if("bye".equals(msg)){  //服务端断开的条件
			 session.close();
		 }
		 Date date=new Date();
		 session.write(date); //返回给服务端数据
	}

	 @Override
	    public void messageSent(IoSession session, Object message) throws Exception {
//	        session.close(); //发送成功后主动断开与客户端的连接
	        logger.info("服务端发送信息成功...");
	    }

	    @Override
	    public void sessionClosed(IoSession session) throws Exception {
	    }

	    @Override
	    public void sessionIdle(IoSession session, IdleStatus status)
	            throws Exception {
	        logger.info("服务端进入空闲状态...");
	    }

	    @Override
	    public void exceptionCaught(IoSession session, Throwable cause)
	            throws Exception {
	        logger.error("服务端发送异常...", cause);
	    }
	
	
}
