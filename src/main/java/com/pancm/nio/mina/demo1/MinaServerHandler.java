package com.pancm.nio.mina.demo1;

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
	public void sessionCreated(IoSession session) throws Exception{ 
		logger.info("服务端与客户端创建连接...");
		super.sessionCreated(session);
	}
    
	
	@Override
	public void sessionOpened(IoSession session) throws Exception{ 
		logger.info("服务端与客户端连接打开...");
		 super.sessionOpened(session);
	}
    
	@Override
	public void messageReceived(IoSession session,Object message) throws Exception{
		 String msg=message.toString();
		 logger.info("服务端收到的数据为:"+msg);
		 if("bye".equals(msg)){  //服务端断开的条件
			 session.close();
		 }
		 Date date=new Date();
		 String mg="Come on:"+date;
		 logger.info("服务端返回给客户端的数据:"+mg);
		 session.write(mg); //返回给服务端数据
	}

	 @Override
	    public void messageSent(IoSession session, Object message) throws Exception {
		 	logger.info("服务端发送数据 = "+message);
//		 	session.close(); //发送成功后主动断开与客户端的连接 实现短连接
	        logger.info("服务端发送信息成功...");
	    }

	    @Override
	    public void sessionClosed(IoSession session) throws Exception {
	    	 logger.info("断开连接");
	    	super.sessionClosed(session);
	    }

	    @Override
	    public void sessionIdle(IoSession session, IdleStatus status)
	            throws Exception {
	        logger.info("服务端进入空闲状态...");
	        super.sessionIdle(session, status);
	    }

	    @Override
	    public void exceptionCaught(IoSession session, Throwable cause)
	            throws Exception {
	        logger.error("服务端发送异常...", cause);
	        super.exceptionCaught(session, cause);
	    }
	
	
}
