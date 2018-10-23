package com.pancm.nio.netty.demo3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;  
 
/**
 * 
* Description:服务端业务逻辑处理类 
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyServerHandlerDemo3 extends SimpleChannelInboundHandler<Object> {  
  
    @Override  
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
        if(msg instanceof NettyMsg) {  
            NettyMsg customMsg = (NettyMsg)msg;  
            System.out.println("接受的数据:"+ctx.channel().remoteAddress()+" send "+customMsg.getBody());  
        }  
          
    }  
  
}
