package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.Config;

public class FullColumnNames implements Iterable<FullColumnName> {

	private ArrayList<FullColumnName> fullColumnNames;

	/**
	 * Constructor
	 */
	public FullColumnNames() {
		fullColumnNames = new ArrayList<FullColumnName>();
	}
	/**
	 * Retrieve the list of QueryAttributes in this QueryAttribute List
	 * @return A reference to the queryAttribute list in the current object.
	 */
	public ArrayList<FullColumnName> getFullColumnNames (){return fullColumnNames;}
	public FullColumnName get(int i) {return fullColumnNames.get(i);}

	public void addFullColumnName(FullColumnName fullColumnName) {
		// Don't allow duplicates in the attribute collection: check schema/table/attribute and check that if either rawData is non-blank then they must not match
		boolean matchFound = false;
		for (FullColumnName fcn : fullColumnNames) {
			if (Config.getConfig().compareAttributeNames(fcn.getAttributeName(), fullColumnName.getAttributeName()) &&
			    Config.getConfig().compareSchemaNames(fcn.getSchemaName(), fullColumnName.getSchemaName()) &&
				Config.getConfig().compareTableNames(fcn.getTableName(), fullColumnName.getTableName())) {
				if ((fullColumnName.getRawData().toUpperCase().equals(fcn.getRawData().toUpperCase())) &&
					(fullColumnName.getRawData().length() > 0 || fcn.getRawData().length() > 0)) {
					matchFound = true;
					break;
				}
			}
		}
		if (!matchFound) {
			fullColumnNames.add(fullColumnName);
		}
	}
	public void clear() {fullColumnNames.clear();}
	@Override
	public Iterator<FullColumnName> iterator() {
		Iterator<FullColumnName> myIterator = fullColumnNames.iterator();
        return myIterator;
    }
	public String toString() {return fullColumnNames.size() + " attributes";}
	public int size() { return fullColumnNames.size();}
}
