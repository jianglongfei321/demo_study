package com.roby.demo.rpc.framework.netty;

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

import com.roby.demo.rpc.framework.bean.Config;
import com.roby.demo.rpc.service.RpcServer;

public class NettyRpcServer implements RpcServer{
	
    /** 
     * 暴露服务 
     *  
     * @param service 服务实现 
     * @param port 服务端口 
     * @throws Exception 
     */  
    public  void export(final Object service, final Config config) throws Exception {  
        if (service == null)  
            throw new IllegalArgumentException("服务实例为空");  
        if (config.getPort() <= 0 || config.getPort() > 65535)  
            throw new IllegalArgumentException("端口无效");  
        System.out.println("Export service " + service.getClass().getName() + " on port " + config.getPort());  
        EventLoopGroup group =  new NioEventLoopGroup();//Factory.getEventLoopGroup(new Config());
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(group).channel(NioServerSocketChannel.class).localAddress(config.getPort()).childHandler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
					p.addLast(new ObjectEncoder());
					p.addLast(new ServerHandler(service));
				}
			});
			ChannelFuture f = b.bind().sync();
			System.out.println("----服务器端启动---端口号为:"+config.getPort());
			f.channel().closeFuture().sync();
	    }finally{
	    	group.shutdownGracefully().sync();
	    }
    }
	
    
}
