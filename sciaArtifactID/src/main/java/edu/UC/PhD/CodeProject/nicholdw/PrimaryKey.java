/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import lib.MySQL;

public class PrimaryKey {
	private Attributes globalAttributeList;
	private int[] attributeMask;
	private ArrayList<int[]> attributeMaskList;		// The list of attribute combinations that can be candidate keys
	private int totalColumnsInFirstRow;

	/**
	 * Find Candidate Keys in a CSV data file
	 * @param fileName The filename with the CSV file
	 * @param firstColumnHasAttributeName true if the first row contains column names, false otherwise
	 * @return The list of Candidate Keys
	 */
	ArrayList<Attributes> Find(String fileName, String targetTable, boolean firstColumnHasAttributeName, boolean dataAlreadyLoaded) {
		ArrayList<Attributes> attributeLists = new ArrayList<Attributes>();
		Results results = new Results();
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			globalAttributeList = new Attributes();
			attributeMaskList = new ArrayList<int[]>();
			String tmp;
			if (!dataAlreadyLoaded) {
				if (firstColumnHasAttributeName) {
					// Grab the attribute names into an ArrayList
					tmp = br.readLine();
					String[] attributeNames = tmp.split(",");
					for (String attributeName : attributeNames) {
						globalAttributeList.addAttribute(new Attribute(attributeName.trim()));
					}
				} else {
					// The first line is just data, no attribute names.
					// All we can do is make up some sequential names.
					tmp = br.readLine();
					String attributeValues[] = tmp.split(",");
					for (int i = 0; i < attributeValues.length; i++) {
						globalAttributeList.addAttribute(new Attribute(Config.getConfig().getAttributenameprefix() + (i+1)));
					}
				}
				try {br.close();} catch(Exception ex) {}
				loadIntoDatabase(fileName, firstColumnHasAttributeName, results, targetTable);
				System.out.println(results.count - results.rejected  + " records appended to database.");
				System.out.println(results.rejected + " records rejected.");
			} else {
				// We need to populate globalAttributeList with the column names from the actual table in our database
				populateGlobalAttributeListFromExistingTable(globalAttributeList, targetTable);
			}
			attributeMask = new int[globalAttributeList.getAttributes().size()];
			for (int i = 0; i < attributeMask.length; i++) {
				attributeMask[i] = 0;
			}
			attributeMask[attributeMask.length-1] = 1;
			java.sql.PreparedStatement preparedStatement = null;
			java.sql.ResultSet resultSet = null;
			java.sql.Connection connection = new MySQL().connectToDatabase("phd");
			preparedStatement = connection.prepareStatement("select * from " + targetTable);
			/*
			// Step through all the attributes except the first one, which is the Identity attribute that we added.
		    for (int i = 0; i < globalAttributeList.getAttributes().size(); i++) {
			    // ResultSet record pointer is initially before the first data set
			    boolean isUnique = true;
			    String attributeName = "";
		    	attributeName = globalAttributeList.getAttributes().get(i).getName();
		    	String cmd = "SELECT COUNT(*) as foo FROM "  + Config.getConfig().targetTable + " GROUP BY " + attributeName + " Order by foo Desc";
				preparedStatement = connection.prepareStatement(cmd);
				resultSet = preparedStatement.executeQuery() ;
				resultSet.next();
		    	long maxUniqueCount = resultSet.getLong(1);		// Columns index from 1.
		    	resultSet.close();
		    	if (maxUniqueCount != 1) {
		    		isUnique = false;
		    	}
			    if (isUnique) {
		    		System.out.println("Attribute [" + attributeName + "] could be a Candidate Key.");
			    } else {
		    		System.out.println("Attribute [" + attributeName + "] is not a Candidate Key.");
			    }
		    }
		    */
			System.out.println("PrimaryKey.Find(): finding candidate keys from " + globalAttributeList.getAttributes().size() + " attributes..." );
		    while (true) {
		    	String comma;
		    	comma = "";
		    	String cmd;
		    	String attributeNameList;
		    	attributeNameList = "";
		    	for (int i = 0; i < attributeMask.length; i++) {
			    	String attributeName;
			    	attributeName = globalAttributeList.getAttributes().get(i).getAttributeName();
		    		if (attributeMask[i] == 1) {
		    			attributeNameList += comma + attributeName;
		    			comma = ",";
		    		}
		    	}
		    	cmd = "SELECT COUNT(*) as foo FROM `" + targetTable + "` GROUP BY " + attributeNameList + " Order by foo Desc";
		    	//System.out.println(cmd);
				preparedStatement = connection.prepareStatement(cmd);
				resultSet = preparedStatement.executeQuery() ;
				resultSet.next();
		    	long maxUniqueCount = resultSet.getLong(1);		// Columns index from 1.
		    	resultSet.close();
		    	boolean isUnique = true;
		    	if (maxUniqueCount != 1) {
		    		isUnique = false;
		    	}
			    if (isUnique) {
		    		//System.out.println("Attribute [" + attributeNameList + "] could be a Candidate Key.");
		    		attributeMaskList.add(new int[attributeMask.length]);
		    		int idx = attributeMaskList.size()-1;		// The index of the last attributeMask added.
		    		for (int j = 0; j < attributeMask.length; j++) {
		    			attributeMaskList.get(idx)[j] = attributeMask[j];
		    		}
			    } else {
		    		//System.out.println("Attribute [" + attributeNameList + "] is not a Candidate Key.");
			    }
		    	if (incrementMask() == false) {break;}
		    }
		    // List all the candidate keys we found
		    String comma = " ";
		    /*
		    System.out.println("Candidate Keys:");
		    for (int i = 0; i < attributeMaskList.size(); i++) {
		    	int[] myAttributeList = attributeMaskList.get(i);
		    	comma = " ";
		    	for (int j = 0; j < myAttributeList.length; j++) {
		    		if (myAttributeList[j] == 1) {
		    			System.out.print(comma + Config.getConfig().attributeNamePrefix + globalAttributeList.getAttributes().get(j).getName());
		    			comma = ", ";
		    		}
		    	}
		    	if (comma.contains(",")) System.out.println("");
		    }
		    */
			System.out.println("PrimaryKey.Find(): reducing the set of candidate keys" );
		    try {
				// Reduce the collection of Candidate Keys
			    for (int i = 1; i < globalAttributeList.getAttributes().size() - 1; i++ ) {
			    	ArrayList<int[]> tmpAttributeMaskList = new ArrayList<int[]>(attributeMaskList);
			    	for (int[] myAttributeMask : tmpAttributeMaskList) {
						if (countOnes(myAttributeMask) == i) {
							int[] tmpAttributeMask;
							for (int n = 0; n < tmpAttributeMaskList.size(); n++) {
								tmpAttributeMask = tmpAttributeMaskList.get(n);
								int tmpCountOnes;
								tmpCountOnes = countOnes(tmpAttributeMask);
								if (tmpCountOnes > i) {
									int match;
									match = 0;
									for (int j = 0; j < myAttributeMask.length; j++) {
										if (myAttributeMask[j] == 1 && tmpAttributeMask[j] == 1) {match++;}
									}
									if (match == i) {
										attributeMaskList.remove(tmpAttributeMask);
									}
								}
							}
						}
					}
			    }
		    } catch (Exception ex) {
		    	System.out.println("Reducing collection of candidate keys: " + ex.getLocalizedMessage() );
		    }
		    // List all the candidate keys we found after the list has been reduced
		    comma = " ";
		    System.out.println("Candidate Keys Reduced List:");
		    for (int ii = 0; ii < attributeMaskList.size(); ii++) {
		    	int[] myAttributeList = attributeMaskList.get(ii);
		    	comma = " ";
		    	for (int j = 0; j < myAttributeList.length; j++) {
		    		if (myAttributeList[j] == 1) {
		    			System.out.print(comma + globalAttributeList.getAttributes().get(j).getAttributeName());
		    			comma = ", ";
		    		}
		    	}
		    	if (comma.contains(",")) System.out.println("");
		    }
			// Process all the records. Fun fun.
			//while (tmp.length() > 0) {
			//	break;
			//}
		} catch(Exception ex) {
			System.out.println(fileName + ": " + ex.getLocalizedMessage());
		}
		return attributeLists;
	}
	private int countOnes(int[] myArray) {
		int count = 0;
		for (int i = 0; i < myArray.length; i++) {
			if (myArray[i] == 1) {count++;}
		}
		return count;
	}
	/**
	 * Drop and create the target table that will contain our data
	 * @param numOfColumns Number of columns to create in the table. If zero, use attributeNames.size()
	 * @param attributeNames Names of the columns. if null, use numOfColumns and the default attribute prefix in Config class.
	 * @return
	 * @throws Exception
	 */
	private boolean createTable(String targetTable, int numOfColumns, String[] attributeNames) throws Exception {
		java.sql.Connection connection = null;
		boolean status = true;
		try {
			// Drop the table
			java.sql.PreparedStatement preparedStatement;
			connection = new MySQL().connectToDatabase("phd");
			String cmd = "Create table " + targetTable + "( ";
			cmd += Config.getConfig().getAttributeID() + " INT primary key auto_increment";
			String comma = ", ";
			if (numOfColumns != 0) {
				// Use the column count passed into the method
				for (int i = 0; i < numOfColumns; i++) {
					cmd += comma + Config.getConfig().getAttributenameprefix() + (i + 1) + " varchar(255)";
					comma = ", ";
				}
			} else {
				// Use the attribute names passed into the method
				for (String attributeName : attributeNames) {
					cmd += comma + attributeName.trim() + " varchar(255)";
					comma = ", ";
				}
			}
			//cmd += ", PRIMARY KEY (" + Config.getConfig().attributeID + ")";
			cmd += ")";
			//cmd += " AUTO_INCREMENT = (" + Config.getConfig().attributeID + ")";
			try {
				preparedStatement = connection.prepareStatement("DROP TABLE " + targetTable);
				preparedStatement.executeUpdate();
			} catch (Exception ex) {}
			preparedStatement = connection.prepareStatement(cmd);
		    preparedStatement.executeUpdate();
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {connection.close();} catch (Exception ex) {}
		}
		return status;
	}
	/**
	 * Load from CSV into database
	 * @param fileName Location of the CSV file containing the data to be imported
	 * @return Number of records loaded
	 */
	public void loadIntoDatabase(String fileName, boolean firstColumnHasAttributeName, Results results, String targetTable) {
		FileReader fr = null;
		BufferedReader br = null;
		results.count = 0;
		results.rejected = 0;
		String SQLStatementTemplate = "insert into  " + targetTable + " values (default";
		String SQLStatement = "";
		java.sql.Connection connection = null;
		boolean endsWithComma = false;
		try {
			connection = new MySQL().connectToDatabase("phd");
			java.sql.PreparedStatement preparedStatement = null;
			try {
				preparedStatement = connection.prepareStatement("DROP TABLE " + targetTable);
				preparedStatement.executeUpdate();
			} catch(Exception ex) {}
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			clearTable(connection, targetTable);
			String tmp = null;
			String[] attributeValues = null;
			if (firstColumnHasAttributeName) {
				String attributeNames = br.readLine();
				String tmpHeadings[] = attributeNames.split(",");
				totalColumnsInFirstRow = tmpHeadings.length;
				createTable(targetTable, 0, tmpHeadings); // Recreate the table with the appropriate number of columns
				System.out.println("The first row is column headings and there are " + tmpHeadings.length + " of them.");
				System.out.println("The first column heading is " + tmpHeadings[0]);
				System.out.println("The last column heading is " + tmpHeadings[tmpHeadings.length-1]);
				tmp = br.readLine();
			} else {
				// The first row is just data. We have to kludge the column names in the table
				tmp = br.readLine();
				if (tmp.endsWith(",")) {
					endsWithComma = true;
					tmp += " ";		// Force the split to honor all the delimiters
				} else {
					endsWithComma = false;
				}
				attributeValues = tmp.split(",");
				totalColumnsInFirstRow = attributeValues.length;
				System.out.println("The first row is data and there are " + attributeValues.length + " columns.");
				createTable(targetTable, attributeValues.length, null); // Recreate the table with the appropriate number of columns
			}
			while (true) {
				attributeValues = tmp.split(",");
				if (endsWithComma) {
					attributeValues[attributeValues.length-1] = "";		// Remove the " " we forced into the input string.
				}
				if (attributeValues.length == totalColumnsInFirstRow) {
					SQLStatement = SQLStatementTemplate;
					for (int i = 0; i < attributeValues.length; i++) {
						SQLStatement += ",?";
					}
					SQLStatement += ")";
					int i = 0;
					preparedStatement = connection.prepareStatement(SQLStatement);
					for (String attributeValue: attributeValues) {
						// Parameters start with 1
					    preparedStatement.setString(i+1, attributeValue.trim());
						i++;
						//System.out.println(attributeValue.trim());
					}
				    preparedStatement.executeUpdate();
				} else {
					//throw new Exception ("PrimaryKey.loadIntoDatabase(): Inconsistent number of columns in row " + (count + 1) + ". Expected " + totalColumnsInFirstRow + " and got " + attributeValues.length);
					System.out.println("PrimaryKey.loadIntoDatabase(): Skipping this record. Inconsistent number of columns in row " + (results.count + 1) + ". Expected " + totalColumnsInFirstRow + " and got " + attributeValues.length);
					results.rejected++;
				}
				results.count++;
			    //if (count == 187) {	System.out.println("");}
			    if (results.count % 1000 == 0) {System.out.println(results.count);}
				try {tmp = br.readLine();} catch (Exception ex) {break;}
				if (tmp == null) {break;}
				if (tmp.trim().length() == 0) {break;}
				if (tmp.endsWith(",")) {
					endsWithComma = true;
					tmp += " ";		// Force the split to honor all the delimiters. If the string ends with a comma the split() will stop early.
				} else {
					endsWithComma = false;
				}
			}
		} catch (Exception ex) {
			System.out.println("PrimaryKey.loadIntoDatabase(): " + ex.getLocalizedMessage());
			try {br.close();} catch (Exception ex1) {}
		} finally {
			try {br.close();} catch (Exception ex) {}
			try {connection.close();} catch (Exception ex) {}
		}
	}

	public void clearTable(java.sql.Connection connection, String tableName) {
		try {
			String cmd = "DELETE FROM `" + tableName + "`";
			// java.sql.PreparedStatement preparedStatement =
			connection.prepareStatement(cmd).executeUpdate();
		} catch (Exception ex) {
			//System.out.println("PrimaryKey.clearTable(): " + ex.getLocalizedMessage());
		}
	}

	public boolean incrementMask() {
		boolean zeroFound = false;
		int carry = 1;
		int digit;
		// Are there any zeroes left in the mask?
		for (int i = 0; i < attributeMask.length; i++) {
			if (attributeMask[i] == 0) {
				zeroFound = true;
				break;
			}
		}
		if (zeroFound == true) {
			for (int i = attributeMask.length-1; i >= 0; i--) {
				if (carry == 0) break;
				digit = attributeMask[i];
				if (digit == 1 && carry == 1) {
					attributeMask[i] = 0;
					carry = 1;
				} else if (digit == 0 && carry == 1) {
					attributeMask[i] = 1;
					carry = 0;
				}
			}
		}
		//System.out.println("");for (int j = 0; j < attributeMask.length; j++) {System.out.print(attributeMask[j]);}
		return zeroFound;
	}
	/**
	 * Populate the global attribute list from an existing table definition
	 * @param globalAttributeList
	 * @param tableName
	 */
	private void populateGlobalAttributeListFromExistingTable(Attributes globalAttributeList, String tableName) {
		String cmd = "select column_name,data_type from information_schema.columns where table_name =  '" + tableName + "' order by column_name;";
		java.sql.PreparedStatement preparedStatement = null;
		java.sql.ResultSet resultSet = null;
		java.sql.Connection connection = new MySQL().connectToDatabase("phd");
		try {
			preparedStatement = connection.prepareStatement(cmd);
			resultSet = preparedStatement.executeQuery() ;
			// ResultSet is initially before the first data set
		    while (resultSet.next()) {
		    	String columnName;
		    	columnName= resultSet.getString(1);
		    	globalAttributeList.addAttribute(new Attribute(columnName));
		    }
		} catch (Exception ex) {
			System.out.println("PrimaryKey.populateGlobalAttributeListFromExistingTable(): " + ex.getLocalizedMessage());
		}
	}
}
/**
 * The results of a call to loadIntoDatabase()
 * @author nicomp
 *
 */
class Results {
	public long count;
	public long rejected;
	public Results() {
		count = 0;
		rejected = 0;
	}
}
