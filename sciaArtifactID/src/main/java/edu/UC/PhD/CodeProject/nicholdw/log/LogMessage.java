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
	public enum enumLogMessageType {progress, error, neo4jQuery, queryParseProgress};
	private enumLogMessageType logMessageType;
	/**
	 * Copy Constructor
	 * @param logMessage LogMessage object to be copied
	 */
	public LogMessage(LogMessage logMessage) {
		this.message = logMessage.getMessage();
		this.localDateTime = logMessage.getLocalDateTime();
		this.logMessageType = logMessage.logMessageType;
	}
	/**
	 * Create a LogMessage object. The date/time stamp will be defaulted to now
	 * @param message
	 */
	public LogMessage(String message, enumLogMessageType logMessageType) {
		localDateTime = LocalDateTime.now();
		this.message = message;
		this.logMessageType = logMessageType;
	}
	/**
	 * Get the type of message
	 * @return The enum that defines the message type
	 */
	public enumLogMessageType getLogMessageType() {return logMessageType;}
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
