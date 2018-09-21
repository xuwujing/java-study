package com.pancm.nio.netty.demo1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
* Title: NettyClientHandler
* Description:客户端业务逻辑 
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext chc, String str)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("消息为:"+str);
		
	}  
	
	@Override
    public void channelActive(ChannelHandlerContext chc) throws Exception {
        System.out.println("正在连接...");
        super.channelActive(chc);
    }

    @Override
    public void channelInactive(ChannelHandlerContext chc) throws Exception {
        System.out.println("连接关闭");
        super.channelInactive(chc);
    }
	
}
