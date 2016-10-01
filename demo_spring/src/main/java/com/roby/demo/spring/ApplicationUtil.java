package com.roby.demo.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationUtil {
	static ApplicationContext ac = null;
	static{
		ac = new ClassPathXmlApplicationContext("spring/spring.xml");
	}
	public static Object getObject(String beanName){
		return ac.getBean(beanName);
	}
	public static void close(){
		((AbstractApplicationContext) ac).registerShutdownHook();
	}
	
}
