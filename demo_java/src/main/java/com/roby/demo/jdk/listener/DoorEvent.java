package com.roby.demo.jdk.listener;

import java.util.EventObject;

public class DoorEvent extends EventObject{
	private static final long serialVersionUID = 1L;
	 private String doorState = "";// 表示门的状态，有“开”和“关”两种
	 
	public DoorEvent(Object source, String doorState) {
		super(source);
		this.doorState = doorState;
	}

	public String getDoorState() {
		return doorState;
	}
	public void setDoorState(String doorState) {
		this.doorState = doorState;
	}

}
