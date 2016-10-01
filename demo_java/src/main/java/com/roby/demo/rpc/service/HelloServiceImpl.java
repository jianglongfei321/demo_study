package com.roby.demo.rpc.service;

public class HelloServiceImpl implements HelloService {

	public String hello(String name) {
		return "echo:"+name;
	}

}
