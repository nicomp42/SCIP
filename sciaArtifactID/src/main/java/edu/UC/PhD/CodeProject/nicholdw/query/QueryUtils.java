package edu.UC.PhD.CodeProject.nicholdw.query;

import java.sql.SQLException;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import lib.SQLUtils;

public class QueryUtils {
	/***
	 * Check a SQL statement to see if it is an ad-hoc query
	 * @param sql The SQL to check
	 * @return True is sql contains an ad-hoc query, false otherwise
	 */
	public static boolean isAdHocQuery(String sql, StringBuilder sqlReduced) {
		boolean status = false;
		String[] p = sql.split(" ");
		try {
			if (p.length >= 7) {
				if (p[3].toUpperCase().equals("SELECT")) {
					if (p[4].trim().substring(0, 1).equals("*")) {
						int i = 5;
						while (!p[i].toUpperCase().equals("FROM")) {i++;}
						i++;
						// Now we need to know if we are selecting from an existing query or something else
						String t = p[i];
						// If t is a query then this is not an ad-hoc query
						if (isTable(t)) {
							status = true;
						} else {
							status = false;
						}
					}
				}
				if (status == true && sqlReduced != null) {
					// Extract the SQL statement from the transaction log entry into sqlReduced
					sqlReduced.setLength(0);
					String space = "";
					for (int i = 3; i < p.length; i++) {
						sqlReduced.append(space + p[i]);
						space = " ";
					}
				}
			}
		} catch (Exception ex) {}
		return status;
	}
	public static boolean isTable(String tableName) {
		boolean status = false;
		String parts[] = tableName.split("\\.");
		try {
			String sql = "SELECT * FROM information_schema.tables WHERE TABLE_NAME = " 
			             + Utils.QuoteMeSingle(parts[1])
			             + " AND " 
			             + "TABLE_SCHEMA = "
			             + Utils.QuoteMeSingle(parts[0])
			             + " AND " 
			             + " TABLE_TYPE = " + Utils.QuoteMeSingle("BASE TABLE");
			
			java.sql.ResultSet resultSet = null;
			resultSet = SQLUtils.executeQuery(Config.getConfig().getMySQLDefaultHostname(), 
											 "",
					              			 Config.getConfig().getMySQLDefaultLoginName(), 
					              			 Config.getConfig().getMySQLDefaultPassword(), 
					              			 sql);
			if (resultSet.first() == true) {status = true;}
		} catch (Exception e) {
			Log.logError("QueryUtils.isTable(" + tableName + "): " + e.getLocalizedMessage());
		}
		return status;
	}
}
