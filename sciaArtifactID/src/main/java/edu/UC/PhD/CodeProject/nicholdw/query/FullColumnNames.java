package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

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
	public FullColumnName getQueryAttribute(int i) {return fullColumnNames.get(i);}

	public void addFullColumnName(FullColumnName fullColumnName) {
		// Don't allow duplicates in the attribute collection
		boolean matchFound = false;
		for (FullColumnName fcn : fullColumnNames) {
			if (fcn.getRawData().trim().toLowerCase().equals(fullColumnName.getRawData().trim().toLowerCase())) {
				matchFound = true;
				break;
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
