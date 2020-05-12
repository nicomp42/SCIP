/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.ResultSet;

import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

public class Utils {
	/**
	 * Chop off the prefix of the filename where the job is stored.
	 * @return Just the filename with the prefix removed. 
	 */
	public static String getFilenameWithoutPath(String myFilename) {
		String result = myFilename;
		// ${Internal.Entry.Current.Directory}/KettleJobReconciledToDataWarehouse.kjb
		int backslashIdx = myFilename.lastIndexOf("\\");
		int slashIdx = myFilename.lastIndexOf("/");
		int maxIdx = (backslashIdx > slashIdx) ? backslashIdx : slashIdx;
		if (maxIdx > 0) {
			result = myFilename.substring(maxIdx + 1);
		}
		return result;
	}
	/**
	 * Chop off the prefix of the filename where the job is stored.
	 * @return Just the path with the filename removed. 
	 */
	public static String getPathWithoutFilename(String myFilename) {
		String result = myFilename;
		// ${Internal.Entry.Current.Directory}/KettleJobReconciledToDataWarehouse.kjb
		int backslashIdx = myFilename.lastIndexOf("\\");
		int slashIdx = myFilename.lastIndexOf("/");
		int maxIdx = (backslashIdx > slashIdx) ? backslashIdx : slashIdx;
		if (maxIdx > 0) {
			result = myFilename.substring(0, maxIdx) + "\\";
		}
		return result;
	}

	/**
	 * Make sure the DB is loaded into Neo4j
	 */
	public static void openBrowserWindow() {
		Browser browser = new Browser(Browser.getNextBrowserSerialNumber().toString());
		Config.getConfig().addBrowser(browser);
		browser.initAndLoad(null);
	}
	/***
	 * Make the labels in a graph a little easier to read. Or not.
	 * @param text The text of the label
	 * @return The cleaned up text, delimiters removed.
	 */
	public static String cleanForGraph(String text) {
		return Utils.removeBackQuotes(text);
/*		String result;
		if (text.startsWith("`") && text.endsWith("`")) {
			result = text.substring(1, text.length() - 2);
		} else {
			result = text;
		}
		return result; */
	}
	public static void copyDirectoryStructure(File source, File destination) {
		if (source.isDirectory()) {
			if (!destination.exists()) {
				destination.mkdirs();
			}
			String files[] = source.list();
			for (String file : files) {
				File srcFile = new File(source, file);
				File destFile = new File(destination, file);
				copyDirectoryStructure(srcFile, destFile);
			}
		} else {
			InputStream in = null;
			OutputStream out = null;
			try	{
				in = new FileInputStream(source);
				out = new FileOutputStream(destination);

				byte[] buffer = new byte[1024];

				int length;
				while ((length = in.read(buffer)) > 0) {out.write(buffer, 0, length);}
			}
			catch (Exception e) {
				try	{in.close();} catch (IOException e1) {Log.logError(e1.getLocalizedMessage(), e1);}
				try {out.close();} catch (IOException e1) {Log.logError(e1.getLocalizedMessage(), e1);}
			}
		}
	}
	/**
	 * Add the terminating slash to a path name if it's not already there
	 * @param path Path to be formatted
	 * @return Formatted Path
	 */
	public static String formatPath(String path) {
		try {
			String lastChar = path.trim().substring(path.length()-1);
			if (!lastChar.equals("\\") && !lastChar.equals("/")) {
				return path + "/";
			} else {
				return path;
			}
		} catch (Exception ex) {
			return path;			// If the path is empty we will end up here.
		}
	}
	/**
	 * Replace \ with / and \\ with / in a path
	 * @param path
	 * @return cleaned up string
	 */
	public static String cleanPath(String path) {
		String result = path.replace("\\\\", "/");		// \\\\ collapses to \\ in an ASCII string
		result = result.replace("\\", "/");				// \\ collapses to \ in an ASCII string
		return result;
	}

	public static void ExecuteCommandLine(String command) {
		ProcessBuilder pb=new ProcessBuilder(command);
		pb.redirectErrorStream(true);
		Process process = null;
		try {
			process = pb.start();
		} catch (IOException e1) {
			Log.logError("Utils.ExecuteCommandLine(): " + e1.getLocalizedMessage());
		}
		BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		try {
			while(inStreamReader.readLine() != null){
				//do something with commandline output.
			}
		} catch (IOException e) {
			Log.logError("Utils.ExecuteCommandLine(): " + e.getLocalizedMessage());
		}
	}
	/**
	 * Wrap a string in back quotes
	 * @param sb
	 * @return sb wrapped in back quotes
	 */
	public static String QuoteMeBack(String sb) {
		return "`" + sb + "`";
	}
	/**
	 * Wrap a string in single quotes
	 * @param sb
	 * @return sb wrapped in single quotes
	 */
	public static String quoteMeSingle(String sb) {
		return "'" + sb + "'";
	}
	/**
	 * Wrap a string in double quotes
	 * @param sb
	 * @return sb wrapped in double quotes
	 */
	public static String QuoteMeDouble(String sb) {
		return "\"" + sb + "\"";
	}
	/**
	 * Wrap a string in double quotes
	 * @param sb What to be wrapped
	 * @return sb wrapped in double quotes
	 */
	public static String QuoteMeDouble(int sb) {
		return "\"" + String.valueOf(sb) + "\"";
	}
	/**
	 * Wrap a string in double quotes
	 * @param sb What to be wrapped
	 * @return sb wrapped in double quotes
	 */
	public static String QuoteMeDouble(long sb) {
		return "\"" + String.valueOf(sb) + "\"";
	}

	public static int ExportMySQLResultSetToCSV(ResultSet rs, String filePath) {
		int count = 0;
		String comma;
		try {
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			FileWriter fw = new FileWriter(filePath, false);
			// Print column headings
			comma = "";
			for(int i = 1; i <= columnCount; i++) {
				fw.append(comma);
				fw.append(rsmd.getColumnLabel(i));		// Use GetColumnLabel method because it honors the AS clause in the SQL statement, if there is one.
				comma = ",";
			}
			fw.append('\n');
			// Print all the rows in the resultSet
			while (rs.next()) {
				comma = "";
				for (int i = 1; i <= columnCount; i++) {
					fw.append(comma);
					fw.append(rs.getString(i));
					comma = ",";
				}
				fw.append('\n');
				count++;
			}
			fw.flush();
			fw.close();
			Log.logProgress("ExportMySQLResultSetToCSV(): CSV File created successfully: " + filePath);
		} catch (Exception e) {
			Log.logError("ExportMySQLResultSetToCSV(): " + e.getLocalizedMessage());
		}
		return count;
	}
	/**
	 * Remove the period from a string
	 * @param data The string
	 * @return The string with period removed
	 */
	public static String removePeriod(String data) {return data.replace("\\.", "");}	// The . is a special character in RegEx. We have to hide it.
	/**
	 * Remove the back quotes from the beginning and end of a string, if present.
	 * @param data The string
	 * @return The string with back quotes removed from beginning and end
	 */
	public static String removeBackQuotes(String data) {
		if (data.startsWith("`") && data.endsWith("`")) {
			return data.substring(1, data.length()-1);
		} else {
			return data;
		}
//		return data.replace("`", "");
		}

	/***
	 * Map a null string to an empty string
	 * @param s The string to be mapped
	 * @return s or an empty string if s is null
	 */
	public static String replaceNullWithEmpty(String s) {
		if (s == null) {
			return "";
		} else {
			return s;
		}
	}
	/***
	 * Read from an InputStream into a String: https://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
	 * @param is the InputStream, already open. Be sure to close it after.
	 * @return the string.
	 */
	@SuppressWarnings("resource")
	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	/**
	 * Wrap a string in a delimiter, if the string is not zero length
	 * @param data The string to process
	 * @param delimiter The delimiter to use
	 * @return The wrapped String, or the original String if the original String is zero length
	 */
	public static String wrapInDelimiter(String data, String delimiter) {
		if (data.trim().length() > 0) {
			return delimiter + data.trim() + delimiter;
		} else {
			return data;
		}
	}
	/**
	 * Remove a delimiter from around a String, if it's there
	 * @param data The string to process
	 * @param delimiter The delimiter to use
	 * @return The unwrapped String, or the original String if the original String doesn't have the delimiter
	 */
	public static String removeDelimiter(String data, String delimiter) {
		String tmp = data.trim();
		if (tmp.substring(0, 0).equals(delimiter) && tmp.substring(tmp.length()-1, tmp.length()-1).equals(delimiter)) {
			return tmp.substring(1, tmp.length()-2);
		} else {
			return tmp;
		}
	}
	/**
	 * Check an artifact name to see if it's blank, except for the delimiters that the database engine might use
	 * @param artifactName
	 * @return True if it's blank, false otherwise
	 */
	public static Boolean isBlank(String artifactName) {
		Boolean isBlankFlag = false;
		String tmp = artifactName.trim();
		if (tmp.trim().length() == 0 || 
			(tmp.trim().startsWith("`") && tmp.trim().endsWith("`") && tmp.length() == 2)) {
			isBlankFlag = true;
		}
		return isBlankFlag;
	}

}

