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
/**
 * Message logging for the application
 * @author nicomp
 *
 */
public class Log {
	// If there is a non-null buffer, bypass processing the message and just store it in the buffer
	private static LogMessages progressLog, errorLog, neo4jQueryHistoryLog, queryParseProgressLog;
	
	static {
		progressLog = null; errorLog = null; neo4jQueryHistoryLog = null; queryParseProgressLog = null;
		//resetMsgBuffer();
		//reseterrorLog();
		//resetNeo4jBuffer();
	}

	public static synchronized void flushAllBuffers() {
		flushProgressLog();
		flushErrorLog();
		flushNeo4jQueryHistoryLog();
		flushQueryParseProgressLog();		
	}
	public static synchronized void resetAllBuffers() {
		resetMsgBuffer();
		resetErrorLog();
		resetNeo4jQueryHistoryLog();
		resetQueryParseProgressLog();
	}
	public static synchronized void nullAllBuffers() {
		progressLog = null;
		errorLog = null;
		neo4jQueryHistoryLog = null;
		queryParseProgressLog = null;
	}
	/**
	 * Log the error message
	 * @param msg The error message
	 */
	public static synchronized void logError(LogMessage logMessage) {
		if (errorLog == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeError(logMessage);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(logMessage.toString());}
			}
		} else {
			errorLog.add(logMessage);
		}
	}
	/**
	 * Log the error message
	 * @param msg The error message
	 */
	public static synchronized void logError(String msg) {
		logError(new LogMessage(msg));
/*		if (errorLog == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeError(msg);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(msg);}
			}
		} else {
			errorLog.add(new LogMessage(msg));
		} */
	}
	public static synchronized void logError(String msg, StackTraceElement[] stackTrace) {
		if (errorLog == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeError(msg + "\n" + stackTrace.toString());
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.err.println(msg + "\n" + stackTrace.toString());}
			}
		} else {
			errorLog.add(new LogMessage(msg + "\n" + stackTrace.toString()));
		}
	}
	public static synchronized void logError(String msg, Exception ex) {
		logError(new LogMessage(msg + "\n" + ex.getLocalizedMessage()));
/*		if (errorLog == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeError(msg);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.err.println(msg + "\n" + ex.getLocalizedMessage());}
			}
		} else {
			errorLog.add(new LogMessage(msg + "\n" + ex.getLocalizedMessage()));
		} */
	}
	public static synchronized void logQueryParseProgress(String msg, Boolean isError) {
		String prefix = "";
		if (isError) {prefix = "***** ";}
		if (queryParseProgressLog == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeQueryParseProgress(prefix + msg);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(prefix + msg);}
			}
		} else {
			queryParseProgressLog.add(new LogMessage(prefix + msg));
		}
	}
	public static synchronized void logQueryParseProgress(String msg) {
		logQueryParseProgress(msg, false);
	}
	public static synchronized void logProgress(LogMessage msg) {
		if (progressLog == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeProgress(msg);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(msg);}
			}
		} else {
			progressLog.add(new LogMessage(msg));
		}
	}
	public static synchronized void logProgress(String msg) {
		logProgress(new LogMessage(msg));
/*		if (progressLog == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeProgress(msg);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(msg);}
			}
		} else {
			progressLog.add(new LogMessage(msg));
		} */
	}
	public static synchronized void logNeo4jQueryHistory(String sql) {
		if (neo4jQueryHistoryLog == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeNeo4jQueryInfo(sql);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(sql);}
			}
		} else {
			neo4jQueryHistoryLog.add(sql);
		}
	}
	/**
	 * Flush the contents of the message buffer to the final destination of the messages, then clear the log
	 */
	public static synchronized void flushProgressLog() {
		if (progressLog != null) {
			progressLog.writeAllMessages();
			progressLog.clear();
		}
	}
	/**
	 * Flush the contents of the error buffer to the final destination of the messages, then clear the log
	 */
	public static synchronized void flushErrorLog() {
		if (errorLog != null) {
			errorLog.writeAllMessages();
			errorLog.clear();
		}
	}
	/**
	 * Flush the contents of the Neo4j buffer to the final destination of the messages, then clear the log
	 */
	public static synchronized void flushNeo4jQueryHistoryLog() {
		if (neo4jQueryHistoryLog != null) {
			neo4jQueryHistoryLog.writeAllMessages();
			neo4jQueryHistoryLog.clear();
		}
	}
	/**
	 * Flush the contents of the Query Parsing buffer to the final destination of the messages, then clear the log
	 */
	public static synchronized void flushQueryParseProgressLog() {
		if (queryParseProgressLog != null) {
			queryParseProgressLog.writeAllMessages();
			queryParseProgressLog.clear();
		}
	}
	
	// The reset methods are private because we should only use them when we call the flush methods, above.
//	public static List<String> getMsgBuffer() {return progressLog;}
	private static void resetMsgBuffer() {progressLog = new LogMessages("Progess");}// Collections.synchronizedList(new ArrayList<String>());}
//	public static List<String> geterrorLog() {return errorLog;}
	private static void resetErrorLog() {errorLog = new LogMessages("Error");}
//	public static List<String> getneo4jQueryHistoryLog() {return neo4jQueryHistoryLog;}
	private static void resetNeo4jQueryHistoryLog() {neo4jQueryHistoryLog = new LogMessages("Neo4j Query History");}
	private static void resetQueryParseProgressLog() {queryParseProgressLog = new LogMessages("Query Parse Progress");}
}
