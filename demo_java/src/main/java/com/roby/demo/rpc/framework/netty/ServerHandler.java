package com.roby.demo.rpc.framework.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

import com.roby.demo.rpc.framework.bean.RequestMessage;
import com.roby.demo.rpc.framework.bean.ResponseMessage;
public class ServerHandler  extends ChannelHandlerAdapter{
	private Object service;
	ServerHandler(Object service){
		this.service = service;
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if(msg instanceof RequestMessage){
			RequestMessage request = (RequestMessage)msg;
			 Method method = service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
			 method.invoke(service, request.getArguments());
			 Object result = method.invoke(service, request.getArguments());  
			 ResponseMessage response = new ResponseMessage(request.getMsgId(),result,ctx.channel());
			 ctx.writeAndFlush(response);
		}
		
	}
	public Object getService() {
		return service;
	}
	public void setService(Object service) {
		this.service = service;
	}

}
