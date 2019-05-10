package edu.UC.PhD.CodeProject.nicholdw;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import lib.MySQL;

import java.util.Iterator;

import lib.SQLUtils;

public class Determinants implements Iterable<Determinant> {
	private ArrayList<Determinant> determinants;
	private Table table;
	private BufferedWriter bw;
	private boolean ignoreNULLValuesWhenCheckingForDeterminants;

	public Determinants(Table table) {
		this.table = table;
		determinants = new ArrayList<Determinant>();
		ignoreNULLValuesWhenCheckingForDeterminants = false;
	}

	/**
	 * Eliminate the redundant determinants in the collection.
	 * For example, (A1,A2)->A3 is redundant if we also have (A1)->A3
	 */
	public void reduceDeterminants() {
		try {
			// Set all the determinants to false. Assume none are redundant.
			for (Determinant myDeterminant : determinants) {
				myDeterminant.setRedundant(false);
			}
			int attributeCountInTable = table.getAttributes().size();
			// Process all determinants with one attribute, then 2, etc., up to (total attributes in the table - 1)
			for (int i = 1; i < attributeCountInTable - 1; i++) {
				for (Determinant targetDeterminant : determinants) {
					if (targetDeterminant.isRedundant() == true) {continue;}
					if (targetDeterminant.getAttributes().size() != i) {continue;}
					String attributeThatIsDetermined = targetDeterminant.getAttributeThatIsDetermined().getAttributeName();
					// We have a determinant: now we need to look at each of the other determinants to see if:
					// 1. It determines the same attribute,
					// 2. AND it contains a superset of the attributes in our target determinant
					for (Determinant myDeterminant : determinants) {
						if (myDeterminant == targetDeterminant) continue;		// Don't compare the target with the target
						if (!(attributeThatIsDetermined.contentEquals(myDeterminant.getAttributeThatIsDetermined().getAttributeName()))) continue;	// Don't compare unless the attribute determined is the same
						//  Whew. Now we can look for a superset.
						boolean isSuperset = checkForSuperset(targetDeterminant.getAttributes(), myDeterminant.getAttributes());
						if (isSuperset) {myDeterminant.setRedundant(true);}
					}
				}
			}
			Iterator<Determinant> i = iterator();
			// http://stackoverflow.com/questions/1196586/calling-remove-in-foreach-loop-in-java
			while (i.hasNext()) {
				Determinant myDeterminant = i.next(); // must be called before you can call i.remove()
				if (myDeterminant.isRedundant()) {i.remove();}
			}
		} catch (Exception ex) {
			Config.getConfig().DebugLine("Determinant.reduceDeterminants("	+ table.getTableName() + "): "+ ex.getLocalizedMessage());
		}
	}
	/**
	 * Check to see if a set of attributes is a superset of another set of attributes
	 * @param targetDeterminantAttributes
	 * @param myDeterminantAttributes
	 * @return True if myDeterminantAttributes is a superset of targetDeterminantAttributes, false otherwise.
	 */
	private boolean checkForSuperset(Attributes targetDeterminantAttributes, Attributes myDeterminantAttributes) {
		boolean isSuperset = true;
		// myDeterminantAttributes must have more members than  targetDeterminantAttributes
		if (targetDeterminantAttributes.size() < myDeterminantAttributes.size()) {
			// We need to know if all the attributes in targetDeterminantAttributes are also in myDeterminantAttributes
			for (Attribute targetAttribute: targetDeterminantAttributes) {
				if (myDeterminantAttributes.findAttributeByName(targetAttribute.getAttributeName()) == null) {
					// Attribute is in targetDeterminantAttributes but not in myDeterminantAttributes, give up.
					isSuperset = false;
					break;
				}
			}
		} else {isSuperset = false;}
		return isSuperset;
	}

	/***
	 * Check to see if a set of one or more attributes is a determinant
	 *
	 * @param attributes
	 *            The Attribute set to check
	 * @return True if the attribute set is a determinant, false otherwise
	 */
	public boolean isDeterminant(Attributes attributes) {
		boolean result = false;
		int count;
		for (Determinant myDeterminant : determinants) {
			count = 0;
			Attributes myAttributes = myDeterminant.getAttributes();
			if (attributes.size() == myAttributes.size()) {
				for (Attribute attribute : attributes) {
					if (myAttributes.findAttributeByName(attribute.getAttributeName()) != null) {
						count++;
					}
				}
				if (count == attributes.size()) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Retrieve the list of indexes in this Index List
	 *
	 * @return A reference to the index list in the current object.
	 */
	public ArrayList<Determinant> getDeterminants() {
		return determinants;
	}

	/**
	 *
	 * @param determinant The Determinant to be added
	 * @return True if the new determinant was added, false if it was already there
	 */
	public boolean addDeterminant(Determinant determinant) {
		boolean result = true;
		for (Determinant existingDeterminant : determinants) {
			if (existingDeterminant.compare(determinant)) {
				result = false;
				break;
			}
		}
		if (result == true) {determinants.add(determinant);}

		return result;
	}

	@Override
	public Iterator<Determinant> iterator() {
		Iterator<Determinant> iprof = determinants.iterator();
		return iprof;
	}

	/**
	 * Analyze the table for possible determinants based on the table contents
	 *
	 * @return The number of possible determinants identified
	 */
	public int AnalyzeTableForDeterminants(boolean ignoreNULLValuesWhenLookingForDeterminants, String debugFilename) {
		FileWriter fw;
		this.ignoreNULLValuesWhenCheckingForDeterminants = ignoreNULLValuesWhenLookingForDeterminants;
		if (debugFilename.trim().length() > 0) {
			try {
				fw = new FileWriter(debugFilename.trim());
				bw = new BufferedWriter(fw);
			} catch (Exception ex) {
				Config.getConfig().DebugLine("Determinants.AnalyzeTableForDeterminants: opening debug file " + debugFilename + ": " + ex.getLocalizedMessage());
				bw = null;
			}
		}
		// We need to keep track of what we've already found so we don't build supersets from what we've already used
		ArrayList<int[]> tmpDeterminantPatterns = new ArrayList<int[]>();
		ArrayList<String> tmpWhatTheDeterminantPatternDetermines = new ArrayList<String>();

		// This will work perfectly. It's one query per set of attributes. Very elegant.
		// select count(DISTINCT store), count(DISTINCT address1), count(DISTINCT address2) from tStore group by city, state
		int countOfOnes = 0;
		int currentNumberOfOnes = 0; // The current number of ones in idx[] that
										// we are working with in this iteration
										// of the major loop
		table.setAttributes(Table.readAttributesFromTableDefinition(table.getTableName(), table.getSchemaName()));
		// Set up an array of flags to allow us to pick out all combinations of the attributes
		int[] idx = new int[(table.getAttributes()).size()];
		int[] idxAlreadyUsed = new int[(table.getAttributes()).size()];
		for (int i = 0; i < idx.length; i++) {idx[i] = 0;}
		for (int i = 0; i < idxAlreadyUsed.length; i++) {idxAlreadyUsed[i] = 0;}
		Attributes groupByAttributes = null;
		Attributes countOfAttributes = null;
		Attributes attributes = table.getAttributes();
		// Step through all combinations of attributes to check for determinants
		java.sql.Connection connection = new MySQL().connectToDatabase(table.getSchemaName());
		// CurrentNumberOfOnes must be less than the total number of attributes because otherwise we won't have anything to count in the select string
		//int debugCounter = 0;
		String sql = null;
		for (currentNumberOfOnes = 1; currentNumberOfOnes < attributes.size(); currentNumberOfOnes++) {
			//Config.getConfig()DebugLine(""+debugCounter++);
			for (int i = 0; i < idx.length; i++) {idx[i] = 0;}
			while (true) {
				// Increment the array of indices until we overflow
				if (incrementIdx(idx) == true) break;
				countOfOnes = countOnes(idx);
				// We have to process idx in ascending order by number of 1's in it.
				if (countOfOnes != currentNumberOfOnes)	continue; // abort this iteration unless we have the correct number of 1's in idx.
				// Build the combination of attributes that we are checking
				//if ((currentNumberOfOnes == 1) && (idx[0] == 1))
				//	Config.getConfig()DebugLine("Hoo");
				groupByAttributes = new Attributes();
				countOfAttributes = new Attributes();
				String groupByComma, groupBy, selectComma, select;
				groupByComma = "";
				selectComma = "";
				select = "";
				groupBy = "";
				for (int i = 0; i < idx.length; i++) {
					if (idx[i] == 0) {	// These are the attributes we want to do nothing with
					}
					if (idx[i] == 1) {	// These are the attributes we want to group by
						groupByAttributes.addAttribute(attributes.getAttribute(i));
						groupBy += groupByComma	+ MySQL.wrapAttributeName(attributes.getAttribute(i).getAttributeName());
						groupByComma = ", ";
					}
					if (idx[i] == 2) {	// These are the attributes we want to count the unique instances of
						countOfAttributes.addAttribute(attributes.getAttribute(i));
						String useNulls;
						if (this.ignoreNULLValuesWhenCheckingForDeterminants == false) {
							useNulls = " + (case when count(" +  MySQL.wrapAttributeName(attributes.getAttribute(i).getAttributeName()) + ") <> count(*) then 1 else 0 end)";
						} else {useNulls = "";}
						select += selectComma + " COUNT(DISTINCT "+ MySQL.wrapAttributeName(attributes.getAttribute(i).getAttributeName()) + ") " + useNulls + " as " + MySQL.wrapAttributeName("countOf_" + attributes.getAttribute(i).getAttributeName());
						selectComma = ", ";
					}
				}
				// If the idx array doesn't have at least one 1 and one 2 then we can't do anything.
				if (select.trim().length() == 0 || groupBy.trim().length() == 0) continue;
				// Build the query for the current set of attributes we are checking
				sql = "SELECT " + select + " from " + table.getTableName() + " GROUP BY " + groupBy;
				Boolean isDeterminant;
				isDeterminant = true; // Hope for the best
				java.sql.PreparedStatement preparedStatement;
				preparedStatement = null;
				java.sql.ResultSet resultSet = null;
				resultSet = null;
				try {
					Config.getConfig().DebugLine(sql);
					writeToDebugFile(sql);
					preparedStatement = connection.prepareStatement(sql);
					resultSet = preparedStatement.executeQuery();
					// resultSet now has all the answers in it for this attribute set of possible determinants. All we have to do is process it.
					// Step through all the records to see if any of the columns are the same across all the rows.
					String columnName = "";
					java.sql.ResultSetMetaData resultSetMetaData;
					resultSetMetaData = resultSet.getMetaData();
					int columnIdx = 1; // This is the index into the resultSet columns and resultSetMetaData columns. Do not use the for loop counter!
					for (int i = 0; i < idx.length; i++) {
						if (idx[i] == 2) {	// We only care about the attributes that were count-ed
							resultSet.beforeFirst();
							columnName = resultSetMetaData.getColumnName(columnIdx);
							if (columnName.startsWith("countOf_")) { // Starts with is case-sensitive
								columnName = columnName.replace("countOf_", "");
								isDeterminant = true; // Hope for the best
								while (resultSet.next()) {
									int attributeValue = Integer.valueOf(resultSet.getString(columnIdx)); // Index is one-based. Sigh
									if (attributeValue != 1) {isDeterminant = false; break;}
								}
								if (isDeterminant) {
									Determinant newDeterminant;
									newDeterminant = new Determinant();
									newDeterminant.addAttributes(groupByAttributes);
									newDeterminant.setAttributeThatIsDetermined(countOfAttributes.getAttribute(columnIdx - 1));
									// Have we already found a subset of this value of idx?
									boolean foundAnExtraGroupByInTmpIdx, foundAnExtraCountOfInTmpIdx;
									boolean foundAnExtraGroupByInIdx;
									boolean foundAnExtraCountOfInIdx;
									boolean foundACountOfThatShouldBeAGroupByInTmpIdx;
									boolean foundAGroupByThatShouldBeACountOfInTmpIdx;
									boolean soFarSoGood;
									soFarSoGood = true;
									// Check the current pattern against all the patterns on file that have been identified as determinants for the same set of attribute(s).
									int n = 0;
									// tmpDeterminantPatterns will get big, in the thousands, and this logic will take forever. ToDo - optimize.
									for (int[] tmpIdx : tmpDeterminantPatterns) {
										if (countOfAttributes.getAttribute(columnIdx - 1).getAttributeName().contentEquals(tmpWhatTheDeterminantPatternDetermines.get(n))) {
											foundAnExtraGroupByInIdx = false;
											foundAnExtraCountOfInIdx = false;
											foundAnExtraGroupByInTmpIdx = false;
											foundAnExtraCountOfInTmpIdx = false;
											foundACountOfThatShouldBeAGroupByInTmpIdx = false;
											foundAGroupByThatShouldBeACountOfInTmpIdx = false;
											for (int k = 0; k < tmpIdx.length; k++) {
												// If they match, do nothing
												if (idx[k] == tmpIdx[k]) {continue;}
												switch (idx[k]) {
												case 0:			// 0 = ignore this attribute
													switch (tmpIdx[k]) {
													case 1:
														foundAnExtraGroupByInTmpIdx = true;
														break;
													case 2:
														foundAnExtraCountOfInTmpIdx = true;
														break;
													}
												case 1:			// 1 = group by
													switch (tmpIdx[k]) {
													case 0:
														foundAnExtraGroupByInIdx = true;
														break;
													case 2:
														foundACountOfThatShouldBeAGroupByInTmpIdx = true;
														//soFarSoGood = false;
														break;
													}

												case 2:			// 2 = count of
													switch (tmpIdx[k]) {
													case 0:
														foundAnExtraCountOfInIdx = true;
														//soFarSoGood = false;
														break;
													case 1:
														foundAGroupByThatShouldBeACountOfInTmpIdx = true;
														break;
													}
												}
											}
											//if (!(foundAnExtraGroupByInIdx && foundAnExtraCountOfInIdx &&
											//  	  foundACountOfThatShouldBeAGroupByInTmpIdx && foundAGroupByThatShouldBeACountOfInTmpIdx &&
											//   	  foundAnExtraGroupByInTmpIdx && foundAnExtraCountOfInTmpIdx )) {
											//	soFarSoGood = false;
											//	break;
											//}
										}
										n++;
									}
									// If the current pattern is a superset of a pattern already identified as a determinant that determines the same attribute, don't store it.
									if (soFarSoGood) {
										// If we get this far we have found a pattern that is not a superset of a pattern that has already been identified as a determinant of the same attribute. Yipee!
//										Config.getConfig()DebugLine("According to the data in " + table.getName() + ": " + groupByAttributes.toString() + " could be a determinant of " + countOfAttributes.getAttribute(columnIdx-1).getName());
										tmpDeterminantPatterns.add(idx.clone());
										tmpWhatTheDeterminantPatternDetermines.add(countOfAttributes.getAttribute(columnIdx - 1).getAttributeName());
										// if (groupByAttributes.toString().contentEquals("BufferID, StoreID, Store, Address1, Address2, City, State, Zip")) {
										// Config.getConfig()DebugLine("Bingo");
										// }
										addDeterminant(newDeterminant);	//determinants.add(newDeterminant);
									}
								} else {
									// Config.getConfig()DebugLine("According to the data in " + table.getName() + ": " + groupByAttributes.toString() + " could NOT be a determinant of " + countOfAttributes.getAttribute(columnIdx-1).getName());
								}
							}
							columnIdx++;
						}
					}
					try {
						resultSet.close();
					} catch (Exception ex) {
						Config.getConfig().DebugLine("Determinant.AnalyzeTableForDeterminants("	+ table.getTableName() + "): "+ ex.getLocalizedMessage());
					}
				} catch (Exception ex) {
					Config.getConfig().DebugLine("Determinant.AnalyzeTableForDeterminants("	+ table.getTableName() + "): "+ ex.getLocalizedMessage());
					Config.getConfig().DebugLine("Determinant.AnalyzeTableForDeterminants("	+ table.getTableName() + "): sql = " + sql );
				}
				try {resultSet.close();} catch (Exception ex) {	}
			}
		}
		try {bw.close();
		} catch (Exception ex) {
			System.out.println("Determinants.AnalyzeTableForDeterminants(): " + ex.getMessage());
		}
		reduceDeterminants();
		return determinants.size();
	}

	/**
	 * Count the ones in our array of index flags
	 *
	 * @param idx The array of index flags
	 * @return The number of 1's in the array
	 */
	private int countOnes(int[] idx) {
		int count = 0;
		for (int i = 0; i < idx.length; i++) {
			if (idx[i] == 1)
				count++;
		}
		return count;
	}

	/**
	 * Check to see if a comma-delimited string is a proper subset of another
	 * comma-delimited string. See
	 * http://mathworld.wolfram.com/ProperSubset.html for proper subest. s1 and
	 * s2 must be in the same order.
	 *
	 * @param s1
	 *            the string that may be a proper subset of s2
	 * @param s2
	 *            the string that may be a proper superset of s1
	 * @return private boolean isSubset(String s1, String s2) { boolean result =
	 *         false; String[] s1Split = s1.split(","); String[] s2Split =
	 *         s2.split(","); if (s1Split.length < s2Split.length) { // Trim the
	 *         strings just in case... int i = 0; for (String s : s1Split)
	 *         {s1Split[i++] = s.trim();} i = 0; for (String s : s2Split)
	 *         {s1Split[i++] = s.trim();} } return result; }
	 */

	/**
	 * Delete the determinants that are redundant, after calling
	 * AnalyzeTableForDeterminants() private void pruneDeterminants() {
	 * ArrayList<Determinant> tmpDeterminants = new ArrayList<Determinant>();
	 *
	 * for (Determinant determinant : determinants) { if
	 * (determinant.getAttributes().size() == 1) { Attribute attribute =
	 * (determinant.getAttributes()).getAttribute(0);
	 * tmpDeterminants.add(determinant); // Definitely keep this one because it
	 * has only one attribute // Now see if this attribute appears in any other
	 * determinants. Those determinants will be pruned
	 *
	 * } } }
	 */
	/**
	 * Make a shallow copy of the determinant list in this object
	 *
	 * @return a shallow copy of the determinant list in this object
	 */
	private ArrayList<Determinant> CloneDeterminantList() {
		ArrayList<Determinant> newDeterminantList = new ArrayList<Determinant>();
		for (Determinant determinant : determinants) {
			newDeterminantList.add(determinant);
		}
		return newDeterminantList;
	}

	/***
	 * Increment the array of 0/1/2 flags flags
	 *
	 * @param idx
	 * @return true on overflow
	 */

	private boolean incrementIdx(int[] idx) {
		boolean overflow = false;
		int carry = 1;
		int sum;
		for (int i = idx.length - 1; i >= 0; i--) {
			sum = idx[i] + carry;
			switch (sum) {
			case 0:
				carry = 0;
				break;
			case 1:
				carry = 0;
				idx[i] = 1;
				break;
			case 2:
				carry = 0;
				idx[i] = 2;
				break;
			case 3:
				carry = 1;
				idx[i] = 0;
				break;
			}
		}
		if (carry == 1) {
			overflow = true;
		}
		return overflow;
	}

	private int sumDigits(int num) {
		int sum = 0;
		char[] digits = String.valueOf(num).toCharArray();

		return sum;
	}
	/**
	 * Write the current list of determinants to a file
	 * @param filename
	 * @return The number of determinants written
	 */
	public int export(String filename) {
		int result = 0;
		boolean firstLine = true;
		try {
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw  = new BufferedWriter(fw);
			for (Determinant myDeterminant : determinants) {
				if (!firstLine) {bw.newLine();}
				bw.write(myDeterminant.toCSV());
				result++;
				firstLine = false;
			}
			bw.close();
		} catch (Exception ex) {
			Config.getConfig().DebugLine("Determinants.export(" + filename + "): " + ex.getLocalizedMessage());
		}
		return result;
	}
	/***
	 * Write to debug file
	 * @param msg String to write to debug file
	 */
	private void writeToDebugFile(String msg) {
		if (bw != null) {
			try {
				bw.write(msg);
				bw.newLine();
			} catch (Exception ex) {

			}
		}
	}
	class TaggedAttribute {
		Attribute attribute;
		boolean tagged;
		public TaggedAttribute(Attribute attribute) {
			tagged = false;
			this.attribute = attribute;
		}
	}
}



/*
 * // Make it into something that we can use in a WHERE clause String
 * formattedAttributeValue = attributeValue; switch
 * (Attribute.mapType(attribute.getType())) { case intType:
 * formattedAttributeValue = attributeValue; break;
 *
 * case varchar: formattedAttributeValue =
 * SQLUtils.DoubleQuoteMe(attributeValue); break;
 *
 * default: Config.getConfig()DebugLine("Determinants.AnalyzeTableForDeterminants(" +
 * table.getName() + ") unrecognized data type: " + attribute.getType()); break;
 * }
 *
 * // Compose a query to count the number of different values for (Attribute
 * testAttribute: table.getAttributes()) { if
 * (testAttribute.getName().equalsIgnoreCase(attribute.getName())) continue; //
 * Don't compare the attribute to itself // The count aggregate returns a long.
 * Wow. long countOfUniqueValues = (long)
 * SQLUtils.MyDLookup(testAttribute.getName(), table.getName(),
 * attribute.getName() + " = " + formattedAttributeValue, "COUNT",
 * attribute.getName() ,connection) ; // If the answer is not one, then give up
 * because this attribute can't be a determinant
 * Config.getConfig()DebugLine("Determinant.AnalyzeTableForDeterminants(" + table.getName()
 * + "): " + attribute.getName() + "= " + formattedAttributeValue + " : " +
 * testAttribute.getName() + ", " + countOfUniqueValues); //if
 * (countOfUniqueValues != 1) {isDeterminant = false; break;} if
 * (countOfUniqueValues > 1) {isDeterminant = false; break;} // If the group by
 * field is null, the COUNT aggregate will return a 0. Yikes. }
 */
