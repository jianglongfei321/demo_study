package com.roby.demo.spring.ioc;

import org.springframework.beans.factory.FactoryBean;

public class FactoryBeanDemo implements FactoryBean{
	public Object getObject() throws Exception {
		 User user = new User();
		 user.setUsername("工厂bean");
		return user;
	}
	public Class getObjectType() {
		return User.class;
	}
	public boolean isSingleton() {
		return false;
	}
	
}
