package com.roby.demo.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufHolderClient {

	public static void main(String[] args) throws Exception {
		CompositeByteBuf cb = Unpooled.compositeBuffer();
		
	}
	
	public static String covert(ByteBuf byteBuf) throws Exception{
		
		ByteBuf buf = byteBuf.readBytes(2);
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		return new String(req,"UTF-8");
	}
}
