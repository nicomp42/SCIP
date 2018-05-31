package lib;

import java.sql.SQLException;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

//import com.mysql.jdbc.PreparedStatement;

public class SQLUtils {
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
	public static Object MyDLookup(String pTarget, String pDomain, String pCriteria, String pAggregate, String pGroupBy, java.sql.Connection connection)
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
}
