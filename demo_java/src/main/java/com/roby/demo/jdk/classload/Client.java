package com.roby.demo.jdk.classload;

import java.lang.reflect.Method;

public class Client {
	
	public static void main(String[] args) throws Exception {
			 while(1==1){
				 Thread.sleep(2000);
				 // 每次都创建出一个新的类加载器
				 CustomClassLoad cl = new CustomClassLoad("F:\\学习\\学习代码\\demo_study\\target\\classes\\", new String[]{"com.jd.demo.classload.Foo"}); 
			        Class cls = cl.loadClass("com.jd.demo.classload.Foo"); 
			        Object foo = cls.newInstance(); 

			        Method m = foo.getClass().getMethod("sayHello", new Class[]{}); 
			        m.invoke(foo, new Object[]{}); 
			 }
	}

}
