// http://www.vogella.com/tutorials/MySQLJava/article.html
// http://www.mysql.com/products/connector/
// http://dev.mysql.com/downloads/file.php?id=456315
package lib;
//package de.vogella.mysql.first;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

public class MySQL {
  private Statement statement = null;
  private java.sql.PreparedStatement preparedStatement = null;
  private java.sql.ResultSet resultSet = null;
  private java.sql.Connection connection = null;

  public java.sql.Connection getConnection() {return connection;}

  public java.sql.Connection connectToDatabase(String hostName, String databaseName, String userName, String password) {
	    try {
	        // This will load the MySQL driver, each DB has its own driver
	        Class.forName("com.mysql.jdbc.Driver");
	        // Setup the connection with the DB
//	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/phd", "root", "Danger42");
	        connection = DriverManager.getConnection("jdbc:mysql://" +
	        										 hostName + ":" + Config.getConfig().getMySQLDefaultPort() + "/" +
	        										 databaseName,
	        		                                 userName,
	        		                                 password);
	    } catch (Exception ex) {
	    	Log.logError("mySQL.connectToDatabase(" + hostName + ", " + databaseName + ", " + userName + ", " + password + ") : " + ex.getLocalizedMessage(), ex);

//	    	System.out.println("mySQL.Connection() : " + ex.getLocalizedMessage());
	    }
	  return connection;
}
  public java.sql.Connection connectToDatabase(String databaseName) {
	    try {
	        // This will load the MySQL driver, each DB has its own driver
	        Class.forName("com.mysql.jdbc.Driver");
	        // Setup the connection with the DB
//	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/phd", "root", "Danger42");
	        connection = DriverManager.getConnection("jdbc:mysql://" + edu.UC.PhD.CodeProject.nicholdw.Config.getConfig().getMySQLDefaultHostname() 
	        		                                 + ":" 
	        		                                 + Config.getConfig().getMySQLDefaultPort() 
	        		                                 + "/" 
	        		                                 + Utils.removeBackQuotes(databaseName), 
	        		                                 edu.UC.PhD.CodeProject.nicholdw.Config.getConfig().getMySQLDefaultLoginName(),
	        		                                 edu.UC.PhD.CodeProject.nicholdw.Config.getConfig().getMySQLDefaultPassword());
	        										//"root",
	        		                                // "Danger42");
//            + "\"" + Utils.removeBackQuotes(databaseName) + "\"", 

	    } catch (Exception ex) {
	    	Log.logError("mySQL.connectToDatabase(" + databaseName + ") : " + ex.getLocalizedMessage(), ex);
//	    	System.out.println("mySQL.Connection() : " + ex.getLocalizedMessage());
	    }
	  return connection;
  }
	public static java.sql.ResultSet loadResultSet(String hostName, String loginName, String password, String sql) throws SQLException {
		java.sql.Connection connection = new MySQL().connectToDatabase(hostName, Config.getConfig().getInformationSchemaName(), loginName, password);
		java.sql.ResultSet resultSet = null;
		java.sql.PreparedStatement preparedStatement = null;
	    try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			Log.logError("Schema.loadResultSet() : " + e.getLocalizedMessage());
		}
	    try {
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {throw e;}
		return resultSet;
	}

  public void readDataBase(java.sql.Connection connection) throws Exception {
	  try {
	      // Statements allow to issue SQL queries to the database
	      statement = connection.createStatement();
	      // Result set get the result of the SQL query
	      resultSet = statement.executeQuery("select * from tTest");
	      writeResultSet(resultSet);

	      // PreparedStatements can use variables and are more efficient
	      preparedStatement = connection.prepareStatement("insert into  tTest values (default, ?, ?, ?)");
	      // Parameters start with 1
	      preparedStatement.setString(1, "Test Data");
	      preparedStatement.setDate(2, new java.sql.Date(2009, 12, 11));
	      preparedStatement.setInt(3, 42);
	      preparedStatement.executeUpdate();

	      preparedStatement = connection.prepareStatement("SELECT testID, testData, timeDateStamp, testInt from tTest");
	      resultSet = preparedStatement.executeQuery();
	      writeResultSet(resultSet);

	      // Remove again the insert comment
	      //preparedStatement = connect.prepareStatement("delete from feedback.comments where myuser= ? ; ");
	      //preparedStatement.setString(1, "Test");
	      //preparedStatement.executeUpdate();

	      //resultSet = statement.executeQuery("select * from feedback.comments");
	      //writeMetaData(resultSet);

	  } catch (Exception e) {
		  Log.logError("mySQL.readDataBase() : " + e.getLocalizedMessage(), e);
		  throw e;
	  } finally {
		  close();
	  }
  }

  /**
   * Enclose an attribute name in MySQL-specific delimiters to precent SQL statement errors.
   * @param attributeName
   * @return
   */
  public static String wrapAttributeName(String attributeName) {
	  return "`" + attributeName + "`";
  }
  /**
   * Enclose an attribute name in single quotes to precent SQL statement errors.
   * @param attributeName
   * @return
   */
  public static String wrapStringInSingleQuotes(String myString) {
	  return "'" + myString + "'";
  }
  private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query

    System.out.println("The columns in the table are: ");

    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
      int testID = resultSet.getInt("testID");
      String testData = resultSet.getString("testData");
      Date timeDateStamp = resultSet.getDate("timeDateStamp");
      int testInt = resultSet.getInt("testInt");
      System.out.println("testID: " + testID);
      System.out.println("timeDateStamp: " + timeDateStamp);
      System.out.println("testInt: " + testInt);
      System.out.println("testData: " + testData);
    }
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {resultSet.close();}
      if (statement != null) {statement.close();}
      if (connection != null) {connection.close();}
    } catch (Exception e) {
    	Log.logError("MySQL.close(): " + e.getLocalizedMessage(), e);
    }
  }

}