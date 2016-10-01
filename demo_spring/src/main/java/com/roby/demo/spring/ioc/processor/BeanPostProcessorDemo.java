package com.roby.demo.spring.ioc.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.roby.demo.spring.ioc.User;
//@Component
public class BeanPostProcessorDemo implements BeanPostProcessor {

	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if(bean instanceof User){
			System.out.println("-------postProcessAfterInitialization-----"+bean);
		}
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if(bean instanceof User){
			System.out.println("-------postProcessBeforeInitialization-----"+bean);
		}
		return bean;
	}

}
