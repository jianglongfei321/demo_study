package com.roby.demo.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Log4j2 {
	
	static Logger logger = LogManager.getLogger(Log4j2.class.getName());    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		    logger.debug("This is debug message");
	        logger.info("This is info message");
	        logger.warn("This is warn message");
	        logger.error("This is error message");
	        logger.fatal("This is fatal message");
	        logger.trace("This is trace message");

	}

}
