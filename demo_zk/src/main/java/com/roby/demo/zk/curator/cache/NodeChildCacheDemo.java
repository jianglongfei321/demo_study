package com.roby.demo.zk.curator.cache;

import java.util.Random;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;

public class NodeChildCacheDemo {
	public static void main(String[] args) throws Exception {
		CuratorFramework client = null;
		NodeCache nodeCache = null;
		String path = "/francis/nodecache/c"+new Random().nextInt(10000);
		try {
			client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",new ExponentialBackoffRetry(1000, 3));
			client.start();
			// 这里将第三个参数cacheData设置为true，这样当对子节点调用setData时，会受到CHILDREN_UPDATE通知。
			PathChildrenCache childrenCache = new PathChildrenCache(client,path, true);
			childrenCache.start(StartMode.NORMAL);
			childrenCache.getListenable().addListener(
					new PathChildrenCacheListener() {
						public void childEvent(CuratorFramework client,
								PathChildrenCacheEvent event) throws Exception {
							if (event.getType() == Type.INITIALIZED) {
								System.out.println("init");
							} else if (event.getType() == Type.CHILD_ADDED) {
								System.out.println("create"+ event.getData().getPath());
							} else if (event.getType() == Type.CHILD_REMOVED) {
								System.out.println("remove:"+ event.getData().getPath());
							} else if (event.getType() == Type.CHILD_UPDATED) {
								System.out.println("update:"	+ new String(event.getData()
														.getData()));
							}

						}
					});
			Thread.sleep(1000);
			// 创建父节点
			//System.out.println(path+"---------"+client.checkExists().forPath(path));
			//System.out.println(client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, "init".getBytes()));
			Thread.sleep(1000);
			// 创建子节点
			String childPath1 = ZKPaths.makePath(path, "a");
			childPath1 = client.create().withMode(CreateMode.PERSISTENT)
					.forPath(childPath1, "1".getBytes());
			Thread.sleep(1000);

			// 对子节点赋值
			client.setData().forPath(childPath1, "aaa".getBytes());
			Thread.sleep(1000);
			
			// 对子节点修改
			client.setData().forPath(childPath1, "bbb".getBytes());
			Thread.sleep(1000);

			// 删除子节点
			client.delete().forPath(childPath1);
			client.delete().deletingChildrenIfNeeded().forPath("/francis");

			Thread.sleep(2000);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(nodeCache);
			CloseableUtils.closeQuietly(client);
		}
	}
}
