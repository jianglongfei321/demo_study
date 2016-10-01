package com.roby.demo.zk.curator.cache;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
/**
 * Curator提供了三种Watcher(Cache)来监听结点的变化：

Path Cache：监视一个路径下1）孩子结点的创建、2）删除，3）以及结点数据的更新。产生的事件会传递给注册的PathChildrenCacheListener。
Node Cache：监视一个结点的创建、更新、删除，并将结点的数据缓存在本地。
Tree Cache：Path Cache和Node Cache的“合体”，监视路径下的创建、更新、删除事件，并缓存路径下所有孩子结点的数据。
 * @author jlf
 *
 */
public class NodeCacheDemo {
  public static void main(String[]args) throws Exception{
      CuratorFramework client=null;
      NodeCache nodeCache=null;
      String path="/francis/nodecache/a";
      try{ 
         client= CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000,3));
         client.start();
         path=client.create().creatingParentsIfNeeded().withProtection().withMode(CreateMode.EPHEMERAL).forPath(path,"init".getBytes());
         nodeCache=new NodeCache(client,path,false);
         nodeCache.start();
         addListener(nodeCache);
         client.setData().forPath(path,"hello".getBytes());
         Thread.sleep(1000);
         client.delete().deletingChildrenIfNeeded().forPath(path);
         Thread.sleep(Integer.MAX_VALUE);
         
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          //这里因为是测试，没有加他们。
          CloseableUtils.closeQuietly(nodeCache);
          CloseableUtils.closeQuietly(client);
      }     
  }
  
  public static void addListener(final NodeCache nodeCache){
      //监听类型：节点是否存在，节点数据内容改变，不监听节点删除。
	  nodeCache.getListenable().addListener(new NodeCacheListener(){
            public void nodeChanged() throws Exception {
                if(nodeCache.getCurrentData()!=null)
                    System.out.println("path:"+nodeCache.getCurrentData().getPath()+",data:"+new String(nodeCache.getCurrentData().getData()));
                
            }});
  }
}
