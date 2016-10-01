package com.roby.demo.jdk.filter;

/**
 * 过滤器接口，需要执行的方法
 *
 */
public interface Filter {
	Response invoke(Request request);
}
