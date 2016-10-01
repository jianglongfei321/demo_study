package com.roby.demo.rpc.service;

import com.roby.demo.rpc.framework.bean.Config;

public interface RpcServer {
	
	public  void export(final Object service, Config config) throws Exception ;

}
