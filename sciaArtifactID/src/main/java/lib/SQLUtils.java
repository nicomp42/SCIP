package lib;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.databaseEngine.ConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
//import com.mysql.jdbc.PreparedStatement;

/***
 * Database Utilities
 * @author nicomp
 *
 */
public class SQLUtils {
	/**
	 * Call a stored procedure in the database 
	 * @param connectionInformation
	 * @param sql
	 */
	public static boolean callStoredProcedure(ConnectionInformation connectionInformation, String sql) {
    	boolean status = false;	
    	try {
    		callStoredProcedure(connectionInformation.getHostName(), 
    							connectionInformation.getSchemaName(), 
    							connectionInformation.getLoginName(), 
    							connectionInformation.getPassword(),
    							sql);
	    } catch (Exception ex) {
	    	status = false;
			Log.logError("SQLUtils.callStoredProcedure(): " + ex.getLocalizedMessage());
	    } finally {
	    }
		return status;
	}
	/***
	 * Call a stored procedure in the database 
	 * @param hostName
	 * @param databaseName
	 * @param loginName
	 * @param password
	 * @param sql The SQL statement, all ready to submit. See https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-statements-callable.html
	 * @return
	 */
	public static boolean callStoredProcedure(String hostName, String databaseName, String loginName, String password, String sql) {
		boolean status = true;
	    java.sql.Connection connection = null;
	    try {
	    	connection = new MySQL().connectToDatabase(hostName, databaseName, loginName, password);		// ToDo: Do we really need a databaseName here? 
	    	java.sql.CallableStatement cStmt = connection.prepareCall(sql);
	        cStmt.execute();
	    } catch (Exception ex) {
	    	status = false;
			Log.logError("SQLUtils.callStoredProcedure(): " + ex.getLocalizedMessage());
	    } finally {
	    	try {connection.close();} catch (Exception ex) {}
	    }
		return status;
	}
	/***
	 * Execute an action query. 
	 * @param connectionInformation The connection information
	 * @param sql the sql statement
	 */
	public static void executeActionQuery(ConnectionInformation connectionInformation, String sql) {
		executeActionQuery(connectionInformation.getHostName(), connectionInformation.getSchemaName(), connectionInformation.getLoginName(), connectionInformation.getPassword(), sql);
	}
	/***
	 * Execute an action query. The connection is opened and then closed. It will be slow.
	 * @param hostName
	 * @param databaseName
	 * @param loginName
	 * @param password
	 * @param sql
	 */
	public static void executeActionQuery(String hostName, String databaseName, String loginName, String password, String sql) {
	    java.sql.Connection connection = null;
		connection = new MySQL().connectToDatabase(hostName, databaseName, loginName, password);		// ToDo: Do we really need a databaseName here? 
	    java.sql.PreparedStatement preparedStatement = null;
	    try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Log.logError("SQLUtils.executeActionQuery(): " + e.getLocalizedMessage() + " (" + sql + ")");
		}
	    try {connection.close();}catch(Exception ex) {}
	}
	/***
	 * Execute an action query. The connection must already be open and it is not closed by this method.
	 * @param connection An open connection. It is not closed at the end of this method
	 * @param sql The sql to be submitted to the DB Engine
	 */
	public static void executeActionQuery(java.sql.Connection connection, String sql) {
	    java.sql.PreparedStatement preparedStatement = null;
	    try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Log.logError("SQLUtils.executeActionQuery(): " + e.getLocalizedMessage() + " (" + sql + ")");
		}
//	    try {connection.close();}catch(Exception ex) {}
	}

	/**
	 * Execute a SELECT statement against the database
	 * @param hostName
	 * @param databaseName
	 * @param loginName
	 * @param password
	 * @param sql
	 * @return The ResultSet object that is the result of the SELECT statement
	 */
	public static java.sql.ResultSet executeQuery(String hostName, String databaseName, String loginName, String password, String sql) {
	    java.sql.ResultSet resultSet = null;
		java.sql.Connection connection = null;
		connection = new MySQL().connectToDatabase(hostName, databaseName, loginName, password);		// ToDo: Do we really need a databaseName here? 
	    java.sql.PreparedStatement preparedStatement = null;
	    try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			Log.logError("SQLUtils.executeQuery(): " + e.getLocalizedMessage());
//			System.out.println("SQLUtils.executeQuery(): " + e.getLocalizedMessage());
		}
	    try {/*connection.close();*/}catch(Exception ex) {}
	    return resultSet;
	}
	/**
	 * Execute a SELECT statement against the database
	 * @param connectionInformation What's needed to access the DBMS
	 * @param sql
	 * @return The ResultSet object that is the result of the SELECT statement
	 */
	public static java.sql.ResultSet executeQuery(ConnectionInformation connectionInformation, String sql) {
	    java.sql.ResultSet resultSet = null;
		java.sql.Connection connection = null;
		connection = new MySQL().connectToDatabase(connectionInformation.getHostName(), connectionInformation.getSchemaName(), connectionInformation.getLoginName(), connectionInformation.getPassword()); 
	    java.sql.PreparedStatement preparedStatement = null;
	    try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			Log.logError("SQLUtils.executeQuery(): " + e.getLocalizedMessage());
//			System.out.println("SQLUtils.executeQuery(): " + e.getLocalizedMessage());
		}
	    try {/*connection.close();*/}catch(Exception ex) {}
	    return resultSet;
	}
	/**
	 * Execute a SELECT statement against the database
	 * @param An open JDBC connection
	 * @param sql
	 * @return The ResultSet object that is the result of the SELECT statement
	 */
	public static java.sql.ResultSet executeQuery(java.sql.Connection connection, String sql) {
	    java.sql.ResultSet resultSet = null;
		//java.sql.Connection connection = null;
		//connection = new MySQL().connectToDatabase(connectionInformation.getHostName(), connectionInformation.getHostName(), connectionInformation.getLoginName(), connectionInformation.getPassword()); 
	    java.sql.PreparedStatement preparedStatement = null;
	    try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			Log.logError("SQLUtils.executeQuery(): " + e.getLocalizedMessage());
//			System.out.println("SQLUtils.executeQuery(): " + e.getLocalizedMessage());
		}
	    try {/*connection.close();*/}catch(Exception ex) {}
	    return resultSet;
	}
	public static java.sql.Connection openJDBCConnection(ConnectionInformation connectionInformation) {
		java.sql.Connection connection = null;
		try {
			connection = new MySQL().connectToDatabase(connectionInformation.getHostName(), connectionInformation.getSchemaName(), connectionInformation.getLoginName(), connectionInformation.getPassword());
		} catch (Exception ex) {
			Log.logError("SQLUtils.openJDBCConnection(); " + ex.getLocalizedMessage());
		}
		return connection;
	}
	public static Object myDLookup(String pTarget, String pDomain, String pCriteria, String pAggregate, String pGroupBy, java.sql.Connection connection)
    {
        String criteria;
        boolean keepGoing;
        String aggregate;
        String asName;
        Object result = null;
        java.sql.ResultSet resultSet = null;
        java.sql.PreparedStatement preparedStatement = null;
        //System.Data.SqlClient.SqlDataReader reader = null;		// .Net

        if (pAggregate.trim().length() != 0)
        {
            aggregate = pAggregate;      // MAX, MIN, etc.
            asName = "foo";              // We need a unique name because this is a calculated field
        } else {
            aggregate = "";
            asName = "foo";              // We can't use pTarget here because it causes an SQL "Circular Reference" error
        }
        //  If the domain is a select statement, don't do anything to it. We can't perform an agregate function on SQL.
        //    Whoever calls this function should configure the SQL to have the desired record at the top of the cursor
        //    before calling this function.
        if ((pDomain.length() > 7) && (pDomain.substring(1, 6).contentEquals("SELECT")))
        {
            criteria = pDomain;
            asName = pTarget;
        } else {
            criteria = "SELECT " + aggregate + "(" + pTarget + ") AS " + asName + " FROM " + pDomain;
            if (pCriteria.length() == 0) {
            } else {
                criteria = criteria + " WHERE " + pCriteria;
            }
            if (pGroupBy.trim().length() > 0) {
                criteria = criteria + " GROUP BY " + pGroupBy;
            }
            criteria = criteria + ";";
        }
        keepGoing = true;

        try {
            //MySqlCommand command = new MySqlCommand(criteria, myConnection);
            // ToDo: This is wasteful. We should check to see if it's already open.
            //try { myConnection.Close(); }
            //catch (Exception e) { }
            //CheckConnection();

        	preparedStatement = connection.prepareStatement(criteria);
        	resultSet = preparedStatement.executeQuery();

            //System.Data.SqlClient.SqlCommand command = myConnection.CreateCommand();
            //command.CommandText = criteria;

            //reader = command.ExecuteReader(System.Data.CommandBehavior.CloseConnection);

            if (resultSet.next()) {            	//resultSet.Read();       // Get the first row. We always assume it's the first row, in this function
                result = resultSet.getObject(1);		// Columns indices are 1-based
            } else {
                result = null;
            }

            resultSet.close();
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            result = null;                // null means we failed
            try { resultSet.close(); }
            catch (Exception ex) { }
        }
        return result;
    }
	/**
	 * Wrap a string in double quotes
	 * @param me The string to be wrapped
	 * @return The wrapped string
	 */
	public static String DoubleQuoteMe(String me) {
		return '"' + me + '"';
	}
	/***
	 * Read the code in stored procedure
	 * @param connectionInformation How to connect to the database
	 * @param storedProcedureName The name of the stored procedure, including schema if necessary
	 * @return
	 */
	public static ArrayList<String> readStoredProcedure(ConnectionInformation connectionInformation, String storedProcedureName) {
		ArrayList<String> result = new ArrayList<String>();
		java.sql.ResultSet resultSet;
		String sql = "show create procedure " + storedProcedureName + ";";
		try {
			resultSet = SQLUtils.executeQuery(connectionInformation, sql);
			while (true) {
				if (!resultSet.next()) {break;};
				result.add(resultSet.getString(3));		// Columns are 1-based.
			}
		} catch (Exception ex) {
			Log.logError("SQLUtils.readStoredProcedure(" + storedProcedureName + ")"  + ex.getLocalizedMessage());
		} finally {
		}

		return result;
	}
	public static int executeSELECTQueriesInStoredProcedure(ConnectionInformation connectionInformation, String storedProcedureName) {
		int queryCount = 0;
		try {
			ArrayList<String> spCode = readStoredProcedure(connectionInformation, storedProcedureName);
			String[] spCodeLines = spCode.get(0).split("\n");
			for (String sql: spCodeLines) {
				if (sql.trim().toLowerCase().startsWith("select")) {
					SQLUtils.executeQuery(connectionInformation,sql);	// We don't care what it returns, we just want it in the transaction log
				}
			}
		} catch (Exception ex) {
			Log.logError("SQLUtils.executeSELECTQueriesInStoredProcedure(" + storedProcedureName + ")"  + ex.getLocalizedMessage());
		}
		return queryCount;
	}
	
}
