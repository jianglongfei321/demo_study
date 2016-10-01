package com.roby.demo.spring.ioc.test;

import org.junit.Test;

import com.roby.demo.spring.ApplicationUtil;
import com.roby.demo.spring.ioc.InitBean;
import com.roby.demo.spring.ioc.User;

public class BeanCreateClient {
	/**
	 * 创建工厂BEAN
	 */
	@Test
	public  void createFactoryBean() {
		User user = (User)ApplicationUtil.getObject("demo");
		System.out.println(user.getUsername());
		User userStatic = (User)ApplicationUtil.getObject("staticDemo");
		System.out.println(userStatic.getUsername());
		
		InitBean initBean = (InitBean)ApplicationUtil.getObject("initBean");
		
	}
	/**
	 * initBean
	 */
	@Test
	public  void testInit() {
		InitBean initBean = (InitBean)ApplicationUtil.getObject("initBean");
		ApplicationUtil.close();
		
	}

}
