package com.roby.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 采用JDK的动态代理处理器
 * @author jlf
 *
 */
public class JdkProxy implements InvocationHandler{
	private Object object;

	public JdkProxy(Object object) {
		super();
		this.object = object;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return method.invoke(object, args);
	}
	
	public static TaskService createProxy(Object object){
		TaskService jdkProxy = (TaskService) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),  
                new Class[] { TaskService.class }, new JdkProxy(object));
		return jdkProxy;  
	}
}
