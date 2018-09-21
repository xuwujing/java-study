package com.pancm.nio.netty.demo1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
* Title: BaseClient2Handler
* Description: 客户端自定义解码器其二
* Version:1.0.0  
* @author panchengming
* @date 2017年9月17日
 */
public class BaseClient2Handler extends ChannelInboundHandlerAdapter{  
    
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        System.out.println("BaseClient2Handler Active");  
    }  
}
