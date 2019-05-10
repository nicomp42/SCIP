/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import edu.UC.PhD.CodeProject.nicholdw.Config;

/***
 * A collection of Log Messages
 * @author nicomp
 */
public class LogMessages implements Iterable<LogMessage> {
//	private List<LogMessage> logMessages;
	BlockingQueue<LogMessage> logMessages;	// = new LinkedBlockingQueue<>();
	private String title;

	/**
	 * Constructor
	 * 
	 * @param title The Description of what will be stored in the log
	 */
	public LogMessages(String title) {
		this.setTitle(title);
//		logMessages = Collections.synchronizedList(new ArrayList<LogMessage>()); 
		logMessages = new LinkedBlockingQueue<LogMessage>(); 
	}

	/**
	 * Add a message to the log
	 * 
	 * @param logMessage
	 */
	public void add(LogMessage logMessage) {
		synchronized (logMessages) {
			logMessages.add(new LogMessage(logMessage));
		}
	}

	/**
	 * Add a message to the log.
	 * 
	 * @param message 
	 */
	public void add(String message, LogMessage.enumLogMessageType logMessageType) {
		synchronized (logMessages) {
			logMessages.add(new LogMessage(message, logMessageType));
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
	 * Write all the progress messages to the output device
	 */
	public void writeAllMessages(WriteLogMessage wlm) {
//		if (Config.getConfig().getEnableLogging() == false) return;
		synchronized (logMessages) {
			//Iterator<LogMessage> i = logMessages.iterator(); // Must be in synchronized block
			while (logMessages.isEmpty() == false) {
				if (wlm != null) {
					try {wlm.writeLogMessage(logMessages.poll(0, TimeUnit.SECONDS));} catch (Exception ex) {}
				} else {
					if (!Config.getConfig().getSupressOutputToConsole()) {
						try {System.out.println(logMessages.poll(0, TimeUnit.SECONDS).toString());} catch (Exception ex) {}
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
