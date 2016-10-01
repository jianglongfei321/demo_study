package com.roby.demo.log.appender;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class ImAppender extends AppenderSkeleton{
	private String account ;  

@Override  
protected void append(LoggingEvent event) {  
    System.out.println("Hello, " + account + " : "+ event.getMessage());  
}  

public String getAccount() {  
    return account;  
}  

public void setAccount(String account) {  
    this.account = account;  
}

public void close() {
	
}

public boolean requiresLayout() {
	return false;
}  }
