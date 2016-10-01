package com.roby.demo.rpc.service;

import com.roby.demo.rpc.framework.bean.Config;

public interface RpcClient {
	
	 public  <T> T refer(final Class<T> interfaceClass,final  Config config) throws Exception ;

}
