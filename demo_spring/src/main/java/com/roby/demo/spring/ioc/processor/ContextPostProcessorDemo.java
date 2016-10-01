package com.roby.demo.spring.ioc.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
//@Component
public class ContextPostProcessorDemo implements BeanFactoryPostProcessor {

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {
		  System.out.println("本程序对spring所做的beanfactory的初始化没有意见。。。。");
		  System.out.println("spring容器是："+beanFactory);
		
	}}
