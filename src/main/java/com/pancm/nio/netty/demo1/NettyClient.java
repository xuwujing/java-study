package com.pancm.nio.netty.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * 
* Title: NettyClient
* Description:Netty客户端 测试
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */
public class NettyClient {
	private static final int port = 1234;  
	private static final String host = "127.0.0.1";  
	private static  EventLoopGroup group = new NioEventLoopGroup();   // 通过nio方式来接收连接和处理连接   
	private static   Bootstrap b = new Bootstrap();
	private static Channel ch ;
	/**
	 * Netty创建全部都是实现自AbstractBootstrap。
	 * 客户端的是Bootstrap，服务端的则是	ServerBootstrap。
	 **/
	 public static void main(String[] args) throws Exception {  
		try{ 
		     b.group(group)  
		      .channel(NioSocketChannel.class)  
		      .option(ChannelOption.TCP_NODELAY, true)  
		      .handler(new ChannelInitializer<SocketChannel>() {  
		          @Override  
		          public void initChannel(SocketChannel ch) throws Exception {  
		              ChannelPipeline p = ch.pipeline();  
		              p.addLast("decoder", new StringDecoder());
                      p.addLast("encoder", new StringEncoder());  
		              p.addLast(new BaseClient1Handler());  
		              p.addLast(new BaseClient2Handler());  
		          }  
		      });  
		     
	         ChannelFuture future = b.connect(host, port).sync();  // 连接服务端
	      	 System.out.println("客户端连接成功!");  
	         future.channel().writeAndFlush("Hello Netty Server ,I am a common client");//发送消息  
	         future.channel().closeFuture().sync();   //关闭
			 } finally {  
			     group.shutdownGracefully();   //释放资源
			 }  
         
        // 	start();  
	    }
	
    public static void start() throws Exception {  
       System.out.println("客户端像服务端发送数据");
    	try {  
          String str="Hello Netty";
          ch.writeAndFlush(str+"\r\n");//发送消息
           System.out.println("客户端发送的消息:"+str);       
           ch.closeFuture().sync();   // 应用程序会一直等待，直到channel关闭 
        } finally {  
       //     group.shutdownGracefully().sync(); //关闭EventLoopGroup，释放掉所有资源包括创建的线程   
        }  
    }  
  
   
}
