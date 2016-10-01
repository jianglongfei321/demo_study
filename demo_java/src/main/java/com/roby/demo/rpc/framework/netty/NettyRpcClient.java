package com.roby.demo.rpc.framework.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.roby.demo.rpc.framework.bean.Config;
import com.roby.demo.rpc.framework.bean.RequestMessage;
import com.roby.demo.rpc.framework.bean.ResponseMessage;
import com.roby.demo.rpc.service.RpcClient;


public class NettyRpcClient<T> implements RpcClient{
	private final ConcurrentHashMap<Long, BlockingQueue<ResponseMessage>> responseMap = new ConcurrentHashMap<Long, BlockingQueue<ResponseMessage>>();
	private EventLoopGroup group = new NioEventLoopGroup();//Factory.getEventLoopGroup(new Config());
	private Bootstrap b = new Bootstrap();
	private long msgId;

	/***
	 * 初始化EventLoopGroup和Bootstrap
	 * @param host
	 * @param port
	 */
	public NettyRpcClient(long msgId){
		this.msgId = msgId;
		b = b.group(group).channel(NioSocketChannel.class);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T refer(final Class<T> interfaceClass,final Config config) throws  Exception{
		Object obj = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new InvocationHandler(){
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return  invokeMethod(method, args,  config);
			}
		});
		return (T)obj;
	}
	public Object invokeMethod(final Method method, final Object[] args,final Config config) throws Exception{
		b = b.remoteAddress(new InetSocketAddress(config.getHost(), config.getPort())).handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new ObjectDecoder(1024*1024,ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				p.addLast(new ObjectEncoder());
				p.addLast(new ClientHandler<T>(NettyRpcClient.this));
			}
		});
		ChannelFuture f = b.connect().sync();
		f =  b.connect().syncUninterruptibly().sync();
		RequestMessage request = new RequestMessage(method.getName(),method.getParameterTypes(),args);
		request.setMsgId(msgId);
		f.channel().writeAndFlush(request); 
		responseMap.putIfAbsent(msgId, new LinkedBlockingQueue<ResponseMessage>(1));
		Object rep = getResponse(msgId).getResponse();
		return rep;
	}
	public ResponseMessage getResponse(final long messageId) {
		ResponseMessage result = null;
		responseMap.putIfAbsent(messageId, new LinkedBlockingQueue<ResponseMessage>(1));
		try {
			result = responseMap.get(messageId).take();
		} catch (final InterruptedException ex) {
			ex.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}  finally {
			responseMap.remove(messageId);
		}
		return result;
	}
	public ConcurrentHashMap<Long, BlockingQueue<ResponseMessage>> getResponseMap() {
		return responseMap;
	}
}
