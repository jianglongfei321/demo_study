package com.roby.demo.zk.zkclinet.test;

import java.util.List;
import java.util.Random;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class zkClient {
	
	public static void main(String[] args) throws Exception {
		
		ZkClient zk= ZkCenter.getInstance();
		String root = "/coo";
		String testPath = root+"/test"+new Random().nextInt(100);
		if(!zk.exists(testPath)){
			zk.subscribeChildChanges(root, new IZkChildListener() {
				public void handleChildChange(String arg0, List<String> arg1)
						throws Exception {
					System.out.println("---------handleChildChange--------"+arg0+arg1);
				}
			});
			zk.subscribeDataChanges(testPath, new IZkDataListener() {
				public void handleDataDeleted(String arg0) throws Exception {
					System.out.println("---------handleDataDeleted--------"+arg0);
				}
				public void handleDataChange(String arg0, Object arg1) throws Exception {
					System.out.println(arg0+"---------handleDataChange--------"+arg1);
				}
			});
			if(!zk.exists(root)){
				zk.create(root, "root", CreateMode.PERSISTENT);
			}
			zk.create(testPath, "哈哈1", CreateMode.PERSISTENT);
			zk.readData(testPath);
		/*	zk.writeData(testPath, "哈哈2");
			zk.readData(testPath);
			zk.writeData(testPath, "哈哈3");
			zk.readData(testPath);
			zk.exists(testPath);*/
		    zk.delete(testPath);
		}
	}

}
