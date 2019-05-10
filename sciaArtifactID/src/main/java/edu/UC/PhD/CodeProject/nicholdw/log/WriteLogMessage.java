/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.log;

/**
 * Used to get the DebugController to work with log messages. 
 * @author nicomp
 *
 */
public interface WriteLogMessage {
	/**
	 * Write a log message somewhere. 
	 * @param msg The log message
	 */
	void writeLogMessage(LogMessage logMessage);
}
