package com.roby.demo.spring.ioc.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DemoApplicationListenter implements ApplicationListener<DemoEvent> {
	public void onApplicationEvent(DemoEvent demoEvent) {
		System.out.println("---DemoApplicationListenter--"+demoEvent.getName());
		
	}

}
