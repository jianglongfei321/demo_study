package com.roby.demo.rpc.framework.netty;
import com.roby.demo.rpc.framework.bean.Config;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
public class Factory {
	
	   private static EventLoopGroup eventLoopGroup; // 全局共用
	   private static int childNioEventThreads = 5; // worker线程==IO线程
	    
	    public static EventLoopGroup getEventLoopGroup(Config config){
	        if (eventLoopGroup == null || eventLoopGroup.isShutdown()) {
	            initEventLoop(config);
	        }
	        return eventLoopGroup;
	    }
	    private static synchronized void initEventLoop(Config config) {
	        if (eventLoopGroup == null || eventLoopGroup.isShutdown()) {
	            int threads = childNioEventThreads > 0 ?  childNioEventThreads : 6; // 默认cpu+1,至少6个
	            NamedThreadFactory threadName = new NamedThreadFactory("JLF-CLI-WORKER", true);
	            eventLoopGroup = new NioEventLoopGroup(threads, threadName);
	        }
	    }
	    public static void closeEventGroup(){
	        if (eventLoopGroup != null && !eventLoopGroup.isShutdown()) {
	            eventLoopGroup.shutdownGracefully();
	        }
	        eventLoopGroup = null;
	    }
	    
	private Factory(){
		
	}
}
