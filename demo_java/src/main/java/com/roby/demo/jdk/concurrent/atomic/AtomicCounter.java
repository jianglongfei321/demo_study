package com.roby.demo.jdk.concurrent.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Runnable {
	static  AtomicInteger count=new AtomicInteger(0);

	public void run() {
            //成功后才会返回期望值，否则无线循环  
		System.out.println(Thread.currentThread().getName() + ":" + count.getAndAdd(1));
	}
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++) {
			es.execute(new AtomicCounter());
		}
	}
}
