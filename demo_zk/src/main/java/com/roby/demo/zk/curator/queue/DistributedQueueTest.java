package com.roby.demo.zk.curator.queue;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.utils.CloseableUtils;
/**
 * DistributedQueue
DistributedQueue是最普通的一种队列。 它设计以下四个类：
QueueBuilder
QueueConsumer
QueueSerializer
DistributedQueue
创建队列使用QueueBuilder,它也是其它队列的创建类。

public static <T> QueueBuilder<T> builder(CuratorFramework client,
                                          QueueConsumer<T> consumer,
                                          QueueSerializer<T> serializer,
                                          java.lang.String queuePath)
QueueBuilder<MessageType>    builder = QueueBuilder.builder(client, consumer, serializer, path);
... more builder method calls as needed ...
DistributedQueue<MessageType queue = builder.build();    `
创建好queue就可以往里面放入数据了：

queue.put(aMessage);
QueueSerializer提供了对队列中的对象的序列化和反序列化。

QueueConsumer是消费者， 它可以接收队列的数据。 处理队列中的数据的代码逻辑可以放在
QueueConsumer.consumeMessage()中。

正常情况下先将消息从队列中移除，再交给消费者消费。 但这是两个步骤，不是原子的。
 可以调用Builder的lockPath()消费者加锁， 当消费者消费数据时持有锁，
 这样其它消费者不能消费此消息。 如果消费失败或者进程死掉，消息可以交给其它进程。
 这会带来一点性能的损失。 最好还是单消费者模式使用队列。

 * @author jlf
 *
 */
public class DistributedQueueTest extends AbstractQueue{
    public static void main(String[] args) throws Exception {
        CuratorFramework client = null;
        DistributedQueue<String> queue = null;
        try {client  = createClient();
        client.start();
        QueueConsumer<String> consumer = createQueueConsumer();
        QueueBuilder<String> builder = createBuilder(client,consumer);
            queue = builder.buildQueue();
            queue.start();

            for (int i = 0; i < 10; i++) {
                queue.put(" test-" + i);
                Thread.sleep((long)(3 * Math.random()));
            }

            Thread.sleep(20000);

        } catch (Exception ex) {

        } finally {
            CloseableUtils.closeQuietly(queue);
            CloseableUtils.closeQuietly(client);
        }
    }

}