package com.roby.demo.netty.test;
/*package com.jd.demo.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import junit.framework.TestCase;

public class TestClient extends TestCase{
	
	public  void testMethod1() throws Exception {
		EmbeddedChannel channel = new EmbeddedChannel(new DecoderHandler(), new EchoUserHandler());
		User user = channel.readOutbound();
		System.out.println(user.getName());
		System.out.println(channel.writeInbound(new User("写入的数据")));
	}
	
	
	public  void testMethod2() throws Exception {
		EmbeddedChannel channel = new EmbeddedChannel( new EchoHandler());
		ByteBuf bb = channel.readOutbound();
		ByteBuf buf=(ByteBuf) bb;
		byte[] req=new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body=new String(req,"UTF-8");
		System.out.println("client 数据为:"+body);
		
		byte[] r ="写入的数据".getBytes();
		ByteBuf msg=Unpooled.buffer(r.length);
		System.out.println(channel.writeInbound(msg));
		System.out.println(channel.readInbound());
	}
	
	public  void testMethod3() throws Exception {
		EmbeddedChannel channel = new EmbeddedChannel(new EchoStringHandler());
		User body = (User)channel.readOutbound();
		System.out.println("client 数据为:"+body.getName());
		
		System.out.println("writeInbound-----"+channel.writeInbound(new User("ds")));
		System.out.println("-------readInbound---"+channel.readInbound());
		
		
	}
	
	
	public  void testMethod4() throws Exception {
		EmbeddedChannel channel = new EmbeddedChannel(new EchoStringHandler());
		ByteBufAllocator allo = channel.alloc();
		ByteBuf bb = allo.directBuffer();
		System.out.println(bb.refCnt());
		bb.release();
		System.out.println(bb.refCnt());
		
	}
}
*/