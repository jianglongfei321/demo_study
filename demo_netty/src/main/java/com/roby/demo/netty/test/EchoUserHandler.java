package com.roby.demo.netty.test;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoUserHandler extends ChannelHandlerAdapter{
	public EchoUserHandler(){}
	@Override
	 public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		ctx.channel().writeAndFlush(new User("name"));
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
			User user=(User) msg;
			System.out.println("数据为:"+user.getName());
		}
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
			System.out.println("客户端收到服务器响应数据处理完成");
		}
	
	
	
	}
