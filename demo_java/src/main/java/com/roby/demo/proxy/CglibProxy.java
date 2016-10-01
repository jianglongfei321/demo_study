package com.roby.demo.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


/**
 * 方法执行的拦截器
 * @author jlf
 *
 */
public class CglibProxy implements MethodInterceptor{
	private Object object;

	public CglibProxy(Object object) {
		this.object = object;
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		return method.invoke(object, args);
	}
	public static TaskService createProxy(Object object){
		 Enhancer enhancer = new Enhancer();  
		 enhancer.setCallback(new CglibProxy(object)); 
		 enhancer.setInterfaces(new Class[] {TaskService.class });  
		 TaskService cglibProxy = (TaskService) enhancer.create();  
	     return cglibProxy;  
	}
}
