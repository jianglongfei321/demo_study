package com.roby.demo.zk.zkclinet.test;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class ZkCenter {
	
	private static ZkClient zk= new ZkClient("127.0.0.1:2181", 3000 );
	
	public static ZkClient getInstance(){
		return zk;
	}
	
	public static void createLoop(String path,String data){
		String[] paths = path.split("/");
		String root="" ;
		for(String p:paths){
			root+="/"+p;
			System.out.println(root);
			if(!zk.exists(root)){
				zk.create(root, data, CreateMode.PERSISTENT);
			}
		}
	}
}

