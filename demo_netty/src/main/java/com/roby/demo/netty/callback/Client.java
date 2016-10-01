package com.roby.demo.netty.callback;

public class Client {
	
	public static void main(String[] args) {
		Handler my = new MyHandler();
		my.handle(new Callback(){
			public void invoke() {
				System.out.println("fdfdfd");
			}
			
		});
	}

}
