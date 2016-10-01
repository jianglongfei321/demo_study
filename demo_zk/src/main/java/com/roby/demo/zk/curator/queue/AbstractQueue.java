package com.roby.demo.zk.curator.queue;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.recipes.queue.QueueSerializer;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class AbstractQueue {

	  static final String PATH = "/example/queue";
	  public static CuratorFramework createClient(){
		  CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
          client.getCuratorListenable().addListener(new CuratorListener() {
              public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                  System.out.println("CuratorEvent: " + event.getType().name());
              }
          });
          return client;
	  }
	  static  QueueBuilder<String> createBuilder(CuratorFramework client,QueueConsumer<String> consumer){
		  return QueueBuilder.builder(client, consumer, createQueueSerializer(), PATH);
	  }
	   static QueueSerializer<String> createQueueSerializer() {
	        return new QueueSerializer<String>(){

	            public byte[] serialize(String item) {
	                return item.getBytes();
	            }

	            public String deserialize(byte[] bytes) {
	                return new String(bytes);
	            }

	        };
	    }

	     static QueueConsumer<String> createQueueConsumer() {

	        return new QueueConsumer<String>(){

	            public void stateChanged(CuratorFramework client, ConnectionState newState) {
	                System.out.println("connection new state: " + newState.name());
	            }

	            public void consumeMessage(String message) throws Exception {
	                System.out.println("consume one message: " + message);                
	            }

	        };
	    }
}
