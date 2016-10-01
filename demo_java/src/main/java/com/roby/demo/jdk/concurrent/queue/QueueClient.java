package com.roby.demo.jdk.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class QueueClient {
	
	public static void main(String[] args) throws InterruptedException {
		ArrayBlockingQueue<Integer> queue= new ArrayBlockingQueue<Integer>(5);
		queue.add(1);//增加一个元索 如果队列已满，则抛出一个IIIegaISlabEepeplian异常
		queue.add(2);
		queue.add(3);
		queue.put(4);//添加一个元素如果队列满，则阻塞
		for(int i = 5;i<9;i++){
			System.out.println(queue.offer(4));// 添加一个元素并返回true 如果队列已满，则返回false
		}
		
		System.out.println("------take()--"+queue.take());//移除并返回队列头部的元素,如果队列为空，则阻塞
		System.out.println("------remove()--"+queue.remove());// 移除并返回队列头部的元素,如果队列为空，则抛出一个NoSuchElementException异常
		System.out.println("------poll()--"+queue.poll());//移除并返问队列头部的元素,如果队列为空，则返回null
		System.out.println("------element()--"+queue.element());//返回队列头部的元素,如果队列为空，则抛出一个NoSuchElementException异常
		System.out.println("------peek()--"+queue.peek());//返回队列头部的元素,如果队列为空，则返回null
		//offer与poll与peek是一组 ，可能成功可能失败
	}
}