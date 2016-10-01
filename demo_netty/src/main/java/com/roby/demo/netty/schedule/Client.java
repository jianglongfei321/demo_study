package com.roby.demo.netty.schedule;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

public class Client {
	
	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		//EventLoop event = new NioEventLoop(group,);
		Channel channel = null;
		ScheduledFuture scheduleFuture = 	channel.eventLoop().schedule(new Runnable(){
			public void run() {
				System.out.println("执行一次");
			}
			
		}, 5, TimeUnit.SECONDS);
	}

}
