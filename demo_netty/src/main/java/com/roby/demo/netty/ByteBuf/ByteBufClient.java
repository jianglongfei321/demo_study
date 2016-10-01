package com.roby.demo.netty.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufClient {

	public static void main(String[] args) throws Exception {
		CompositeByteBuf cb = Unpooled.compositeBuffer();
		
		ByteBuf head = Unpooled.buffer(10);
		head.writeBytes("abcd".getBytes());
		System.out.println(head.getByte(0));
		
		System.out.println(covert(head.readBytes(2)));
		
		System.out.println(covert(head.readBytes(2)));
		System.out.println(head.readerIndex());
		System.out.println(head.isWritable());
		System.out.println(head.isReadable(3));
		ByteBuf bodyS = Unpooled.copiedBuffer("aaaa".getBytes());
		//body.writeBytes();
		cb.addComponents(head,bodyS);
		for(Iterator<ByteBuf> it = cb.iterator();it.hasNext();){
			System.out.println(it.next().toString());
			
		}
	}
	
	public static String covert(ByteBuf byteBuf) throws Exception{
		
		ByteBuf buf = byteBuf.readBytes(2);
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		return new String(req,"UTF-8");
	}
}
