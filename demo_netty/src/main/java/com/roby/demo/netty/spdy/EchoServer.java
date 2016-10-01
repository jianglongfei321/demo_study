package com.roby.demo.netty.spdy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;

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
			ChannelFuture f = b.group(group).channel(NioServerSocketChannel.class).localAddress(port)
			.childHandler(new IdleStateHandlerInitializer() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					super.initChannel(ch);
				}
			}).bind().sync();
		
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
