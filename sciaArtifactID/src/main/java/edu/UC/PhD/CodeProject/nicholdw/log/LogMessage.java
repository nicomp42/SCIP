/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.log;

import java.time.LocalDateTime;

import edu.UC.PhD.CodeProject.nicholdw.Config;

/**
 * A message in a log file
 * @author nicomp
 *
 */
public class LogMessage {
	private String message;
	private LocalDateTime localDateTime;	// Using Java API added in Java 8
	/**
	 * Copy Constructor
	 * @param logMessage LogMessage object to be copied
	 */
	public LogMessage(LogMessage logMessage) {
		this.message = logMessage.getMessage();
		this.localDateTime = logMessage.getLocalDateTime();
	}
	/**
	 * Create a LogMessage object. The date/time stamp will be defaulted to now
	 * @param message
	 */
	public LogMessage(String message) {
		localDateTime = LocalDateTime.now();
		this.message = message;
	}
	/**
	 * Get the value in the message
	 * @return the message 
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Set the value in the message. The date/time stamp is defaulted to now.
	 * @param message 
	 */
	public void setMessage(String message) {
		localDateTime = LocalDateTime.now();
		this.message = message;
	}
	public LocalDateTime getLocalDateTime() {return localDateTime;}
	/**
	 * String representation of the message
	 * @return the formatted message
	 */
	public String toString() {return Config.formatLocalDateTime(localDateTime) + ": " + message;}
}
