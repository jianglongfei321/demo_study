package com.roby.demo.netty.heartbeat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter{
	@Override
	 public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush("hello");
		System.out.println("客户端建立一个连接 ");
	 }

	 @Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			String body=(String) msg;
			System.out.println("客户端收到服务器响应数据----------------"+body);
			if(body.equals("ping")){
				ctx.writeAndFlush("ok");
			}
		}
	
	}
