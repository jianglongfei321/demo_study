package com.roby.demo.rpc.framework.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import com.roby.demo.rpc.framework.bean.ResponseMessage;


public class ClientHandler<T>  extends ChannelHandlerAdapter {
	private NettyRpcClient nettyRpcClient;

	 ClientHandler(NettyRpcClient<?> nettyRpcClient) {
		this.nettyRpcClient = nettyRpcClient;
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ResponseMessage response = (ResponseMessage)msg;
		ConcurrentHashMap<Long, BlockingQueue<ResponseMessage>> responseMap =  nettyRpcClient.getResponseMap();
		BlockingQueue<ResponseMessage> queue = responseMap.get(response.getMsgId());
		if(queue!=null){
			queue.add(response);
			System.out.println("--result---"+response.getResponse());
		}else{
			System.out.println("result is null ------------"+response.getResponse());
		}
	}
	public NettyRpcClient getNettyRpcClient() {
		return nettyRpcClient;
	}
	public void setNettyRpcClient(NettyRpcClient nettyRpcClient) {
		this.nettyRpcClient = nettyRpcClient;
	}
	
	
	


}
