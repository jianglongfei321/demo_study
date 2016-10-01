package com.roby.demo.zk.curator.leader.election;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

/**
 * Leader Election
 * Curator还提供了另外一种选举方法。 注意涉及以下四个类：
 * LeaderSelector
 * LeaderSelectorListener
 * LeaderSelectorListenerAdapter
 * CancelLeadershipException
 * 重要的是LeaderSelector类，它的构造函数为：

 * public LeaderSelector(CuratorFramework client, String mutexPath,LeaderSelectorListener listener)
 * public LeaderSelector(CuratorFramework client, String mutexPath, ThreadFactory threadFactory, Executor executor, LeaderSelectorListener listener)
 * 类似LeaderLatch,必须start: leaderSelector.start(); 一旦启动，当实例取得领导权时你的listener的takeLeadership()方法被调用. 而takeLeadership()
 * 方法只有领导权被释放时才返回。 当你不再使用LeaderSelector实例时，应该调用它的close方法。

 * 异常处理 LeaderSelectorListener类继承ConnectionStateListener.LeaderSelector必须小心连接状态的 改变.
  * 如果实例成为leader, 它应该相应SUSPENDED 或 LOST. 当 SUSPENDED 状态出现时， 实例必须假定在重新连接成功之前它可能不再是leader了。 
  * 如果LOST状态出现， 实例不再是leader， takeLeadership方法返回.

 * 重要: 推荐处理方式是当收到SUSPENDED 或 LOST时抛出CancelLeadershipException异常
 * . 这会导致LeaderSelector实例中断并取消执行takeLeadership方法的异常. 这非常重要， 
 * 你必须考虑扩展LeaderSelectorListenerAdapter. LeaderSelectorListenerAdapter提供了推荐的处理逻辑。
 */
public class ExampleClient extends LeaderSelectorListenerAdapter implements Closeable {
    private final String name;
    private final LeaderSelector leaderSelector;
    private final AtomicInteger leaderCount = new AtomicInteger();//记录此client获得领导权的次数， 它是”fair”， 每个client有平等的机会获得领导权。

    public ExampleClient(CuratorFramework client, String path, String name) {
        this.name = name;
        leaderSelector = new LeaderSelector(client, path, this);
        leaderSelector.autoRequeue();// autoRequeue.set(true);保证在此实例释放领导权之后还可能获得领导权
    }
    public void start() throws IOException {
        leaderSelector.start();
    }

    public void close() throws IOException {
        leaderSelector.close();
    }

    public void takeLeadership(CuratorFramework client) throws Exception {
        final int waitSeconds = (int) (5 * Math.random()) + 1;
        System.out.println(name + " is now the leader. Waiting " + waitSeconds + " seconds...");
        System.out.println(name + " has been leader " + leaderCount.getAndIncrement() + " time(s) before.");
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
        } catch (InterruptedException e) {
            System.err.println(name + " was interrupted.");
            Thread.currentThread().interrupt();//线程终止
        } finally {
            System.out.println(name + " relinquishing leadership.\n");
        }
    }
}