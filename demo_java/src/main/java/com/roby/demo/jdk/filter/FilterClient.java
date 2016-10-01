package com.roby.demo.jdk.filter;

public class FilterClient {
	
	public static void main(String[] args) {
		HandlerFilter handlerFilter = new HandlerFilter();
		FilterChain filterChain = new FilterChain();
		Request request = new Request();
		request.setHead("头信息");
		request.setBody("传输信息");
		filterChain.create(handlerFilter).invoke(request);
	}
}
