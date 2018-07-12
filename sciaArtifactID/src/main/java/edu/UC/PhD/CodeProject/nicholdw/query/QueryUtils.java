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
	public static boolean isAdHocQuery(String sql) {
		boolean status = false;
		String[] p = sql.split(" ");
		if (p.length == 7) {
			if (p[3].toUpperCase().equals("SELECT")) {
				if (p[4].equals("*")) {
					// Now we need to know if we are selecting from an existing query or something else
					String t = p[6];
					// If t is a query then this is not an ad-hoc query
					if (isTable(t)) {
						status = true;
					} else {
						status = false;
					}
				}
			}
		}
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
