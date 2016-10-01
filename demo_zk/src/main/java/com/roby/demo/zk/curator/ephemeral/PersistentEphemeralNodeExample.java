package com.roby.demo.zk.curator.ephemeral;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode.Mode;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;

public class PersistentEphemeralNodeExample {
    private static final String PATH = "/example2/ephemeralNode";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = null;
        PersistentEphemeralNode node = null;
        try {
            client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
            client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                public void stateChanged(CuratorFramework client, ConnectionState newState) {
                    System.out.println("client state:" + newState.name());
                }
            });
            client.start();
            
           client.create().withMode(CreateMode.EPHEMERAL).forPath(PATH,"I love messi".getBytes());
           byte[] bs = client.getData().forPath(PATH);  
            System.out.println("新建的节点，data为:" + new String(bs));  
            
            node = new PersistentEphemeralNode(client, Mode.EPHEMERAL,PATH, "test".getBytes());
            node.start();
            node.waitForInitialCreate(1, TimeUnit.SECONDS);
            String actualPath = node.getActualPath();
            System.out.println("node " + actualPath + " value: " + new String(client.getData().forPath(actualPath)));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(node);
            CloseableUtils.closeQuietly(client);
           // CloseableUtils.closeQuietly(server);
        }

    }

}