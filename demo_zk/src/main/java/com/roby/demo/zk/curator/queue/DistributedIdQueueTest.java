package com.roby.demo.zk.curator.queue;
/**
 * DistributedIdQueue
DistributedIdQueue和上面的队列类似， 但是可以为队列中的每一个元素设置一个ID。
 可以通过ID把队列中任意的元素移除。 它涉及几个类：

QueueBuilder
QueueConsumer
QueueSerializer
DistributedQueue
 */
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedIdQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.utils.CloseableUtils;

public class DistributedIdQueueTest extends AbstractQueue{

    public static void main(String[] args) throws Exception {
        CuratorFramework client = null;
        DistributedIdQueue<String> queue = null;
        try {
        	client  = createClient();
            client.start();
            QueueConsumer<String> consumer = createQueueConsumer();
            QueueBuilder<String> builder = createBuilder(client,consumer);
            queue = builder.buildIdQueue();
            queue.start();

            for (int i = 0; i < 10; i++) {
                queue.put(" test-" + i, "Id" + i);
                Thread.sleep((long)(20 * Math.random()));
                queue.remove("Id" + i);
            }
            Thread.sleep(20000);
        } catch (Exception ex) {
        } finally {
            CloseableUtils.closeQuietly(queue);
            CloseableUtils.closeQuietly(client);
        }
    }

}