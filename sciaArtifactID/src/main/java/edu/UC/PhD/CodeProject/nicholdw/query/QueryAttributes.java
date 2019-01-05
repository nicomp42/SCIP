package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.Config;

public class QueryAttributes implements Iterable<QueryAttribute> {

	private ArrayList<QueryAttribute> queryAttributes;

	/**
	 * Constructor
	 */
	public QueryAttributes() {
		queryAttributes = new ArrayList<QueryAttribute>();
	}
	/**
	 * Retrieve the list of QueryAttributes in this QueryAttribute List
	 * @return A reference to the queryAttribute list in the current object.
	 */
	public ArrayList<QueryAttribute> getAttributes (){return queryAttributes;}
	public QueryAttribute getQueryAttribute(int i) {return queryAttributes.get(i);}

	public void addAttribute(QueryAttribute queryAttribute) {
		// Don't allow duplicates in the attribute collection
		boolean matchFound = false;
		for (QueryAttribute qa : queryAttributes) {
			if (qa.getAttributeName().trim().toLowerCase().equals(queryAttribute.getAttributeName().trim().toLowerCase()) &&
				qa.getSchemaName().trim().toLowerCase().equals(queryAttribute.getSchemaName().trim().toLowerCase()) &&
				qa.getTableName().trim().toLowerCase().equals(queryAttribute.getTableName().trim().toLowerCase())) {
				// We found a match. If the alias doesn't already exist, add that to the matching Query Attribute. Otherwise we're done.
				for (AliasNameClassOLD aliasName: queryAttribute.getAliasNames()) {
					if (!qa.getAliasNames().contains(aliasName)) {
						qa.addAliasName(aliasName);
						break;
					}
				}
				matchFound = true;
				break;
			}
		}
		if (!matchFound) {
			queryAttributes.add(queryAttribute);
		}
	}
	public void clear() {queryAttributes.clear();}
	@Override
	public Iterator<QueryAttribute> iterator() {
		Iterator<QueryAttribute> myIterator = queryAttributes.iterator();
        return myIterator;
    }
	public String toString() {return queryAttributes.size() + " query attributes";}
	public int size() { return queryAttributes.size();}
	/**
	 * Does this collection contain a particular QueryAttribute?
	 * @param queryAttribute The QueryAttribute to look for
	 * @return true if this collection contains a particular QueryAttribute, false otherwise
	 */
	public boolean contains(QueryAttribute queryAttribute) {
		boolean result = false;
		for (QueryAttribute qa : queryAttributes) {
			if (qa.equals(queryAttribute)) {result = true; break;}
		}
		return result;
	}
	/**
	 * Find a QueryAttribute by AttributeName or AliasName
	 * @param attributeOrAliasName The name/alias to match
	 * @return The QueryAttribute that is found, else null if not found
	 */
	public QueryAttribute findAttribute(String attributeOrAliasName) {
		QueryAttribute result = null;
		Boolean matchFound;
		for (QueryAttribute queryAttribute : queryAttributes) {
			matchFound = false;
			if (Config.getConfig().compareAttributeNames(queryAttribute.getAttributeName(), attributeOrAliasName) == true) {
				result = queryAttribute;
				matchFound = true;
				break;
			}
			if (matchFound == false) {
				for (AliasNameClassOLD aliasName : queryAttribute.getAliasNames()) {
					if (Config.getConfig().compareAliasNames(aliasName.getAliasName(), attributeOrAliasName) == true) {
						result = queryAttribute;
						matchFound = true;
						break;
					}
				}
			}
			if (matchFound == true) {break;}
		}
		return result;
	}
	/***
	 * Find a QueryAttribute by ID
	 * @param ID The ID to search for
	 * @return The QueryAttribute that was found, or null
	 */
	public QueryAttribute findAttributeByID(String ID) {
		QueryAttribute result = null;
		for (QueryAttribute queryAttribute : queryAttributes) {
			if (queryAttribute.getID().contentEquals(ID)) {
				result = queryAttribute;
				break;
			}
		}
		return result;
	}
}
