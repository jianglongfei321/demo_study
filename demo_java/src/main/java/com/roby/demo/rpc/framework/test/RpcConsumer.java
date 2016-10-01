package com.roby.demo.rpc.framework.test;

import com.roby.demo.rpc.framework.bean.Config;
import com.roby.demo.rpc.framework.netty.NettyRpcClient;
import com.roby.demo.rpc.framework.zeromq.ZmqRpcClient;
import com.roby.demo.rpc.service.HelloService;
import com.roby.demo.rpc.service.RpcClient;

public class RpcConsumer {
	 public static void main(String[] args) throws Exception {  
		 Config config = new Config();
		 RpcClient client = null;
		    //client = new IoRpcClient();
		 // client = new ZmqRpcClient();
		      client = new NettyRpcClient(2l);
	          HelloService service = client.refer(HelloService.class,config);  
	          for (int i = 0; i < Integer.MAX_VALUE; i ++) {  
	            String hello = service.hello("World" + i);  
	            System.out.println(hello);  
	           // Thread.sleep(1000);  
	        }  
	    }  
}
