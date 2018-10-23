package com.pancm.nio.netty.demo3;

import java.io.IOException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
* Title: NettyClientDemo3
* Description: Netty客户端  测试自定义解码器
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyClientDemo3 {
	  public static String host = "127.0.0.1";  //ip地址
	    public static int port = 3456;			//端口
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
	                     p.addLast(new NettyEncoder());    //绑定自定义编码器
	                     p.addLast(new NettyClientHandlerDemo3());   //绑定自定义业务 
	                 }  
	             });  
	            // 连接服务端
	            ch = b.connect(host, port).sync().channel();
	            System.out.println("客户端成功启动...");
	            star();
	    }
	    
	    public static void star() throws IOException{
	    	String str="你好,Netty";
	    	 NettyMsg customMsg = new NettyMsg((byte)0xAB, (byte)0xCD, str.length(), str);  
	        ch.writeAndFlush(customMsg); 
	    	System.out.println("客户端发送数据:"+customMsg);
	   } 
}
