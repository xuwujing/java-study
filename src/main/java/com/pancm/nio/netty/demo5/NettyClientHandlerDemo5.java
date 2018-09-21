package com.pancm.nio.netty.demo5;

import com.pancm.utils.MyTools;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 
* Description: Netty业务处理  心跳测试
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyClientHandlerDemo5 extends ChannelInboundHandlerAdapter {  
      
		/**心跳命令 */
	 	private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",  
	            CharsetUtil.UTF_8));  
	      
	    private static final int TRY_TIMES = 3;  
	      
	    private int currentTime = 0;  
	      
	    /**
	     * 建立连接时
	     */
	    @Override  
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
	        System.out.println("激活时间是："+MyTools.getNowTime(""));  
	        ctx.fireChannelActive();  
	    }  
	  
	     /**
	      * 关闭连接时
	      */
	    @Override  
	    public void channelInactive(ChannelHandlerContext ctx) throws Exception {  
	        System.out.println("停止时间是："+MyTools.getNowTime(""));  
	    }  
	  
	    @Override  
	    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {  
	        System.out.println("循环触发时间："+MyTools.getNowTime(""));  
	        if (evt instanceof IdleStateEvent) {  
	            IdleStateEvent event = (IdleStateEvent) evt;  
	            System.out.println("event.state():"+event.state()+",IdleState.READER_IDLE:"+IdleState.READER_IDLE);
	            if (event.state() == IdleState.WRITER_IDLE) {  
	            	System.out.println("TRY_TIMES:"+TRY_TIMES);
	                if(currentTime <= TRY_TIMES){  
	                    System.out.println("currentTime:"+currentTime);  
	                    currentTime++;  
	                    ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());  
	                }  
	            }  
	        }  
	    }  

	    /**
	     * 业务逻辑处理	
	     */
	    @Override  
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
	    	System.out.println("接受的消息:"+msg);
	        String message = (String) msg;  
	        if (message.equals("Heartbeat")) {  
	            ctx.write("成功收到心跳信息");  
	            ctx.flush();  
	        }  
	        ReferenceCountUtil.release(msg);  
	    }  
  
}  
