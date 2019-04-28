/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.UC.PhD.CodeProject.nicholdw.Config;

/***
 * A collection of Log Messages
 * @author nicomp
 *
 */
public class LogMessages implements Iterable<LogMessage> {
	private List<LogMessage> logMessages;
	private String title;

	/**
	 * Constructor
	 * 
	 * @param title The Description of what will be stored in the log
	 */
	public LogMessages(String title) {
		this.setTitle(title);
		logMessages = Collections.synchronizedList(new ArrayList<LogMessage>()); // new ArrayList<LogMessage>();
	}

	/**
	 * Add a message to the log
	 * 
	 * @param logMessage
	 */
	public void add(LogMessage logMessage) {
		synchronized (logMessages) {
			logMessages.add(logMessage);
		}
	}

	/**
	 * Add a message to the log.
	 * 
	 * @param message 
	 */
	public void add(String message) {
		synchronized (logMessages) {
			logMessages.add(new LogMessage(message));
		}
	}

	/**
	 * Get the title of the Log
	 * 
	 * @return title of the log
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Define the title of the log
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Iterator<LogMessage> iterator() {
		Iterator<LogMessage> myIterator = logMessages.iterator();
		return myIterator;
	}
	/***
	 * Write all the messages to the output device
	 */
	public void writeAllMessages() {
		synchronized (logMessages) {
			Iterator<LogMessage> i = logMessages.iterator(); // Must be in synchronized block
			while (i.hasNext()) {
				if (Config.getConfig().getDebugController() != null) {
					Config.getConfig().getDebugController().writeProgress(i.toString());
				} else {
					if (!Config.getConfig().getSupressOutputToConsole()) {
						System.out.println(i.toString());
					}
				}
			}
		}
	}
	/**
	 * Remove all the messages in the log. They are gone. poof.
	 */
	public void clear() {logMessages.clear();}
}
