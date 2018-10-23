package com.pancm.nio.netty.demo2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 
* Title: NettyServerDemo2
* Description:  Netty服务端   用于测试粘包、拆包
* Version:1.0.0  
* @author pancm
* @date 2017年9月20日
 */
public class NettyServerDemo2 {
	private  final static int port=2345;  
      
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))  
                    .childHandler(new ChannelInitializer<SocketChannel>() {  
                        protected void initChannel(SocketChannel ch) throws Exception { 
                        	ChannelPipeline p = ch.pipeline(); 
                         
                          p.addLast(new LineBasedFrameDecoder(2048));		//字节解码器 ,其中2048是规定一行数据最大的字节数。  用于解决拆包问题
//                          p.addLast(new FixedLengthFrameDecoder(100));   //定长数据帧的解码器 ，每帧数据100个字节就切分一次。  用于解决粘包问题        
//                          p.addLast(new DelimiterBasedFrameDecoder(1024,Unpooled.copiedBuffer("~_~".getBytes()))); //固定字符切分解码器 ,会以"~_~"为分隔符。  注意此方法要放到StringDecoder()上面
                            p.addLast(new StringDecoder());     //设置解码器
                            p.addLast(new NettyServerHandlerDemo2());   //绑定自定义事物
                        };  
                          
                    }).option(ChannelOption.SO_BACKLOG, 128)     
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
             // 绑定端口，开始接收进来的连接  
             ChannelFuture future = sbs.bind(port).sync();    
               
             System.out.println("服务端启动成功，端口为 :" + port );  
             future.channel().closeFuture().sync();  
        } catch (Exception e) {  
            bossGroup.shutdownGracefully();  //关闭EventLoopGroup，释放掉所有资源包括创建的线程
            workerGroup.shutdownGracefully();  //关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        new NettyServerDemo2().start();  
    }  
}
