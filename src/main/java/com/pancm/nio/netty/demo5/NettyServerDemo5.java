package com.pancm.nio.netty.demo5;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
 
/**
 * 
* Description:  Netty 服务端    测试心跳
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyServerDemo5 {  
      /** 设置端口 */
    private  final static int port=5678;  
      
      
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))  
                    .childHandler(new ChannelInitializer<SocketChannel>() {  
                          
                        protected void initChannel(SocketChannel ch) throws Exception { 
                        	 ChannelPipeline p = ch.pipeline(); 
                        	 //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
                        	 p.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                        	 p.addLast(new StringDecoder());  			//String解码器
                        	 p.addLast(new StringEncoder());  			//String编码器
                        	 p.addLast(new NettyServerHandlerDemo5());  //绑定自定义业务逻辑
                        };  
                          
                    }).option(ChannelOption.SO_BACKLOG, 128)     
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
             // 绑定端口，开始接收进来的连接  
             ChannelFuture future = sbs.bind(port).sync();    
               
             System.out.println("Netty服务端启动成功,端口为: " + port );  
             future.channel().closeFuture().sync();   //释放监听
        } catch (Exception e) {  
            bossGroup.shutdownGracefully();    //释放资源
            workerGroup.shutdownGracefully();  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        new NettyServerDemo5().start();  
    }  
}  
