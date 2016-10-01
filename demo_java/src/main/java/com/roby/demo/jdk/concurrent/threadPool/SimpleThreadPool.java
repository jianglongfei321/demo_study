package com.roby.demo.jdk.concurrent.threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleThreadPool {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newFixedThreadPool(15);
		ExecutorService executor2 = Executors.newCachedThreadPool();
		ExecutorService executor3 = Executors.newSingleThreadExecutor();
		scheduled( Executors.newScheduledThreadPool(11));
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
	/***
	 * 定时调用
	 * @param executor
	 */
  public static void scheduled(ScheduledExecutorService executor){
	  /***
	    * command：执行线程
		* initialDelay：初始化延时
		* period：两次开始执行最小间隔时间
		* unit：计时单位
	   */
	  Runnable command  = new Runnable(){
			public void run() {
				 try {  
			            Thread.sleep(50);  
			        } catch (InterruptedException e) {  
			            e.printStackTrace();  
			        }  
			        System.out.println("This is a echo server. The current time is " +  
			                System.currentTimeMillis() + ".");  
			}
		};
		int delay = 1;
		TimeUnit unit = TimeUnit.SECONDS;
		int initialDelay = 5;
		int period = 1;
	    executor.scheduleAtFixedRate(command, initialDelay, period, unit);//周期定时执行某个任务。
	    //executor.scheduleWithFixedDelay(command, initialDelay, delay, unit);//按指定频率间隔执行某个任务。
	    /***
	     * 	  
	     *  scheduleWithFixedDelay从字面意义上可以理解为就是以固定延迟（时间）来执行线程任务，它实际上是不管线程任务的执行时间的，
	     *  每次都要把任务执行完成后再延迟固定时间后再执行下一次。
			而scheduleFixedRate呢，是以固定频率来执行线程任务，固定频率的含义就是可能设定的固定时间不足以完成线程任务，但是它不管，
			达到设定的延迟时间了就要执行下一次了。
	     */
}
}
