package com.roby.demo.netty.callback;

public class MyHandler implements Handler{

	public void handle(Callback callBack) {
		
		System.out.println("-------业务开始-------------");
		callBack.invoke();
		System.out.println("-------业务结束------------");
	}

}
