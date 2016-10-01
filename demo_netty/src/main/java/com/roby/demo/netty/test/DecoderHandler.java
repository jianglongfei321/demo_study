package com.roby.demo.netty.test;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class DecoderHandler extends MessageToMessageDecoder<Object>{

	@Override
	protected void decode(ChannelHandlerContext ctx, Object msg, List out)
			throws Exception {
		User user = (User)msg;
		user.setName("echo:"+user.getName());
		out.add(user);
	}}
