package com.pancm.nio.mina.demo1;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;






/**
 * @author ZERO:
 * @version 2017-3-28 上午10:20:11
 * 创建服务端
 */
public class MinaServer {
		//记录日志
	public static Logger logger=Logger.getLogger(MinaServer.class);
	private static int PORT=3305;
	
	/* 启动此类 提示服务端运行成功后
	 * Windows 命令 输入  telnet 127.0.0.1 3305
	 * 然后输入消息  message
	 * 消息为bye的时候关闭连接
	 * */
	public static void main(String[] args) {
			new MinaServer().start();
	}
	
	public void start() {
		 IoAcceptor ia=null;
	       try{
	    	   //创建一个非堵塞的server端Socket
	    	  ia=new NioSocketAcceptor();
	    	  //创建 自定义协议编码解码过滤器ProtocolCodecFilter
	    	  ProtocolCodecFilter pf=new ProtocolCodecFilter((new MyTextLineCodecFactory(Charset .forName("utf-8"), "\r\n")));
	    	  //设置端口
	    	  InetSocketAddress pt=new InetSocketAddress(PORT);
	        	// 设置过滤器（使用Mina提供的文本换行符编解码器）
	    	  ia.getFilterChain().addLast("codec", pf);
	    	  //设置读取数据的缓存区大小
	    	  ia.getSessionConfig().setReadBufferSize(2048);
	    	  //读写通道10秒内无操作进入空闲状态
	    	  ia.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	    	  //绑定逻辑处理器
	    	  ia.setHandler(new MinaServerHandler());
	    	  //绑定端口
	    	  ia.bind(pt);
	    	  logger.info("服务端启动成功...端口号为:"+PORT);
	    	   
	       }catch(Exception e){
	    	   logger.error("服务器的异常..."+e);
	    	   e.printStackTrace();
	       }
		
	}
	
}
