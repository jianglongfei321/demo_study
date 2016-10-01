package com.roby.demo.zk.curator.queue;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

public class MyDisbutedQueue {
	static DistributedQueue<String> queue = null;
	static CuratorFramework client = null;
	public static void main(String[] args) {
		try {
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
			client =CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
			 client.getCuratorListenable().addListener(new CuratorListener() {
	              public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
	                  System.out.println("CuratorEvent: " + event.getType().name());
	              }
	          });
			client.start();
			QueueConsumer<String> consumer = createConsumer();
			 QueueBuilder<String> builder = QueueBuilder.builder(client, consumer, createQueueSerializer(), "/c00/sa");
			 queue =  builder.buildQueue();
			 queue.start();
			 
			 for(int i = 0;i<10;i++){
				 queue.put("queue:"+i);
				 Thread.sleep(1000);
			 }
		} catch (Exception e) {
			
		}finally{
			CloseableUtils.closeQuietly(client);
			CloseableUtils.closeQuietly(queue);
		}
		 
	}
	private static QueueSerializer<String> createQueueSerializer() {
		return new QueueSerializer<String>(){

			public byte[] serialize(String item) {
				return item.getBytes();
			}
			public String deserialize(byte[] bytes) {
				return new String(bytes);
			}
		};
	}
	private static QueueConsumer<String> createConsumer(){
		 return  new QueueConsumer<String>(){
			public void stateChanged(CuratorFramework client,
					ConnectionState newState) {
			}
			public void consumeMessage(String message) throws Exception {
				System.out.println(" this is consumner:"+message);
			}
			
		};
	}
}
