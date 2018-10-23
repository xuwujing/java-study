package com.pancm.nio.netty.demo2;

import java.io.IOException;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 
* Title: NettyClientDemo2
* Description: Netty客户端  用于测试粘包、拆包
* Version:1.0.0  
* @author pancm
* @date 2017年9月20日
 */
public class NettyClientDemo2 {
	  public static String host = "127.0.0.1";  //ip地址
	    public static int port = 2345;			//端口
	    /// 通过nio方式来接收连接和处理连接   
	    private static EventLoopGroup group = new NioEventLoopGroup(); 
	    private static  Bootstrap b = new Bootstrap();
	    private static Channel ch;
  
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
	                     p.addLast(new StringDecoder());    //绑定解码器
	                     p.addLast(new NettyClientHandlerDemo2());   //绑定自定义业务 
	                 }  
	             });  
	            // 连接服务端
	            ch = b.connect(host, port).sync().channel();
	            System.out.println("客户端成功启动...");
	     //       star();
	    }
	    
	    public static void star() throws IOException{
	    	String str="你好 Netty!";
	    	byte[] by=str.getBytes();
	    	ByteBuf message = Unpooled.buffer(by.length); ;  
	        message.writeBytes(by);  
	        ch.writeAndFlush(message); 
	    	System.out.println("客户端发送数据:"+str);
	   } 
}
