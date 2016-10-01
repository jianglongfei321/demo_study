package com.roby.demo.netty.heartbeat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body=(String) msg;
		System.out.println("服务器读取到客户端请求...数据为:"+body);
		//ctx.write(body);
	}
	
}
