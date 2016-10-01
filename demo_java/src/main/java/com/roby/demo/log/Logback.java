package com.roby.demo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logback {
	
	static Logger logger = LoggerFactory.getLogger(Slf4j.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		    logger.debug("This is debug message");
	        logger.info("This is info message");
	        logger.warn("This is warn message");
	        logger.error("This is error message");
	        logger.trace("This is trace message");

	}

}
