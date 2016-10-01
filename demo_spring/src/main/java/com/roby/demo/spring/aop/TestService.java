package com.roby.demo.spring.aop;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component("testService")
public class TestService {
	
	public String joinPoint(String msg){
		msg = "echo:"+msg;
		System.out.println(msg);
		return msg;
	}
	public String echo(String msg){
		msg = "echo:"+msg;
		System.out.println(msg);
		return msg;
	}
	@Spi
	public String testAnnotation(String msg){
		msg = "echo annotation:"+msg;
		System.out.println(msg);
		return msg;
	}
	public Map testArgs(Map map){
		System.out.println("echo:"+map);
		return map;
	}
}
