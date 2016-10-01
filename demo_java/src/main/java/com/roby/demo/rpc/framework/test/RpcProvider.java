package com.roby.demo.rpc.framework.test;

import com.roby.demo.rpc.framework.bean.Config;
import com.roby.demo.rpc.framework.io.IoRpcServer;
import com.roby.demo.rpc.framework.netty.NettyRpcServer;
import com.roby.demo.rpc.framework.zeromq.ZmqRpcServer;
import com.roby.demo.rpc.service.HelloService;
import com.roby.demo.rpc.service.HelloServiceImpl;
import com.roby.demo.rpc.service.RpcServer;

public class RpcProvider {
	 public static void main(String[] args) throws Exception {  
		 Config config = new Config();
		     RpcServer server = null;
		     //server = new IoRpcServer();
		     // server = new ZmqRpcServer();
		     server = new NettyRpcServer();
	        HelloService service = new HelloServiceImpl();  
	        server.export(service, config);  
	    }  
}
