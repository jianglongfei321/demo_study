package com.roby.demo.spring.ioc.event;

import org.springframework.context.ApplicationEvent;
/**
 * 定义一个事件
 * @author jlf
 *
 */
public class DemoEvent extends ApplicationEvent {
	private String name;
	
	private static final long serialVersionUID = 1L;
	public DemoEvent(Object source,String name) {
		super(source);
		this.name = name;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	

}
