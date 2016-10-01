package com.roby.demo.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class Jcl {
	
	static Log logger = LogFactory.getLog(Jcl.class);
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
