package com.roby.demo.spring.ioc;
/**
 * 静态工厂创建Bean factory-method指定方法名
 * @author jlf
 *
 */
public class StaticFactoryBeanDemo{
	public static User createInstance(){
		 User user = new User();
		 user.setUsername("静态工厂创建");
		return user;
	}
}
