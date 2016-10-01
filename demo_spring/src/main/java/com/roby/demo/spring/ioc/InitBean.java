package com.roby.demo.spring.ioc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
//@Component("initBean")
public class InitBean implements InitializingBean,DisposableBean{
	InitBean(){
		System.out.println("---------Init构造函数-------");
	}
	public void afterPropertiesSet() throws Exception {
		System.out.println("---------afterPropertiesSet-------");
	}
	public void destroy() throws Exception {
		System.out.println("---------destroy-------");
	}
	/**
	 * 
	 */
	
	//@PostConstruct
	public void postConstruct(){
		System.out.println("---------@PostConstruct-------");
	}
	//@PreDestroy
	public void preDestroy(){
		System.out.println("---------@PreDestroy-------");
	}
	
	
	public void initMethod(){
		System.out.println("---------initMethod-------");
	}
	public void destroyMethod(){
		System.out.println("---------destroyMethod-------");
	}
}
