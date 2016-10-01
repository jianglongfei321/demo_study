package com.roby.demo.jdk.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DoorManager {
	
	 private List<DoorListener> listeners;
	 
	 public void addListener(DoorListener listener){
		 if (listeners == null) {
	            listeners = new ArrayList();
	        }
	        listeners.add(listener);
	 }
	 
	 /**
	     * 移除事件
	     * 
	     * @param listener
	     *            DoorListener
	     */
	    public void removeDoorListener(DoorListener listener) {
	        if (listeners == null)
	            return;
	        listeners.remove(listener);
	    }
	    
	    /**
	     * 通知所有的DoorListener
	     */
	    private void notifyListeners(DoorEvent event) {
	        Iterator iter = listeners.iterator();
	        while (iter.hasNext()) {
	            DoorListener listener = (DoorListener) iter.next();
	            listener.doorEvent(event);
	        }
	    }
	    /**
	     * 触发开门事件
	     */
	    protected void fireWorkspaceOpened() {
	        if (listeners == null)
	            return;
	        DoorEvent event = new DoorEvent(this, "open");
	        notifyListeners(event);
	    }

	    /**
	     * 触发关门事件
	     */
	    protected void fireWorkspaceClosed() {
	        if (listeners == null)
	            return;
	        DoorEvent event = new DoorEvent(this, "close");
	        notifyListeners(event);
	    }
	    
	    public int size(){  
	        return listeners.size();  
	    }  
	    public void removeAllListener(){  
	        listeners.clear();  
	    } 

}
