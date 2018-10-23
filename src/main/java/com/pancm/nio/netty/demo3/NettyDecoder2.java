package com.pancm.nio.netty.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;  
  
/**
 * 
* Description: Netty自定义解码器
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class NettyDecoder2 extends LengthFieldBasedFrameDecoder {  
      
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
  
    /** 
     *  
     * @param maxFrameLength 解码时，处理每个帧数据的最大长度 
     * @param lengthFieldOffset 该帧数据中，存放该帧数据的长度的数据的起始位置 
     * @param lengthFieldLength 记录该帧数据长度的字段本身的长度 
     * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数 
     * @param initialBytesToStrip 解析的时候需要跳过的字节数 
     * @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常 
     */  
    public NettyDecoder2(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,  
            int lengthAdjustment, int initialBytesToStrip, boolean failFast) {  
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,  
                lengthAdjustment, initialBytesToStrip, failFast);  
    }  
      
    @Override  
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {  
        if (in == null) {  
            return null;  
        }  
        if (in.readableBytes() < HEADER_SIZE) {  
            throw new Exception("可读信息段比头部信息都小，你在逗我？");  
        }  
          
        //注意在读的过程中，readIndex的指针也在移动  
        type = in.readByte();  
          
        flag = in.readByte();  
          
        length = in.readInt();  
          
        if (in.readableBytes() < length) {  
            throw new Exception("body字段你告诉我长度是"+length+",但是真实情况是没有这么多，你又逗我？");  
        }  
        ByteBuf buf = in.readBytes(length);  
        byte[] req = new byte[buf.readableBytes()];  
        buf.readBytes(req);  
        body = new String(req, "UTF-8");  
          
        NettyMsg customMsg = new NettyMsg(type,flag,length,body);  
        return customMsg;  
    }  
  
}  
