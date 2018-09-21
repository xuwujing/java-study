package com.pancm.nio.netty.demo5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
 
/**
 * 
* Description:服务端业务逻辑处理类  
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyServerHandlerDemo5 extends ChannelInboundHandlerAdapter {  
	/** 时间 */
    private int loss_connect_time = 0; 
    /** 发送次数 */
    private int count = 1;  
    
    @Override  
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {  
        if (evt instanceof IdleStateEvent) {  
            IdleStateEvent event = (IdleStateEvent) evt;  
            System.out.println("event.state():"+event.state()+",IdleState.READER_IDLE:"+IdleState.READER_IDLE);
            if (event.state() == IdleState.READER_IDLE) {  
                loss_connect_time++;  
                System.out.println("5 秒没有接收到客户端的信息了");  
                if (loss_connect_time > 2) {  
                    System.out.println("关闭这个不活跃的channel");  
                    ctx.channel().close();  
                }  
            }  
        } else {  
            super.userEventTriggered(ctx, evt);  
        }  
    } 
   
	
    /**
     * 处理业务逻辑
     */
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
    	System.out.println(" ----- "+msg);
    	String a="你好啊"+",count:"+count;
        ctx.writeAndFlush(a);
        count++;
    }  
  
    /**
     * 异常处理
     */
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
        cause.printStackTrace();  
        ctx.close();  
    } 
    
}
