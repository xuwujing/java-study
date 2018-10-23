package com.pancm.nio.netty.demo;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * 
* Title: HelloServerHandler
* Description:  服务端业务逻辑
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
	/*
	 * 收到消息时，返回信息
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		// 收到消息直接打印输出
		System.out.println("服务端接受的消息 : " + msg);
		if("quit".equals(msg)){//服务端断开的条件
			ctx.close();
		}
		// 返回客户端消息
//		ctx.writeAndFlush("收到消息:"+ msg+",当前的时间是:"+MyTools.getNowTime("")+"\n");
		ctx.writeAndFlush("收到消息:"+ msg+",当前的时间是:"+new Date());
	}

	/*
	 * 建立连接时，返回消息
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
		ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ ");
		super.channelActive(ctx);
	}
}
