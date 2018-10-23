package com.pancm.nio.netty.demo3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;  

/**
 * 
* Description: 
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyClientHandlerDemo3 extends ChannelInboundHandlerAdapter {  
      
	/**
	 * 连接时发送消息
	 */
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        NettyMsg customMsg = new NettyMsg((byte)0xAB, (byte)0xCD, "Hello,Netty".length(), "Hello,Netty");  
        ctx.writeAndFlush(customMsg);  
    }  
  
}  
