package com.pancm.nio.netty.demo;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
 
/**
  * 
 * Title: HelloServerInitializer
 * Description: Netty 服务端过滤器
 * Version:1.0.0  
 * @author Administrator
 * @date 2017-8-31
  */
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {
 
     @Override
     protected void initChannel(SocketChannel ch) throws Exception {
         ChannelPipeline ph = ch.pipeline();
         // 以("\n")为结尾分割的 解码器
//        ph.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
         // 解码和编码，应和客户端一致
         ph.addLast("decoder", new StringDecoder());
         ph.addLast("encoder", new StringEncoder());
         ph.addLast("handler", new NettyServerHandler());// 服务端业务逻辑
     }
 }
