package com.roby.demo.spring.ioc.test;

import org.junit.Test;

import com.roby.demo.spring.ApplicationUtil;
import com.roby.demo.spring.ioc.event.PulishDemoEvent;

public class EventClient {
	/**
	 * 创建工厂BEAN
	 */
	@Test
	public  void testEvent() {
		PulishDemoEvent event = (PulishDemoEvent)ApplicationUtil.getObject("pulishDemoEvent");
		event.publish();
		
	}
	
}
