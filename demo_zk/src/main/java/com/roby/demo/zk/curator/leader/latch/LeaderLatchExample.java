package com.roby.demo.zk.curator.leader.latch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;

/**
 * 构造函数如下：
 * public LeaderLatch(CuratorFramework client, String latchPath)
 * public LeaderLatch(CuratorFramework client, String latchPath,  String id)
 * 必须启动LeaderLatch: leaderLatch.start(); 
 * 一旦启动， LeaderLatch会和其它使用相同latch path的其它LeaderLatch交涉，
 * 然后随机的选择其中一个作为leader。 你可以随时查看一个给定的实例是否是leader:
 * 一旦不使用LeaderLatch了，必须调用close方法。 如果它是leader,会释放leadership， 其它的参与者将会选举一个leader。

 * 异常处理 LeaderLatch实例可以增加ConnectionStateListener来监听网络连接问题。
 * 当 SUSPENDED 或 LOST 时, leader不再认为自己还是leader.
 *  public boolean hasLeadership()
 * 当LOST 连接重连后 RECONNECTED,LeaderLatch会删除先前的ZNode然后重新创建一个.
 *  LeaderLatch用户必须考虑导致leadershi丢失的连接问题。 强烈推荐你使用ConnectionStateListener。
 
 * 首先我们创建了10个LeaderLatch，启动后它们中的一个会被选举为leader。
 * 因为选举会花费一些时间，start后并不能马上就得到leader。 
 * 通过hasLeadership查看自己是否是leader， 如果是的话返回true。 
 * 可以通过.getLeader().getId()可以得到当前的leader的ID。
 * 只能通过close释放当前的领导权。 await是一个阻塞方法， 
 * 尝试获取leader地位，但是未必能上位。
 * 
 * 
 * 你首先要创建CuratorFramework，然后并启动它，一个CuratorFramework就是一个ZooKeeper客户端。
 * 然后创建LeaderLatch，并制定刚才创建的CuratorFramework和一个leaderPath，
 * leaderPath是一个ZooKeepe路径，node0，node1，node2中的leaderPath必须一致。
 * 创建好LeaderLatch之后，需要为他注册一个LeaderLatchListener回掉，
 * 如果某个node成为leader，
 * 那么会调用这个node的LeaderLatchListener的isLeader()，
 * 因此你可以在这里写自己的业务逻辑。最后，
 * 调用LeaderLatch的start()，这个LeaderLatch将参加选举了。
 */
public class LeaderLatchExample {
    private static final int CLIENT_QTY = 10;
    private static final String PATH = "/examples/leader";
    static List<CuratorFramework> clients = Lists.newArrayList();
    static  List<LeaderLatch> examples = Lists.newArrayList();
    public static void main(String[] args) throws Exception {
        try {
            for (int i = 0; i < CLIENT_QTY; ++i) {
                CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
                clients.add(client);
                LeaderLatch example = new LeaderLatch(client, PATH, "Client #" + i);
                example.addListener(new LeaderLatchListener(){
                    public void isLeader() {
                        System.out.println("--I am Leader");
                    }
                    public void notLeader() {
                        System.out.println("I am not Leader");
                    }});
                  
                examples.add(example);
                client.start();
                example.start();
            }
            Thread.sleep(10000);
            LeaderLatch currentLeader = null;
            for (int i = 0; i < CLIENT_QTY; ++i) {
                LeaderLatch example = examples.get(i);
                if (example.hasLeadership())
                    currentLeader = example;
            }
            System.out.println("current leader is " + currentLeader.getId());
            System.out.println("release the leader " + currentLeader.getId());
            currentLeader.close();//当前master关闭后
            examples.get(0).await(2, TimeUnit.SECONDS);//阻塞
            System.out.println("Client #0 maybe is elected as the leader or not although it want to be");
            System.out.println("the new leader is " + examples.get(3).getLeader().getId());

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Shutting down...");
            for (LeaderLatch exampleClient : examples) {
                CloseableUtils.closeQuietly(exampleClient);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
        }
    }
}
/**
LeaderLatch在Master-Slave中的应用

在一个典型的master-slave场景下。你可以在isLeader中做如下处理:

  1.每一个master类都有一个state属性，初始值为standby.

  2.在isLeader中，从持久话引擎中读取要恢复的数据到一个临时的内存缓存中

  3.将这个master的state修改为recovering

  4.通知所有worker将其内部的master修改为当前master。

  5.将临时内存缓存中的数据恢复到master内部。

  6.将master状态修改为alive，然后这个master就可以对外服务了。

注意第5步，由于将持久话引擎中的数据添加到了master内部的内存中，所以需要确保之多恢复一次语义。
**/