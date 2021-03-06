/*
 * Table/query attributes
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation.GRAPH_NODE_ANNOTATION;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jDB;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaGraph;

public class TableAttributes implements Iterable<TableAttribute> {

	private ArrayList<TableAttribute> tableAttributes;

	/**
	 * Constructor
	 */
	public TableAttributes() {
		tableAttributes = new ArrayList<TableAttribute>();
	}

	/**
	 * Retrieve the list of attributes in this Attribute List
	 * @return A reference to the attribute list in the current object.
	 */
	public ArrayList<TableAttribute> getAttributes ()
	{
		return tableAttributes;
	}
	public TableAttribute getAttribute(int i) {
		return tableAttributes.get(i);
	}
	public void addAttribute(TableAttribute attribute) {
		tableAttributes.add(attribute);
	}
	public void clear() {
		tableAttributes.clear();
	}
	@Override
	public Iterator<TableAttribute> iterator() {
		return tableAttributes.iterator();
//		Iterator<TableAttribute> myIterator = tableAttributes.iterator();
//        return myIterator;
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
		for (TableAttribute attribute : tableAttributes) {
			if (attribute.getAttributeName().equals(attributeName)) {
				found = true;
				break;
			}
			i++;
		}
		if (found) {
			tableAttributes.get(i).addForeignKeyRef(referencedTableName, foreignKeyName, referencedAttributeName);
		}
	}
	public int size() {return tableAttributes.size();}

	/**
	 * Search the list of attributes by attribute name and table name
	 * @param attributeName The attribute name to search for
	 * @param tableName the table name to search for
	 * @return The Attribute object corresponding to tableName.attributeName
	 */
	public TableAttribute findAttributeBySchemaAndTableAndName(String schemaName, String tableName, String attributeName) {
		TableAttribute tmp = null;
		for (TableAttribute attribute: tableAttributes) {
			if (Config.getConfig().compareAttributeNames(attribute.getAttributeName(), attributeName) &&
				Config.getConfig().compareTableNames(attribute.getContainerName(), tableName) &&
				Config.getConfig().compareSchemaNames(attribute.getSchemaName(), schemaName)) {
				tmp = attribute;
				break;
			}
		}
		return tmp;
	}
	/**
	 * Search the list of attributes by attribute name and table name
	 * @param attributeName The attribute name to search for
	 * @param tableName the table name to search for
	 * @return The Attribute object corresponding to tableName.attributeName
	 */
	public TableAttribute findAttribute(TableAttribute targetTableAttribute) {
		TableAttribute tmp = null;
		for (TableAttribute tableAttribute: tableAttributes) {
			if ((Config.getConfig().compareAttributeNames(tableAttribute.getAttributeName(), targetTableAttribute.getAttributeName())) &&
				(Config.getConfig().compareTableNames(tableAttribute.getContainerName(), targetTableAttribute.getContainerName())) &&
				(Config.getConfig().compareSchemaNames(tableAttribute.getSchemaName(), targetTableAttribute.getSchemaName()))) {
				tmp = tableAttribute;
				break;
			}
		}
		return tmp;
	}
	/**
	 * Search the list of attributes by attribute name and table name
	 * @param attributeName The attribute name to search for
	 * @param tableName the table name to search for
	 * @return The Attribute object corresponding to tableName.attributeName
	 */
	public TableAttribute findAttributeByTableAndName( String tableName, String attributeName) {
		TableAttribute tmp = null;
		for (TableAttribute attribute: tableAttributes) {
			if ((Config.getConfig().compareAttributeNames(attribute.getAttributeName(), attributeName)) &&
				(Config.getConfig().compareTableNames(attribute.getContainerName(), tableName))) {
				tmp = attribute;
				break;
			}
		}
		return tmp;
	}
	/**
	 * Search the list of attributes by attribute name
	 * @param attributeName The attribute name to search for
	 * @return The Attribute object corresponding to attributeName
	 */
	public TableAttribute findAttributeByName(String attributeName) {
		TableAttribute tmp = null;
		for (TableAttribute attribute: tableAttributes) {
			if (Config.getConfig().compareAttributeNames(attribute.getAttributeName(), attributeName)) {
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
		for (TableAttribute attribute: tableAttributes) {
			result += comma + attribute.getAttributeName();
			comma = ", ";
		}
		return result;
	}
	public int applyNodeChangeLabels() {
		int count = 0;
		for (TableAttribute tableAttribute: tableAttributes) {
			if (tableAttribute.getGraphNodeAnnotation().getGraphNodeAnnotation() == GRAPH_NODE_ANNOTATION.Changed) {
				Neo4jDB.renameNodeLabel(tableAttribute.getKey(), SchemaGraph.attributeNodeLabel, SchemaGraph.affectedAttributeNodeLabel);
			}
		}
		return count;
	}
}
