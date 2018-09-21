package com.pancm.nio.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
* Title: NettyServer
* Description: Netty服务端
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */
public class NettyServer {
	    private static final int port = 6789; //设置服务端端口
	    private static  EventLoopGroup group = new NioEventLoopGroup();   // 通过nio方式来接收连接和处理连接   
	    private static  ServerBootstrap b = new ServerBootstrap();
	    
	    /**
		 * Netty创建全部都是实现自AbstractBootstrap。
		 * 客户端的是Bootstrap，服务端的则是	ServerBootstrap。
		 **/
	    public static void main(String[] args) throws InterruptedException {
	        try {
	            b.group(group);
	            b.channel(NioServerSocketChannel.class);
	            b.childHandler(new NettyServerFilter()); //设置过滤器
	            // 异步地绑定服务器；调用 sync()方法阻塞  等待直到绑定完成
	            ChannelFuture f = b.bind(port).sync();
	            System.out.println("服务端启动成功,端口是:"+port);
	            // 获取 Channel 的  CloseFuture，并且阻塞当前线程直到它完成
	            f.channel().closeFuture().sync();
	        } finally {
	            group.shutdownGracefully(); //关闭EventLoopGroup，释放掉所有资源包括创建的线程  
	        }
	    }
}
