package com.roby.demo.zk.curator.queue;

/**
 * DistributedPriorityQueue
	优先级队列对队列中的元素按照优先级进行排序。 Priority越小，
 	元素越靠前， 越先被消费掉。 它涉及下面几个类：
	QueueBuilder
	QueueConsumer
	QueueSerializer
	DistributedPriorityQueue
	通过builder.buildPriorityQueue(minItemsBeforeRefresh)方法创建。
 	当优先级队列得到元素增删消息时，它会暂停处理当前的元素队列，然后刷新队列。
 	minItemsBeforeRefresh指定刷新前当前活动的队列的最小数量。 主要设置你的程序可以容忍的不排序的最小值。
 */
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedPriorityQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.utils.CloseableUtils;

public class DistributedPriorityQueueTest extends AbstractQueue{
  
    public static void main(String[] args) throws Exception {
        CuratorFramework client = null;
        DistributedPriorityQueue<String> queue = null;
        try {
        	client  = createClient();
            client.start();
            QueueConsumer<String> consumer = createQueueConsumer();
            QueueBuilder<String> builder = createBuilder(client,consumer);
            queue = builder.buildPriorityQueue(0);
            queue.start();

            for (int i = 0; i < 10; i++) {
                int priority = (int)(Math.random() * 100);
                System.out.println("test-" + i + " priority:" + priority);
                queue.put("test-" + i, priority);
                Thread.sleep((long)(50 * Math.random()));
            }
            Thread.sleep(20000);
        } catch (Exception ex) {

        } finally {
            CloseableUtils.closeQuietly(queue);
            CloseableUtils.closeQuietly(client);
        }
    }

    
}