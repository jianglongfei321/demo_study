package com.roby.demo.spring.ioc.event;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
@Component("pulishDemoEvent")
public class PulishDemoEvent implements ApplicationContextAware,ApplicationEventPublisherAware {
	ApplicationEventPublisher applicationEventPublisher;
	ApplicationContext context;
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	public void publish(){
		DemoEvent event = new DemoEvent(this,"测试事件!");
		System.out.println("--------context------"+context);
		applicationEventPublisher.publishEvent(event);
	}
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		  this.context = context;
		
	}
}
