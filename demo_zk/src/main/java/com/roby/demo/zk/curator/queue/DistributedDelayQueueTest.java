package com.roby.demo.zk.curator.queue;

/**
 * DistributedDelayQueue
JDK中也有DelayQueue，不知道你是否熟悉。 DistributedDelayQueue也提供了类似的功能， 元素有个delay值， 消费者隔一段时间才能收到元素。 涉及到下面四个类。

QueueBuilder
QueueConsumer
QueueSerializer
DistributedDelayQueue
 */
import java.util.Date;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedDelayQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.utils.CloseableUtils;

public class DistributedDelayQueueTest extends AbstractQueue{

    public static void main(String[] args) throws Exception {
        CuratorFramework client = null;
        DistributedDelayQueue<String> queue = null;
        try {
            client  = createClient();
            client.start();
            QueueConsumer<String> consumer = createQueueConsumer();
            QueueBuilder<String> builder = createBuilder(client,consumer);
            queue = builder.buildDelayQueue();
            queue.start();
            for (int i = 0; i < 10; i++) {
                queue.put("test-" + i, System.currentTimeMillis() + 10000);
            }
            System.out.println(new Date().getTime() + ": already put all items");
            Thread.sleep(20000);

        } catch (Exception ex) {

        } finally {
            CloseableUtils.closeQuietly(queue);
            CloseableUtils.closeQuietly(client);
        }
    }

}
