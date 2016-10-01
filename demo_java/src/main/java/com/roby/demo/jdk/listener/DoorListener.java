package com.roby.demo.jdk.listener;

import java.util.EventListener;

public interface DoorListener extends EventListener{

	public void doorEvent(DoorEvent doorEvent);  
	
}
