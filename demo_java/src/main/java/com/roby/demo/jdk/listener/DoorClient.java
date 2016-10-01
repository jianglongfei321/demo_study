package com.roby.demo.jdk.listener;

public class DoorClient {
    public static void main(String[] args) {
        DoorManager manager = new DoorManager();
        manager.addListener(new DoorListener(){
			public void doorEvent(DoorEvent doorEvent) {
				if (doorEvent.getDoorState() != null && doorEvent.getDoorState().equals("open")) {
		            System.out.println("门1打开");
		        } else {
		            System.out.println("门1关闭");
		        }
			}
        });
        // 开门
        manager.fireWorkspaceOpened();
        System.out.println("我已经进来了");
        // 关门
        manager.fireWorkspaceClosed();
    }
}