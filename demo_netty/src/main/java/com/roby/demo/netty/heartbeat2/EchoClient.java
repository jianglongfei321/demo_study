package com.roby.demo.netty.heartbeat2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class EchoClient {
	private final static int readerIdleTimeSeconds = 4;//读操作空闲30秒
	private final static int writerIdleTimeSeconds = 5;//写操作空闲60秒
	private final static int allIdleTimeSeconds = 10;//读写全部空闲100秒
    public void connect(int port, String host) throws Exception {
	// 配置客户端NIO线程组
	EventLoopGroup group = new NioEventLoopGroup();
	try {
	    Bootstrap b = new Bootstrap();
	    b.group(group).channel(NioSocketChannel.class)
		    .option(ChannelOption.TCP_NODELAY, true)
		    .handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch)
					throws Exception {
				    ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds,allIdleTimeSeconds));
				    ch.pipeline().addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				    ch.pipeline().addLast(new ObjectEncoder());
				    ch.pipeline().addLast(new StringDecoder());
				    ch.pipeline().addLast(new EchoClientHandler());
				}
		    });

	    // 发起异步连接操作
	    ChannelFuture f = b.connect(host, port).sync();
	    // 当代客户端链路关闭
	    f.channel().closeFuture().sync();
	} finally {
	    // 优雅退出，释放NIO线程组
	    group.shutdownGracefully();
	}
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	int port = 8080;
	if (args != null && args.length > 0) {
	    try {
		port = Integer.valueOf(args[0]);
	    } catch (NumberFormatException e) {
		// 采用默认值
	    }
	}
	new EchoClient().connect(port, "127.0.0.1");
    }
}