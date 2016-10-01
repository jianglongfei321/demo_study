package com.roby.demo.spring.aop;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.roby.demo.spring.ApplicationUtil;

public class TestAspect {
	TestService testService ;
	@Before
	public void init(){
		testService = (TestService)ApplicationUtil.getObject("testService");
		
	}
	/**
	 * initBean
	 */
	@Test
	public  void testInit() {
		testService.echo("hello world!");
	}
	@Test
	public  void testAnnotation() {
		testService.testAnnotation("hello world!");
	}
	@Test
	public  void testArgs() {
		testService.testArgs(new HashMap<Object, Object>());
	}
	@Test
	public  void testJoinPoint() {
		testService.joinPoint("hello world!");
	}
}
