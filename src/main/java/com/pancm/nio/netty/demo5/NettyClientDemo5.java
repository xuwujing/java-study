package com.pancm.nio.netty.demo5;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 
* Title: NettyClientDemo5
* Description: Netty客户端  心跳测试
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyClientDemo5 {
	  public static String host = "127.0.0.1";  //ip地址
	    public static int port = 5678;			//端口
	    /// 通过nio方式来接收连接和处理连接   
	    private static EventLoopGroup group = new NioEventLoopGroup(); 
	    private static  Bootstrap b = new Bootstrap();
	    private static Channel ch=null;
  
	    /**
		 * Netty创建全部都是实现自AbstractBootstrap。
		 * 客户端的是Bootstrap，服务端的则是ServerBootstrap。
		 **/
	    public static void main(String[] args) throws InterruptedException, IOException { 
	        	 b.group(group)  
	             .channel(NioSocketChannel.class)  
	             .option(ChannelOption.TCP_NODELAY,true)  
	             .handler(new ChannelInitializer<SocketChannel>() {  
	                 @Override  
	                 public void initChannel(SocketChannel ch) throws Exception {  
	                     ChannelPipeline p = ch.pipeline();  
	                     //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
	                     //因为服务端设置的超时时间是5秒，所以设置4秒
	                     p.addLast( new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));  
	                     p.addLast( new StringDecoder());  
	                     p.addLast( new StringEncoder());  
	                     p.addLast(new NettyClientHandlerDemo5());   //绑定自定义业务 
	                 }  
	             });  
	            // 连接服务端
	            ch = b.connect(host, port).sync().channel();
	            System.out.println("客户端成功启动...");
	            //发送消息
//	            star();
	    }
	    
	    public static void star() throws IOException{
	    	String str="你好啊，Netty服务端";
	        ch.writeAndFlush(str); 
	    	System.out.println("客户端发送数据:"+str);
	   } 
}
