package com.roby.demo.netty.heartbeat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartbeatHandler extends ChannelHandlerAdapter  {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if(evt instanceof IdleStateEvent){
			IdleStateEvent e = (IdleStateEvent)evt;
			if(e.state()==IdleState.READER_IDLE){
				System.out.println("READER_IDLE");
			}else if(e.state()==IdleState.WRITER_IDLE){
				System.out.println("WRITER_IDLE");
				ctx.writeAndFlush("ping");
			}else if(e.state() == IdleState.ALL_IDLE)  
                System.out.println("all idle");  
		}
	}
}
