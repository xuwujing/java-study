package com.pancm.nio.netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;



  

/**
 * 
* Title: NettyServer
* Description: Netty服务端  测试自定义channelhandler
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */

public class NettyServer {
	private static final int port = 1234;  
	  public static void main(String[] args) {  
			System.out.println("开始运行...");
	        try {  
	             start();  
	        } catch (InterruptedException e) { 
	        	System.out.println("运行异常...");
	            e.printStackTrace();  
	        }  
	  } 
   
	  /**
		 * Netty创建全部都是实现自AbstractBootstrap。
		 * 客户端的是Bootstrap，服务端的则是	ServerBootstrap。
		 **/
	 public static void start() throws InterruptedException {  
        ServerBootstrap sb = new ServerBootstrap();// 引导辅助程序  
        EventLoopGroup group = new NioEventLoopGroup();// 通过nio方式来接收连接和处理连接  
        try {  
        	sb.group(group);  // 通过nio方式来接收连接和处理连接  
        	sb.channel(NioServerSocketChannel.class);// 设置nio类型的channel  
        	sb.childHandler(new ChannelInitializer<SocketChannel>() {//有连接到达时会创建一个channel  
                        protected void initChannel(SocketChannel ch) throws Exception {  
                        	ChannelPipeline p = ch.pipeline();  
                           
                            // 字符串解码 和 编码
                        	p.addLast("decoder", new StringDecoder());
                        	p.addLast("encoder", new StringEncoder());
                        	 // 在channel队列中添加一个handler来处理业务  
                        	p.addLast("handler", new NettyServerHandler());  
                        	 // 以("\n")为结尾分割的 解码器
                      //  	cp.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                        }  
                    });  
            ChannelFuture f = sb.bind(port).sync();// 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功  
            System.out.println("服务端已启动... 端口是:"+port);
            f.channel().closeFuture().sync();// 应用程序会一直等待，直到channel关闭  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
       //     group.shutdownGracefully().sync();//关闭EventLoopGroup，释放掉所有资源包括创建的线程  
        }  
  
    }  
}
