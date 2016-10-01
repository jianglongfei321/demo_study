package com.roby.demo.jdk.concurrent.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanTest implements Runnable {
	static  AtomicBoolean flag=new AtomicBoolean(false);

	public void run() {
		
		System.out.println(flag.get()+"---"+flag.getAndSet(true));
	}
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++) {
			es.execute(new AtomicBooleanTest());
		}
	}
}
