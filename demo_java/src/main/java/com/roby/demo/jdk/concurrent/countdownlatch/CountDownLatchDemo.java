package com.roby.demo.jdk.concurrent.countdownlatch;
/**
 * concurrent包里面的CountDownLatch其实可以把它看作一个计数器，
 * 只不过这个计数器的操作是原子操作，同时只能有一个线程去操作这个计数器，
 * 也就是同时只能有一个线程去减这个计数器里面的值。 
 *  CountDownLatch的一个非常典型的应用场景是：有一个任务想要往下执行，
 * 但必须要等到其他的任务执行完毕后才可以继续往下执行。
 * 假如我们这个想要继续往下执行的任务调用一个CountDownLatch对象的await()方法，
 * 其他的任务执行完自己的任务后调用同一个CountDownLatch对象上的countDown()方法，
 * 这个调用await()方法的任务将一直阻塞等待，直到这个CountDownLatch对象的计数值减到0为止。 

 *  举个例子，有三个工人在为老板干活，这个老板有一个习惯，
 *  就是当三个工人把一天的活都干完了的时候，他就来检查所有工人所干的活。
 *  记住这个条件：三个工人先全部干完活，老板才检查。所以在这里用Java代码设计两个类，
    Worker代表工人，Boss代表老板，

 */
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {   
	  
    public static void main(String[] args) {   
        ExecutorService executor = Executors.newCachedThreadPool();   
        CountDownLatch latch = new CountDownLatch(3);   
        Worker w1 = new Worker(latch,"张三");   
        Worker w2 = new Worker(latch,"李四");   
        Worker w3 = new Worker(latch,"王二");   
           
       Boss boss = new Boss(latch);   
           
        executor.execute(w3);   
        executor.execute(w2);   
        executor.execute(w1);   
        executor.execute(boss);   
           
        executor.shutdown();   
    }   
  
} 