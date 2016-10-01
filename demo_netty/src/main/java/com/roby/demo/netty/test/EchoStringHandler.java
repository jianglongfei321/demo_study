package com.roby.demo.netty.test;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoStringHandler extends ChannelHandlerAdapter{
	public EchoStringHandler(){
	}
	@Override
	 public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(new User("43"));
		System.out.println("客户端建立一个连接 ");
	 }
	 @Override
		public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
			cause.printStackTrace();
			
			ByteBufAllocator a = ctx.alloc();
			System.out.println("客户端关闭了一台主机 ");
			 ctx.close();
		}

	 @Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			System.out.println("客户端收到服务器响应数据");
			User body=(User) msg;
			System.out.println("数据为:"+body);
		}
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
			System.out.println("客户端收到服务器响应数据处理完成");
		}
	
	
	
	}
