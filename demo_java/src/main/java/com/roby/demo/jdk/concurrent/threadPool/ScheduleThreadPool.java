package com.roby.demo.jdk.concurrent.threadPool;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/***
    * command：执行线程
	* initialDelay：初始化延时
	* period：两次开始执行最小间隔时间
	* unit：计时单位
    *  scheduleWithFixedDelay从字面意义上可以理解为就是以固定延迟（时间）来执行线程任务，
    *  它实际上是不管线程任务的执行时间的，
    *  每次都要把任务执行完成后再延迟固定时间后再执行下一次。
    *  而scheduleFixedRate呢，是以固定频率来执行线程任务，固定频率的含义就是可能设定的固定
    *  时间不足以完成线程任务，但是它不管，
    *  达到设定的延迟时间了就要执行下一次了。
    *   executor.scheduleAtFixedRate(command, initialDelay, period, unit);//周期定时执行某个任务。
    *   executor.scheduleWithFixedDelay(command, initialDelay, delay, unit);//按指定频率间隔执行某个任务。
 */
public class ScheduleThreadPool {
	final static TimeUnit unit = TimeUnit.SECONDS;
	final static int initialDelay = 5;
	final static int period = 1;
	public static void main(String[] args) throws Exception {
		ScheduledExecutorService executor =  Executors.newScheduledThreadPool(11);
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
		    executor.scheduleAtFixedRate(command, initialDelay, period, unit);
		    //executor.scheduleWithFixedDelay(command, initialDelay, delay, unit);
		  

	}	    
}
