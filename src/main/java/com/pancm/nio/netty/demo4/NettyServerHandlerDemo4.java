package com.pancm.nio.netty.demo4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;  
 
/**
 * 
* Description:服务端业务逻辑处理类 
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyServerHandlerDemo4 extends ChannelInboundHandlerAdapter {  
   
	/**
	 * 处理业务逻辑消息
	 */
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
    	System.out.println(" ----- "+msg);
        if(msg instanceof NettySendBody) {  
        	NettySendBody nsb = (NettySendBody)msg;  
            System.out.println("服务端接受的消息为:"+nsb.toString());  
            NettySendBody nsb1 = new  NettySendBody(); 
            nsb1.put("3333","44444");
            ctx.writeAndFlush(nsb1);
        }else{  
        	System.out.println("收到非法请求");
        }
    }  
    
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
    	System.out.println(" ----- "+msg);
        if(msg instanceof NettySendBody) {  
        	NettySendBody nsb = (NettySendBody)msg;  
            System.out.println("服务端接受的消息为:"+nsb.toString());  
            NettySendBody nsb1 = new  NettySendBody(); 
            nsb1.put("3333","44444");
            ctx.writeAndFlush(nsb1);
        }else{  
        	System.out.println("收到非法请求");
        }  
    }  
  
}
