package com.pancm.nio.netty.demo3;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;  

/**
 * 
* Description:Netty自定义编码器 
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyEncoder extends MessageToByteEncoder<NettyMsg> {  
  
    @Override  
    protected void encode(ChannelHandlerContext ctx, NettyMsg msg, ByteBuf out) throws Exception {  
        if(null == msg){  
            throw new Exception("消息不能为空!");  
        }  
          
        String body = msg.getBody();  
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));  
        out.writeByte(msg.getType());  
        out.writeByte(msg.getFlag());  
        out.writeInt(bodyBytes.length);  
        out.writeBytes(bodyBytes);  
          
    }  
  
}  
