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

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

public class Utils {

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
	// Add the terminating slash to a path name if it's not already there
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
	public static String QuoteMeSingle(String sb) {
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
	 * Remove the back quotes from a string
	 * @param data The string
	 * @return The string with back quotes removed
	 */
	public static String removeBackQuotes(String data) {return data.replace("`", "");}

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
	}}

