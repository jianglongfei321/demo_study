package com.roby.demo.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoClientHandler extends ChannelHandlerAdapter{
	private  ByteBuf firstMessage;
	public EchoClientHandler(){
		byte[] req ="test data ".getBytes();
		firstMessage=Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}
	@Override
	 public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		ctx.channel().writeAndFlush(firstMessage);
		System.out.println("客户端建立一个连接 ");
		//ctx.writeAndFlush(firstMessage);
	 }
	 @Override
		public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception {
			cause.printStackTrace();
			ByteBufAllocator a = ctx.alloc();
			System.out.println("客户端关闭了一台主机 ");
			 ctx.close();
		}

	 @Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			System.out.println("客户端收到服务器响应数据");
			ByteBuf buf=(ByteBuf) msg;
			byte[] req=new byte[buf.readableBytes()];
			buf.readBytes(req);
			String body=new String(req,"UTF-8");
			System.out.println("数据为:"+body);
		}
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			ctx.flush();
			System.out.println("客户端收到服务器响应数据处理完成");
		}
	
	
	
	}
