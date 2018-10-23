package com.pancm.nio.netty.demo1;

import java.net.InetAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
* Title: NettyServerHandler
* Description:Netty服务端业务逻辑实现 
* Version:1.0.0  
* @author Administrator
* @date 2017-8-31
 */


public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		// 收到消息直接打印输出
		System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);

		// 返回客户端消息 - 我已经接收到了你的消息
		ctx.writeAndFlush("Received your message !\n");
	}

	/*
	 * 
	 * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
	 * 
	 * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
		ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ \n");
		super.channelActive(ctx);
	}

}



/** 
 * Sharable表示此对象在channel间共享 
 * handler类是我们的具体业务类 
 * */  
/*@Sharable//注解@Sharable可以让它在channels间共享 
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	 public void channelRead(ChannelHandlerContext ctx, Object msg) {   
	        System.out.println("服务端接受的数据为:" + msg);  
	        if("quit".equals(msg)){ //服务端断开的条件
	        	ctx.close();
	        }
	        Date date=new Date();
	        ctx.write(date);//回写数据 
	    }   
	    public void channelReadComplete(ChannelHandlerContext ctx) {  
	    	System.out.println("1111");
	        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) //flush掉所有写回的数据  
	        .addListener(ChannelFutureListener.CLOSE); //当flush完成后关闭channel  
	    }   
	    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
	    	System.out.println("2222");
	        cause.printStackTrace();//捕捉异常信息  
	        ctx.close();//出现异常时关闭channel   
	    }     
}*/
