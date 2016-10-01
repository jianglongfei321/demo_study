package com.roby.demo.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Log4j {
	
	static Logger logger = Logger.getLogger(Log4j.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		    logger.setLevel(Level.WARN);
		    logger.debug("This is debug message");
	        logger.info("This is info message");
	        logger.warn("This is warn message");
	        logger.error("This is error message");
	        logger.fatal("This is fatal message");
	        logger.trace("This is trace message");

	}

}
