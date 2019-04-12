/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.logback;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackDemo {
	public static void main(String args[] ) {
		
	    Logger logger = LoggerFactory.getLogger(LogbackDemo.class);
	     // Set up a simple configuration that logs on the console.
	     BasicConfigurator.configure();

	    logger.debug("Hello world.");
	    logger.error("Error");
		
	}
}
