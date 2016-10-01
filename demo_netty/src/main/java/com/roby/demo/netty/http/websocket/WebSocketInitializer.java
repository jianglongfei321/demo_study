package com.roby.demo.netty.http.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public abstract class WebSocketInitializer extends ChannelInitializer<Channel> {
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new HttpServerCodec(),
				new HttpObjectAggregator(65536),
				 new WebSocketServerProtocolHandler("/websocket"),
				new TextFrameHandler(),
				 new BinaryFrameHandler(),
				 new ContinuationFrameHandler());
	}
	
	public static final class TextFrameHandler extends ChannelHandlerAdapter {
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			System.out.println("客户端收到服务器响应数据1");
		}
		 }
		 public static final class BinaryFrameHandler extends ChannelHandlerAdapter{
		 @Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			 System.out.println("客户端收到服务器响应数据2");
		 }
		 }
		
		 public static final class ContinuationFrameHandler extends ChannelHandlerAdapter{
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			System.out.println("客户端收到服务器响应数据3");
		 }
		 }

}
