package edu.UC.PhD.CodeProject.nicholdw;

import java.sql.SQLException;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.AliasNameClass;
import lib.MySQL;

/***
 * Model a table in the database
 * @author nicomp
 *
 */
public class Table {

	protected String tableName;
	private Indexes indexList;
	private Attributes attributes;
	protected String schemaName;
	private String comment;	// mySQL-specific. A comment attached to the table
	private final String engine = "InnoDB";
	private final String default_charset = "latin1";
	private final String auto_increment = "1";	// http://dev.mysql.com/doc/refman/5.7/en/example-auto-increment.html
	private boolean alreadyHasSurrogateKey;
	private String dBInstanceName;
	
	/**
	 * Test some stuff
	 * @param args
	 */
	public void main(String[] args) {
		Log.logProgress("Table.main()...");
		Table myFlightTable = new Table("flight", "tFlight");
		Table myPilotTable = new Table("flight", "tPilot");
		myFlightTable.setAttributes(Table.readAttributesFromTableDefinition(myFlightTable.getSchemaName(), myFlightTable.getTableName()));
		
		myPilotTable.setAttributes(Table.readAttributesFromTableDefinition(myPilotTable.getSchemaName(), myPilotTable.getTableName()));
		
		
	}
	
	public String getComment() {return comment;}
	public void setComment(String comment) {this.comment = comment;}
	/**
	 * Find an attribute by name in the collection for this table
	 * @param attributeName The name to search for
	 * @return The corresponding Attribute object or null if no match
	 */
	public Attribute findAttribute(String attributeName) {
		Attribute attributeFound = null;
		for(Attribute attribute : attributes) {
			if (Config.getConfig().compareAttributeNames(attribute.getAttributeName(), attributeName) == true) {
				attributeFound = attribute;
				break;
			}
		}
		return attributeFound;
	}
	
	public String generateScript() {
		String script = "CREATE TABLE " + tableName + " (";
		String comma = "";
		String primaryKeyClause = "";
		for (Attribute a : attributes) {
			script += comma;
			script += " " + Utils.QuoteMeBack(a.getAttributeName());
			script += " ";
			script += a.getType();
			if (a.getLength() > 0) {
				script += "(" + a.getLength() + ")";
			}
			if (!a.isNullable()) {
				script += " NOT NULL";
			}
			if (a.isAutoIncrement()) {
				script += " AUTO_INCREMENT";
				primaryKeyClause = "PRIMARY KEY(" + Utils.QuoteMeBack(a.getAttributeName()) + ")";
				this.setAlreadyHasSurrogateKey(true);
			}
			comma = ", ";
		}
		if (primaryKeyClause.length() == 0) {	// Did the table definition include a primary key?
			// ToDo: Do we need this?
		}
		script += comma + primaryKeyClause;
		for (Index index : indexList) {
			script += comma;
			if (index.isUnique()) {	script += "UNIQUE ";}
			script += "KEY " + Utils.QuoteMeBack(index.buildIndexName());
			script += " (" ;
			String indexComma;
			indexComma = "";
			for (Attribute indexAttribute : index.getAttributes()) {
				script += indexComma + Utils.QuoteMeBack(indexAttribute.getAttributeName());
				indexComma = ", ";
			}
			script += ")";
		}
		script += ") ";
		script += "ENGINE=" + engine + " AUTO_INCREMENT=" + auto_increment + " DEFAULT CHARSET=" + default_charset + ((comment != null)? " COMMENT = " + Utils.QuoteMeBack(comment):"");
		return script;
	}

	/**
	 * Constructor
	 * @param name Name of table
	 * @param schemaName Name of schema/database
	 */
	public Table(String tableName, String schemaName) {
		this.tableName = tableName;
		indexList = new Indexes();
		this.schemaName = schemaName;
		attributes = new Attributes();
		this.setAlreadyHasSurrogateKey(false);
		setDBInstanceName("");
	}
	/***
	 * Constructor
	 * @param name Name of table
	 * @param schemaName Name of schema/database
	 * @param attributeList
	 * @param indexList
	 */
	public Table(String tableName, String schemaName, Attributes attributeList, Indexes indexList) {
		this.tableName = tableName;
		this.indexList = indexList;
		this.schemaName = schemaName;
		this.attributes = attributeList;
		this.setAlreadyHasSurrogateKey(false);
		setDBInstanceName("");
	}
	/***
	 * Constructor
	 * @param name Name of table
	 * @param schemaName Name of schema/database
	 * @param indexList
	 */
	public Table(String name,  String schemaName, Indexes indexList) {
		this.tableName = name;
		this.schemaName = schemaName;
		this.indexList = indexList;
		attributes = new Attributes();
		this.setAlreadyHasSurrogateKey(false);
		setDBInstanceName("");
	}
	/***
	 * Constructor
	 * @param name Name of table
	 * @param schemaName Name of database/schema
	 * @param attributeList
	 */
	public Table(String name, String schemaName, Attributes attributeList) {
		this.tableName = name;
		this.schemaName = schemaName;
		this.attributes = attributeList;
		indexList = new Indexes();
		this.setAlreadyHasSurrogateKey(false);
		setDBInstanceName("");
	}
	public Attributes getAttributes() {return attributes;}
	public void setAttributes(Attributes attributes) {this.attributes = attributes;}
	public Indexes getIndexList() {return indexList;}
	public String getTableName() {return tableName;}
	/**
	 * Add an index. This is useful if we are building a table object to generate a script.
	 * @param index The index to add.
	 */
	public void addIndex(Index index) {indexList.addIndex(index);}


	/***
	 * Read the attributes from the table in the schema
	 * @param tableName The name of the table in the schema
	 * @param databaseName The name of the database/schema
	 * @return The collection of attributes
	 */

	public static Attributes readAttributesFromTableDefinition(String tableName, String schemaName) {
		Attributes myAttributeList = new Attributes();
		java.sql.Connection connection = null;
		try {
			//MySQL my = new MySQL();
			//connection = new MySQL().connectToDatabase(Config.getConfig().databaseName);
			connection = new MySQL().connectToDatabase(schemaName);

			String sql = "DESC " + tableName;
		    java.sql.PreparedStatement preparedStatement = null;
		    try {
				preparedStatement = connection.prepareStatement(sql);
			} catch (SQLException e) {
				Log.logError("Table.ReadAttributesFromTableDefinition(" + tableName + ", " + schemaName + ") : " + e.getLocalizedMessage());
			}
		    java.sql.ResultSet resultSet = null;
		    try {
				resultSet = preparedStatement.executeQuery();
			} catch (SQLException e) {
				Log.logError("Table.ReadAttributesFromTableDefinition(" + tableName + ", " + schemaName + "): " + e.getLocalizedMessage());
			}
		    try {
				while (resultSet.next()) {
					// Extract the descriptors from each attribute. See http://www.java2s.com/Tutorial/MySQL/0080__Table/Checkthecreatedtablebyissuingadescribecommand.htm
					String name = resultSet.getString(1);		// The argument to getString() is one-based, not zero-based
					String dataType = resultSet.getString(2);	// Will have a length specification: int(11), varchar(50), etc.
					String nullable = resultSet.getString(3);
					String key = resultSet.getString(4);
					String theDefault = resultSet.getString(5);
					String extra = resultSet.getString(6);
					int length = extractLength(dataType);
					dataType = trimDataType(dataType);
					// That's it. That's the list. No more descriptors.
					// ToDo: The IsPrimary key argument is defaulted to false because it comes from another table.
					Attribute myAttribute = new Attribute(name, tableName, false, dataType, nullable, key, theDefault, extra, length, (Aliases)null);

					myAttributeList.addAttribute(myAttribute);

					//System.out.println(name + "\t\t" + dataType + "\t\t\t\t" + nullable + "\t\t" + key + "\t\t" + theDefault + "\t\t" + extra);
				}
			} catch (SQLException e) {
				Log.logError("Table.ReadAttributesFromTableDefinition(" + tableName + ", " + schemaName + "): " + e.getLocalizedMessage());
			}
		} catch (Exception ex) {
			Log.logError("Table.readAttributesFromTableDefinition(): " + ex.getLocalizedMessage());
		} finally {
			try {connection.close();} catch (Exception ex) {}
		}
	    return myAttributeList;
	}
	/***
	 * Remove the length specifier from the dataType as it's provided by mySQL
	 * @param dataType The string: int(11), varchar(50), etc.
	 * @return The data type without the length and the parens.
	 */
	private static String trimDataType(String dataType) {
		String result = dataType;
		try {
		int idx = dataType.indexOf("(");
		if (idx > -1) {
			result = dataType.substring(0, idx);
		}
		} catch (Exception ex) {
			Log.logError("Table.trimDataType(): " + ex.getLocalizedMessage());
		}
		return result;
	}
	private static int extractLength(String dataType) {
		int length = -1;
		try {
			int idx1, idx2;
			idx1 = dataType.indexOf("(");
			if (idx1 > -1) {
				idx1++;
				idx2 = dataType.indexOf(")");
//				idx2--;
				String strLength = dataType.substring(idx1, idx2);
				length = Integer.valueOf(strLength);
			}
		} catch (Exception ex) {
			Log.logError("Table.extractLength(): " + ex.getLocalizedMessage());
		}
		return length;
	}
	public boolean isAlreadyHasSurrogateKey() {return alreadyHasSurrogateKey;}
	public void setAlreadyHasSurrogateKey(boolean alreadyHasSurrogateKey) {this.alreadyHasSurrogateKey = alreadyHasSurrogateKey;}

	/***
	 * Use our naming conventions to synthesize a table name
	 * @param kind The name of the Kind
	 * @return The table name
	 */
	public static String buildTableNameFromKind(String kind) {
		return "t" + kind;
	}
	/***
	 * Add a surrogate key to the table
	 */
	public void addSurrogateKey() {
		Attribute a = new Attribute(getTableName() + "ID", this.tableName, true, "int", "no", "yes", "", "", 1, (Aliases)null );
		a.setAutoIncrement(true);
		attributes.addAttribute(a);
		setAlreadyHasSurrogateKey(true);
	}
	public String getSchemaName() {return schemaName;}
	public void setSchemaName(String schemaName) {this.schemaName = schemaName;}

	/**
	 * Look up the data type for an attribute in the table
	 * @param attributeName The name of the attribute
	 * @return The data type of the attribute, or "" if not found
	 */
	public String getAttributeDataType(String attributeName) {
		String attributeDataType = "";
		try {
			for (Attribute attribute: attributes) {
				if ((Config.getConfig().compareAttributeNames(attribute.getAttributeName(), attributeName) == true)) { 
					attributeDataType = attribute.getType();
					break;
				}
			}
		} catch (Exception ex) {Log.logError("Tables.getAttributeDataType(): " + ex.getLocalizedMessage());}
		return attributeDataType;
	}
	public String getDBInstanceName() {
		return dBInstanceName;
	}
	public void setDBInstanceName(String dBInstanceName) {
		this.dBInstanceName = dBInstanceName;
	}

	public String toString() {
		return "table name = " + tableName;
	}
}

/*
CREATE TABLE `tcustomer` (
 `CustomerID` int(11) NOT NULL AUTO_INCREMENT,
 `CustomerCode` varchar(10) NOT NULL,
 `CustomerName` varchar(50) NOT NULL,
 `Address1` varchar(50) NOT NULL,
 `Address2` varchar(50) NOT NULL,
 `City` varchar(25) NOT NULL,
 `State` varchar(2) NOT NULL,
 `ZipCode` varchar(10) NOT NULL,
 `CustomerTypeID` int(11) NOT NULL,
 PRIMARY KEY (`CustomerID`),
 UNIQUE KEY `CustomerCode` (`CustomerCode`),
 KEY `CustomerTypeID` (`CustomerTypeID`),
 CONSTRAINT `FK_CustomerTypeID` FOREIGN KEY (`CustomerTypeID`) REFERENCES `tcustomertype` (`CustomerTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='Customer Table'
 */

