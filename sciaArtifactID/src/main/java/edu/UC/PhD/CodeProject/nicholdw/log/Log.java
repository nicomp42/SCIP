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
	// Is there is a non-null buffer, bypass processing the message and just store it in the buffer
	private static List<String> progressBuffer, errorBuffer, neo4jQueryHistoryBuffer, queryParseProgressBuffer;

	static {
		progressBuffer = null; errorBuffer = null; neo4jQueryHistoryBuffer = null; queryParseProgressBuffer = null;
		//resetMsgBuffer();
		//resetErrorBuffer();
		//resetNeo4jBuffer();
	}

	public static synchronized void flushAllBuffers() {
		flushProgressBuffer();
		flushErrorBuffer();
		flushNeo4jQueryHistoryBuffer();
	}
	public static synchronized void resetAllBuffers() {
		resetMsgBuffer();
		resetErrorBuffer();
		resetNeo4jQueryHistoryBuffer();
	}
	public static synchronized void nullAllBuffers() {
		progressBuffer = null;
		errorBuffer = null;
		neo4jQueryHistoryBuffer = null;
	}
	/**
	 * Log the error message
	 * @param msg The error message
	 */
	public static synchronized void logError(String msg) {
		if (errorBuffer == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeError(msg);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(msg);}
			}
		} else {
			errorBuffer.add(msg);
		}
	}
	public static synchronized void logError(String msg, StackTraceElement[] stackTrace) {
		if (errorBuffer == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeError(msg + "\n" + stackTrace.toString());
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.err.println(msg + "\n" + stackTrace.toString());}
			}
		} else {
			errorBuffer.add(msg + "\n" + stackTrace.toString());
		}
	}
	public static synchronized void logError(String msg, Exception ex) {
		if (errorBuffer == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeError(msg);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.err.println(msg + "\n" + ex.getLocalizedMessage());}
			}
		} else {
			errorBuffer.add(msg + "\n" + ex.getLocalizedMessage());
		}
	}
	public static synchronized void logQueryParseProgress(String msg, Boolean isError) {
		String prefix = "";
		if (isError) {prefix = "***** ";}
		if (queryParseProgressBuffer == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeQueryParseProgress(prefix + msg);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(prefix + msg);}
			}
		} else {
			queryParseProgressBuffer.add(prefix + msg);
		}
	}
	public static synchronized void logQueryParseProgress(String msg) {
		logQueryParseProgress(msg, false);
	}
	public static synchronized void logProgress(String msg) {
		if (progressBuffer == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeProgress(msg);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(msg);}
			}
		} else {
			progressBuffer.add(msg);
		}
	}
	public static synchronized void logNeo4jQueryHistory(String sql) {
		if (neo4jQueryHistoryBuffer == null) {
			if (Config.getConfig().getDebugController() != null) {
				Config.getConfig().getDebugController().writeNeo4jQueryInfo(sql);
			} else {
				if (!Config.getConfig().getSupressOutputToConsole()) {System.out.println(sql);}
			}
		} else {
			neo4jQueryHistoryBuffer.add(sql);
		}
	}
	/**
	 * Flush the contents of the message buffer to the final destination of the messages, then null the buffer
	 */
	public static synchronized void flushProgressBuffer() {
		if (progressBuffer != null) {
			List<String> copy = Collections.synchronizedList(new ArrayList<String>(progressBuffer));
			progressBuffer = null;
			for (Iterator<String> iterator = copy.iterator(); iterator.hasNext(); ) {
			    String value = iterator.next();
			    logProgress(value);
//		        iterator.remove();
			}
//			for (String s: msgBuffer) {
//				logProgress(s);
//			}
		}
	}
	/**
	 * Flush the contents of the error buffer to the final destination of the messages, then null the buffer
	 */
	public static synchronized void flushErrorBuffer() {
		if (errorBuffer != null) {
			List<String> copy = Collections.synchronizedList(new ArrayList<String>(errorBuffer));
			errorBuffer = null;
			for (Iterator<String> iterator = copy.iterator(); iterator.hasNext(); ) {
			    String value = iterator.next();
		    	logError(value);
//		        iterator.remove();
			}
//			for (String s: errorBuffer) {
//				logError(s);
//			}
		}
	}
	/**
	 * Flush the contents of the Neo4j buffer to the final destination of the messages, then null the buffer
	 */
	public static synchronized void flushNeo4jQueryHistoryBuffer() {
		if (neo4jQueryHistoryBuffer != null) {
			List<String> copy = Collections.synchronizedList(new ArrayList<String>(neo4jQueryHistoryBuffer));
			neo4jQueryHistoryBuffer = null;
			for (Iterator<String> iterator = copy.iterator(); iterator.hasNext(); ) {
			    String value = iterator.next();
			    logNeo4jQueryHistory(value);
//		        iterator.remove();
			}
//			for (String s: neo4jQueryHistoryBuffer) {
//				logNeo4jQueryHistory(s);
//			}
		}
	}
	// The reset methods are private because we should only use them when we call the flush methods, above.
	public static List<String> getMsgBuffer() {return progressBuffer;}
	private static void resetMsgBuffer() {progressBuffer = Collections.synchronizedList(new ArrayList<String>());}
	public static List<String> getErrorBuffer() {return errorBuffer;}
	private static void resetErrorBuffer() {errorBuffer = Collections.synchronizedList(new ArrayList<String>());}
	public static List<String> getneo4jQueryHistoryBuffer() {return neo4jQueryHistoryBuffer;}
	private static void resetNeo4jQueryHistoryBuffer() {neo4jQueryHistoryBuffer = Collections.synchronizedList(new ArrayList<String>());}
}
