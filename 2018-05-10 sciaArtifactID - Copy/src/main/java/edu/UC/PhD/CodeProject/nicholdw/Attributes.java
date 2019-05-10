/*
 * Table/query attributes
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;
import java.util.Iterator;

public class Attributes implements Iterable<Attribute> {

	private ArrayList<Attribute> attributes;

	/**
	 * Constructor
	 */
	public Attributes() {
		attributes = new ArrayList<Attribute>();
	}

	/**
	 * Retrieve the list of attributes in this Attribute List
	 * @return A reference to the attribute list in the current object.
	 */
	public ArrayList<Attribute> getAttributes ()
	{
		return attributes;
	}
	public Attribute getAttribute(int i) {
		return attributes.get(i);
	}
	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}
	public void clear() {
		attributes.clear();
	}
	@Override
	public Iterator<Attribute> iterator() {
		Iterator<Attribute> myIterator = attributes.iterator();
        return myIterator;
    }
	/***
	 * Add a foreign key to the attribute
	 * @param attributeName
	 * @param referencedTableName
	 * @param foreignKeyName
	 * @param referencedAttributeName
	 */
	public void addForeignKeyRef(String attributeName, String referencedTableName, String foreignKeyName, String referencedAttributeName) {
		// find the attribute in the AttributeList
		int i = 0;
		boolean found = false;
		for (Attribute attribute : attributes) {
			if (attribute.getAttributeName().equals(attributeName)) {
				found = true;
				break;
			}
			i++;
		}
		if (found) {
			attributes.get(i).addForeignKeyRef(referencedTableName, foreignKeyName, referencedAttributeName);
		}
	}
	public int size() {return attributes.size();}

	/**
	 * Search the list of attributes by attribute name
	 * @param attributeName The attribute name to search for
	 * @return The Attribute object corresponding to attributeName
	 */
	public Attribute findAttributeByName(String attributeName) {
		Attribute tmp = null;
		for (Attribute attribute: attributes) {
			if (attribute.getAttributeName().contentEquals(attributeName)) {
				tmp = attribute;
				break;
			}
		}
		return tmp;
	}
	/**
	 * Build a list of the attribute names in the Attributes object
	 * @return The list of attribute names, comma delimited.
	 */
	public String toString() {
		String result = "";
		String comma = "";
		for (Attribute attribute: attributes) {
			result += comma + attribute.getAttributeName();
			comma = ", ";
		}
		return result;
	}
}
