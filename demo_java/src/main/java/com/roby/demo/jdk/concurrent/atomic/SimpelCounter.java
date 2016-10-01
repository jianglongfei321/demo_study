package com.roby.demo.jdk.concurrent.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpelCounter extends Thread{
	static  volatile int count = 0;
	public void run() {  
        System.out.println(Thread.currentThread().getName()   
                + ":" + (++count));  
    }  
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newFixedThreadPool(5);
		for(int i = 0;i<5;i++){
			es.execute(new SimpelCounter());
		}
	}
}
