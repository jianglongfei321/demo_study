package com.roby.demo.proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import net.sf.cglib.proxy.MethodProxy;


/**
 * Javassist库实现的动态代理
 * @author jlf
 *
 */
public class JavassistProxy implements MethodHandler {
	private Object object;

	public JavassistProxy(Object object) {
		this.object = object;
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy mp) throws Throwable {
		return method.invoke(object, args);
	}

	public Object invoke(Object arg0, Method method, Method arg2, Object[] args)
			throws Throwable {
		return method.invoke(object, args);
	}
	
	public static TaskService createProxy(final  Object object) throws Exception{
		
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setInterfaces(new Class[]{TaskService.class});
		Class proxyClass = proxyFactory.createClass();
		TaskService javassistProxy  = (TaskService)proxyClass.newInstance();
		((ProxyObject) javassistProxy).setHandler(new JavassistProxy(object));  
	     return javassistProxy;  
	}
}
