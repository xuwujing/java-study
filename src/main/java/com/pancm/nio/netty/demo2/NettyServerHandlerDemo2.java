package com.pancm.nio.netty.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
* Title: NettyServerHandlerDemo2
* Description: Netty服务端业务逻辑处理  用于测试粘包、拆包
* Version:1.0.0  
* @author pancm
* @date 2017年9月20日
 */
public class NettyServerHandlerDemo2 extends ChannelInboundHandlerAdapter{  
    
    
    private int counter;  
      
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
          
        String body = (String)msg;  
        System.out.println("接受的数据是: " + body + ";条数是: " + ++counter);  
    }  
      
      
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        cause.printStackTrace();  
        ctx.close();  
    }  
  
}  
