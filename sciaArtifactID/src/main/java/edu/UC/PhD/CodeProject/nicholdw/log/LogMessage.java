/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.log;

import java.time.LocalDateTime;

/**
 * A message in a log file
 * @author nicomp
 *
 */
public class LogMessage {
	private String message;
	private LocalDateTime localDateTime;	// Using Java API added in Java 8
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
	/**
	 * String representation of the message
	 * @return the formatted message
	 */
	public String toString() {return localDateTime + ": " + message;}
}
