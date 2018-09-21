package com.pancm.nio.netty.demo6;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
 

/**
 * 
* Title: NettyServerFilter
* Description: Netty 服务端过滤器
* Version:1.0.0  
* @author pancm
* @date 2017年10月26日
 */
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {
 
     @Override
     protected void initChannel(SocketChannel ch) throws Exception {
         ChannelPipeline ph = ch.pipeline();
         //处理http服务的关键handler
         ph.addLast("encoder",new HttpResponseEncoder());
         ph.addLast("decoder",new HttpRequestDecoder());
         ph.addLast("aggregator", new HttpObjectAggregator(10*1024*1024)); 
         ph.addLast("handler", new NettyServerHandler());// 服务端业务逻辑
     }
 }
