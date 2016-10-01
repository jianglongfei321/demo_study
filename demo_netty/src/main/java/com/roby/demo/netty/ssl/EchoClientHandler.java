package com.roby.demo.netty.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import com.roby.demo.netty.codec.User;

public class EchoClientHandler extends ChannelHandlerAdapter{
	@Override
	 public void channelActive(ChannelHandlerContext ctx) throws Exception {
		User user = new User();
		user.setName("江龙飞");
		user.setMsg("这是一条消息");
		ChannelFuture cf = ctx.channel().writeAndFlush(user);
		cf.addListener(new GenericFutureListener<Future<? super Void>>() {
			public void operationComplete(Future<? super Void> future)
					throws Exception {
				System.out.println("-----客户端addListener------");
			}
			
		});
		System.out.println("客户端建立一个连接 ");
		ctx.writeAndFlush(user);
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
			System.out.println("数据为:"+user.getMsg());
		}
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
			System.out.println("客户端收到服务器响应数据处理完成");
		}
	
	
	
	}
