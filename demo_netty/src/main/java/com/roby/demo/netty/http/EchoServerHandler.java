package com.roby.demo.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.DefaultCookie;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.ServerCookieEncoder;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

public class EchoServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		  DefaultFullHttpRequest request=(DefaultFullHttpRequest)msg;  
	        DefaultFullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.ACCEPTED, request.content());  
	        Cookie cookie = new DefaultCookie("name","dsds");
	        response.headers().add("127.0.0.1", ServerCookieEncoder.encode(cookie));
	      
	        response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH,
                    response.content().readableBytes());
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            }
            response.content().writeBytes("dsd".getBytes());
	        ctx.writeAndFlush(response);  
		    System.out.println("服务器读取到客户端请求...数据为"+request.content());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		System.out.println("服务器端接受数据完成");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
		cause.printStackTrace();
		System.out.println("远程关闭了一台主机 ");
		 ctx.close();
	}
	
	 private String getText() {
		    StringBuffer responseContent = new StringBuffer();
	        responseContent.append("dsds");
	        return responseContent.toString();

	 }
	
}
