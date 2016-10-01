package com.roby.demo.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class CodecHandler extends ByteToMessageCodec<String>{

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out)
			throws Exception {
		String str  = (String)msg;
		byte[] req =str.getBytes();
		out.writeBytes(req);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out)
			throws Exception {
		ByteBuf buf=(ByteBuf) in;
		byte[] req=new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body=new String(req,"UTF-8");
		out.add(body);
	}

	

	}
