package com.roby.demo.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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
					ch.pipeline().addLast(new EchoServerHandler());
				}
			});
			ChannelFuture f = b.bind().sync();
/*			f.addListener(new GenericFutureListener<Future<? super Void>>() {
				public void operationComplete(Future<? super Void> future)
						throws Exception {
					System.out.println("-----收到客户端的请求------");
				}
				
			});
*/			System.out.println("----服务器端启动---端口号为:"+port);
			f.channel().closeFuture().sync();
	    }finally{
	    	group.shutdownGracefully().sync();
	    }
	}
	
	    public static void main(String[] args) throws Exception {
		 new EchoServer(8009).start();
		 }
}
