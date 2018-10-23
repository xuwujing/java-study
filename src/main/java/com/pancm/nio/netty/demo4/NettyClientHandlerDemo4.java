package com.pancm.nio.netty.demo4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;  

/**
 * 
* Description: 
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyClientHandlerDemo4 extends ChannelInboundHandlerAdapter {  
      
	/**
	 * 连接时发送消息
	 */
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
    	NettySendBody nst = new NettySendBody();      	
    	nst.put("1111", "2222");
        ctx.writeAndFlush(nst);  
        System.out.println(nst);
    }  
  
}  
