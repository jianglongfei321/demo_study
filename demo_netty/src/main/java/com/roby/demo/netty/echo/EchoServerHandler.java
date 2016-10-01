package com.roby.demo.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("服务器读取到客户端请求...");
		ByteBuf buf=(ByteBuf) msg;
		byte[] req=new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body=new String(req,"UTF-8");
		System.out.println("服务器读取数据为:"+body);
		ByteBuf resp=Unpooled.copiedBuffer(body.getBytes());
		ctx.write(resp);
		System.out.println("服务器做出了响应");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		System.out.println("服务器端接受数据完成");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
		cause.printStackTrace();
		System.out.println("远程关闭了一台主机 ");
		 ctx.close();
	}
	
}
