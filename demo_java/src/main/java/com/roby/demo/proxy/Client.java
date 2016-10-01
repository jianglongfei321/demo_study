package com.roby.demo.proxy;



public class Client {
	public static void main(String[] args) throws Exception {
	    TaskService taskService = new TaskServiceImpl();
		System.out.println(JdkProxy.createProxy(taskService).echo("test"));
		System.out.println(CglibProxy.createProxy(taskService).echo("test"));
		System.out.println(JavassistProxy.createProxy(taskService).echo("test"));
		System.out.println(JavassistByteProxy.createProxy().echo("test"));
		//System.out.println(AsmByteProxy.createProxy(taskService).echo("test"));
	}
}
