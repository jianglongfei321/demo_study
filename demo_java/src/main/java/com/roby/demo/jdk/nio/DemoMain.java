package com.roby.demo.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class DemoMain {
	
	ServerSocketChannel serverSocketChannel = null;
	DemoMain(){
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(9999));
			while(true){
			    SocketChannel socketChannel =
			            serverSocketChannel.accept();
			}
		} catch (IOException e) {
			
		}
	}
	
	
	public static void main(String[] args) {
		new DemoMain();
	}
	

}
