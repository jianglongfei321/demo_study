package com.roby.demo.jdk.concurrent.threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FixedThreadPool {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(15);
		/******************Runnable和*Callable的区别********************************/
		/******************execute方法和*submit方法的区别********************************/
		Runnable runnable = new Runnable(){
			public void run() {
				System.out.println("start thread index");
			}
		};
		Callable callable = new Callable(){
			public Object call() throws Exception {
				return 1;
			}
		};
		 executor.submit(runnable);
		@SuppressWarnings("unchecked")
		Future future2 = executor.submit(callable);
		
		executor.execute(runnable);
		System.out.println(future2.get());
	}
}
