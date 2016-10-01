package com.roby.demo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Slf4j {
	
	static Logger logger = LoggerFactory.getLogger(Slf4j.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 
	   if(logger.isTraceEnabled()){
			 logger.trace("---This is Slf4j trace message---");
		}
		 if(logger.isDebugEnabled()){
			 logger.debug("This is Slf4j  debug message");
		}
		if(logger.isInfoEnabled()){
			logger.info("This is Slf4j info message");
		}
		if(logger.isWarnEnabled()){
			 logger.warn("This is Slf4j warn message");
		}
		if(logger.isErrorEnabled()){
			logger.error("This is Slf4j error message");
		}
        
       
        
       

}

}
