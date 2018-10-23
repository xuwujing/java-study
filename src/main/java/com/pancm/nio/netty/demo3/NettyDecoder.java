package com.pancm.nio.netty.demo3;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;  
  
/**
 * 
* Description: Netty自定义解码器
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyDecoder extends ByteToMessageCodec<NettyMsg> {  
      
    //判断传送客户端传送过来的数据是否按照协议传输，头部信息的大小应该是 byte+byte+int = 1+1+4 = 6  
    private static final int HEADER_SIZE = 6;  
      
    /** 类型  系统编号 0xAB 表示A系统，0xBC 表示B系统  */
    private byte type;  
      
    /** 信息标志  0xAB 表示心跳包    0xBC 表示超时包  0xCD 业务信息包   */
    private byte flag;  
      
    /** 主题信息的长度 */ 
    private int length;  
       
    /** 主题信息  */ 
    private String body; 
  

	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMsg msg, ByteBuf out)
			throws Exception {
		  System.out.println("msg:"+msg); 
		  if (out == null) {  
	            return ;  
	        }  
	        if (out.readableBytes() < HEADER_SIZE) {  
	            throw new Exception("可读信息段比头部信息都小，你在逗我？");  
	        }  
	        //注意在读的过程中，readIndex的指针也在移动  
	        type = out.readByte();  
	          
	        flag = out.readByte();  
	          
	        length = out.readInt();  
	          
	        if (out.readableBytes() < length) {  
	            throw new Exception("body字段你告诉我长度是"+length+",但是真实情况是没有这么多，你又逗我？");  
	        }  
	        ByteBuf buf = out.readBytes(length);  
	        byte[] req = new byte[buf.readableBytes()];  
	        buf.readBytes(req);  
	        body = new String(req, "UTF-8");  
	        NettyMsg customMsg = new NettyMsg(type,flag,length,body);  
		
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		
	}  
  
}  
