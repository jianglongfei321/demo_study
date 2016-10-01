package com.roby.demo.spring.aop;

public @interface Spi {
	public String value() default "aa";
}
