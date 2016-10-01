package com.roby.demo.netty.codec;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class EchoClient {
	
	private final int  port;
	
	private  String   host;

	EchoClient(String host,int port){
		this.host = host;
		this.port = port;
	}
	
	public void start() throws  Exception{
		
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
					//添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
					ch.pipeline().addLast(new ObjectEncoder());
					ch.pipeline().addLast(new EchoClientHandler());
				}
			});
			
			ChannelFuture f = b.connect().sync();
			f.channel().closeFuture().sync();
			
		}finally{
			group.shutdownGracefully().sync();
		}
	}
	
	  public static void main(String[] args) throws Exception {
			 new EchoClient("localhost",8009).start();
			 }
}
