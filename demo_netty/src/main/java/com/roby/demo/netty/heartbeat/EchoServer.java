package com.roby.demo.netty.heartbeat;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class EchoServer {
	private final int  port;
	
	EchoServer(int port){
		this.port = port;
	}
	@SuppressWarnings("unused")
	private void start() throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(group).channel(NioServerSocketChannel.class).localAddress(port).childHandler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					//p.addLast(new LoggingHandler(LogLevel.INFO));
					
				    p.addLast(new IdleStateHandler(30,30,0,TimeUnit.SECONDS));
					p.addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
					p.addLast(new ObjectEncoder());
					p.addLast(new HeartbeatHandler());
					p.addLast(new EchoServerHandler());
				}
			});
			ChannelFuture f = b.bind().sync();
			System.out.println("----服务器端启动---端口号为:"+port);
			f.channel().closeFuture().sync();
	    }finally{
	    	group.shutdownGracefully().sync();
	    }
	}
	
	    public static void main(String[] args) throws Exception {
		 new EchoServer(8009).start();
		 }
}
