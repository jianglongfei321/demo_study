package com.roby.demo.netty.heartbeat2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class EchoClientHandler extends ChannelHandlerAdapter {

	private int counter;
	public EchoClientHandler() {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
			ctx.writeAndFlush("dsds");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("This is " + ++counter + " times receive server : [" + msg + "]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE)
				System.out.println("read idle");
			else if (event.state() == IdleState.WRITER_IDLE)
				System.out.println("write idle");
			else if (event.state() == IdleState.ALL_IDLE)
				System.out.println("all idle");
		}
	}
}