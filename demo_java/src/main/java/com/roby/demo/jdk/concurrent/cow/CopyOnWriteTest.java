package com.roby.demo.jdk.concurrent.cow;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteTest {
	
	public static void main(String[] args) {
		CopyOnWriteArrayList list = new CopyOnWriteArrayList(); //CopyOnWriteArraySet
		System.out.println(list.hashCode());
		list.add("121");
		System.out.println(list.hashCode());
		
		
	}

}
