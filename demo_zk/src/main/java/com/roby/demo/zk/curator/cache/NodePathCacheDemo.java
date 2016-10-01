package com.roby.demo.zk.curator.cache;

import java.util.Random;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
public class NodePathCacheDemo {
  public static void main(String[]args) throws Exception{
      CuratorFramework client=null;
      TreeCache treeNodeCache= null;
      String path="/francis/nodecache/cc"+new Random().nextInt(10000);
      try{ 
         client= CuratorFrameworkFactory.newClient("127.0.0.1:2181",new ExponentialBackoffRetry(1000,3));
         client.start();
         treeNodeCache=new TreeCache(client,path);
         treeNodeCache.start();
         treeNodeCache.getListenable().addListener(new TreeCacheListener(){
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
            	System.out.println("---event.getType()--"+event.getType());
                switch(event.getType()){
                  case NODE_ADDED:
                      System.out.println("added:"+event.getData().getPath()+client.getData());
                      break;
                  case NODE_UPDATED:
                      System.out.println("updated:"+event.getData().getPath());
                      break;
                  case NODE_REMOVED: 
                      System.out.println("removed:"+event.getData().getPath());
                      break;
                  default:
                      System.out.println("other:"+event.getType());
                }
               
           }
         });
         Thread.sleep(1000);
         //创建父节点
         client.create().creatingParentsIfNeeded().////如果指定的节点的父节点不存在，递归创建父节点
         withMode(CreateMode.PERSISTENT).forPath(path,"init".getBytes());
         Thread.sleep(10000);
         System.out.println("创建父节点:"+path);
         //创建子节点
         String childPath1=ZKPaths.makePath(path, "ab");
         childPath1=client.create().withMode(CreateMode.PERSISTENT).forPath(childPath1,"1".getBytes());
         System.out.println("创建子节点:"+childPath1);
         Thread.sleep(10000);
         //对子节点赋值
         client.setData().forPath(childPath1,"aaabb".getBytes());
         System.out.println("对子节点赋值:"+childPath1);
         Thread.sleep(10000);
         //对子节点赋值
         client.setData().forPath(path,"aaabb".getBytes());
         System.out.println("对子节点赋值:"+path);
         Thread.sleep(2000);
         
         //对子节点赋值
         client.setData().forPath(path,"aaabb".getBytes());
         System.out.println("对子节点赋值:"+path);
         Thread.sleep(2000);
         //对子节点赋值
         client.setData().forPath(path,"aaabb".getBytes());
         System.out.println("对子节点赋值:"+path);
         Thread.sleep(2000);
         //对子节点赋值
         client.setData().forPath(path,"aaabb".getBytes());
         System.out.println("对子节点赋值:"+path);
         Thread.sleep(2000);
         
        client.delete().forPath(childPath1);
         System.out.println("删除子节点:"+path);
         client.delete().deletingChildrenIfNeeded().forPath("/francis");
      }catch(Exception e){
          e.printStackTrace();
      }finally{
          //这里因为是测试，没有加他们。
    	  CloseableUtils.closeQuietly(treeNodeCache);
          CloseableUtils.closeQuietly(client);
      }     
  }
}
