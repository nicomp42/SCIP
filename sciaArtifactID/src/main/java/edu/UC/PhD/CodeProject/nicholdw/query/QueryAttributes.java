/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute.ATTRIBUTE_DISPOSITION;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryClause;
import java.util.Iterator;
import edu.UC.PhD.CodeProject.nicholdw.TableAttribute;
import edu.UC.PhD.CodeProject.nicholdw.TableAttributes;
import edu.UC.PhD.CodeProject.nicholdw.Attributable;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation;
import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

public class QueryAttributes implements Iterable<QueryAttribute>, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8144072456742252403L;
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
	
	public void addTableAttributes(String schemaName, TableAttributes attributes) {
		for (TableAttribute a: attributes) {
			addAttribute(new QueryAttribute(schemaName, a.getContainerName(), a.getAttributeName(), 
					     (AliasNameClassOLD)null, new QueryClauseSelect(), "", ATTRIBUTE_DISPOSITION.Select));
		}
	}
	/**
	 * No deep copy is performed.
	 * @param queryAttribute
	 * @param containgSchemaName
	 * @param containingViewName
	 */
	public void addAttribute(QueryAttribute queryAttribute, String containgSchemaName, String containingViewName) {
		// Don't allow duplicates in the attribute collection
		boolean matchFound = false;
		for (QueryAttribute qa: queryAttributes) {
			if (qa.getAttributeName().trim().toLowerCase().equals(queryAttribute.getAttributeName().trim().toLowerCase()) &&
				qa.getSchemaName().trim().toLowerCase().equals(queryAttribute.getSchemaName().trim().toLowerCase()) &&
				qa.getContainerName().trim().toLowerCase().equals(queryAttribute.getContainerName().trim().toLowerCase())) {
				// We found a match. If the alias doesn't already exist, add that to the matching Query Attribute. Otherwise we're done.
				for (AliasNameClassOLD aliasName: queryAttribute.getAliasNames()) {
					if (!qa.getAliasNames().contains(aliasName)) {
						qa.addAliasName(aliasName);
					}
				}
				matchFound = true;
				break;
			}
		}
		if (!matchFound) {
			queryAttributes.add(queryAttribute);
			queryAttribute.setContainingSchemaName(containgSchemaName); 
			queryAttribute.setContainingViewName(containingViewName);
		}
	}
	/**
	 * No deep copy is performed
	 * @param queryAttribute
	 */
	public void addAttribute(QueryAttribute queryAttribute) {
		// Don't allow duplicates in the attribute collection
		boolean matchFound = false;
		for (QueryAttribute qa: queryAttributes) {
			if (qa.getAttributeName().trim().toLowerCase().equals(queryAttribute.getAttributeName().trim().toLowerCase()) &&
				qa.getSchemaName().trim().toLowerCase().equals(queryAttribute.getSchemaName().trim().toLowerCase()) &&
				qa.getContainerName().trim().toLowerCase().equals(queryAttribute.getContainerName().trim().toLowerCase())) {
				// We found a match. If the alias doesn't already exist, add that to the matching Query Attribute. Otherwise we're done.
				for (AliasNameClassOLD aliasName: queryAttribute.getAliasNames()) {
					if (!qa.getAliasNames().contains(aliasName)) {
						qa.addAliasName(aliasName);
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
	 * @param compareNameOnly Just compare attribute names. Ignore schema and table
	 * @return true if this collection contains a particular QueryAttribute, false otherwise
	 */
	public boolean contains(Attributable queryAttribute, Boolean compareNameOnly) {
		boolean result = false;
		if (compareNameOnly) {
			for (QueryAttribute qa : queryAttributes) {
				if (Config.getConfig().compareAttributeNames(qa.getAttributeName(), 
						                                     queryAttribute.getAttributeName())) {
					result = true;
					break;
				}
			}
		} else {
			// Compare schema, container, and name
			for (QueryAttribute qa : queryAttributes) {
				if ((Config.getConfig().compareSchemaNames(qa.getSchemaName(), queryAttribute.getSchemaName())) &&
					(Config.getConfig().compareTableNames(qa.getContainerName(), queryAttribute.getContainerName())) &&
					(Config.getConfig().compareAttributeNames(qa.getAttributeName(), queryAttribute.getAttributeName()))) {
					result = true;
				}
			}
		}
		return result;
	}
	/***
	 * Find a query attribute in the collection
	 * @param queryAttribute A reference to the actual QueryAttribute object in the collection. Be careful!
	 * @return
	 */
	private QueryAttribute findQueryAttribute(QueryAttribute queryAttribute) {
		QueryAttribute queryAttributeFound = null;
		for (QueryAttribute qa : queryAttributes) {
			if (qa.equals(queryAttribute)) {queryAttributeFound = qa; break;}
		}
		return queryAttributeFound;
	}
	
	/***
	 * Get a copy of the QueryNodeAnnotation for a QueryAttribute in this collection
	 * @param queryAttribute The QueryAttribute object to search for
	 * @return A copy of the QueryNodeAnnotation or null if QueryAttribute is not found in the collection.
	 */
	public GraphNodeAnnotation getQueryNodeAnnotation(QueryAttribute queryAttribute) {
		GraphNodeAnnotation graphNodeAnnotation = null;
		graphNodeAnnotation = new GraphNodeAnnotation(findQueryAttribute(queryAttribute).getGraphNodeAnnotation());
		return graphNodeAnnotation;
	}
	/***
	 * Redefine the GraphNodeAnnotation for a QueryAttribute object
	 * @param queryAttribute The QueryAttribute object to operate on
	 * @param graphNodeAnnotation The new GraphNodeAnnotation
	 * @return True if the QueryAttribute was found, false otherwise
	 */
	public boolean setGraphNodeAnnotation(QueryAttribute queryAttribute, GraphNodeAnnotation graphNodeAnnotation) {
		Boolean result = false;
		QueryAttribute queryAttributeFound = null;
		for (QueryAttribute qa : queryAttributes) {
			if (qa.equals(queryAttribute)) {queryAttributeFound = qa; break;}
		}
		if (queryAttributeFound != null) {
			queryAttributeFound.setGraphNodeAnnotation(new GraphNodeAnnotation(graphNodeAnnotation));
			result = true;
		}
		return result;
	}
	/***
	 * Search for a query attribute by schema, table, and attribute name
	 * @return True if found
	 */
	public Boolean containsBySchemaTableAttribute(String schemaName, String tableName, String attributeName) {
		// TODO This seems to be the same as contains() in this class. 
		Boolean queryFound = false;
		for (QueryAttribute qa : queryAttributes) {
			if (Config.getConfig().compareSchemaNames(qa.getSchemaName(), schemaName) &&
				Config.getConfig().compareTableNames(qa.getContainerName(), tableName) &&	
				Config.getConfig().compareAttributeNames(qa.getAttributeName(), attributeName)) {
				queryFound = true;
				break;
			}
		}
		return queryFound;
	}
	/***
	 * Search for a query attribute by schema, table, and attribute name
	 * @param queryAttribute The query attribute to search for
	 * @return True if found
	 */
	public Boolean findAttribute(QueryAttribute queryAttribute) {
		// TODO This seems to be the same as contains() in this class. 
		Boolean queryFound = false;
		for (QueryAttribute qa : queryAttributes) {
			if (Config.getConfig().compareSchemaNames(qa.getSchemaName(),       queryAttribute.getSchemaName()) &&
				Config.getConfig().compareTableNames(qa.getContainerName(),         queryAttribute.getContainerName()) &&	
				Config.getConfig().compareAttributeNames(qa.getAttributeName(), queryAttribute.getAttributeName())) {
				queryFound = true;
				break;
			}
		}
		return queryFound;
	}
	/***
	 * Search for a query attribute by schema, table, and attribute name
	 * View name matches table name and (attribute name , schema name match)
	 * @param queryAttribute The query attribute to search for
	 * @return The attribute if found, null otherwise
	 */
	public QueryAttribute findImpactedAttributeAndReturnIt(QueryAttribute queryAttribute) {
		// This seems to be about the same as contains() in this class. 
		QueryAttribute queryAttributeFound = null;
		for (QueryAttribute qa : queryAttributes) {
			if (Config.getConfig().compareSchemaNames(qa.getContainingSchemaName(), queryAttribute.getSchemaName()) &&
				Config.getConfig().compareTableNames(qa.getContainingViewName(),    queryAttribute.getContainerName()) &&	
				Config.getConfig().compareAttributeNames(qa.getAttributeName(),     queryAttribute.getAttributeName())) {
				queryAttributeFound = qa;
				break;
			}
		}
		return queryAttributeFound;
	}
	/***
	 * Search for a query attribute by schema, table, and attribute name
	 * @param queryAttribute The query attribute to search for
	 * @return The attribute if found, null otherwise
	 */
	public QueryAttribute findAttributeAndReturnIt(QueryAttribute queryAttribute) {
		// This seems to be about the same as contains() in this class. 
		QueryAttribute queryAttributeFound = null;
		for (QueryAttribute qa : queryAttributes) {
			if (Config.getConfig().compareSchemaNames(qa.getSchemaName(),       queryAttribute.getSchemaName()) &&
				Config.getConfig().compareTableNames(qa.getContainerName(),         queryAttribute.getContainerName()) &&	
				Config.getConfig().compareAttributeNames(qa.getAttributeName(), queryAttribute.getAttributeName())) {
				queryAttributeFound = qa;
				break;
			}
		}
		return queryAttributeFound;
	}
	/***
	 * Search for a query attribute by attribute name **only**
	 * @param queryAttribute The query attribute to search for
	 * @return a reference to the QueryAttribute object if found, null otherwise
	 */
	public QueryAttribute findAttributeByNameOnly(Attributable queryAttribute) {
		// TODO This seems to be the same as contains() in this class. 
		QueryAttribute queryAttributeFound = null;
		for (QueryAttribute qa : queryAttributes) {
			if (Config.getConfig().compareAttributeNames(qa.getAttributeName(), queryAttribute.getAttributeName())) {
				queryAttributeFound = qa;
				break;
			}
		}
		return queryAttributeFound;
	}
	/**
	 * Find a QueryAttribute by AttributeName or AliasName
	 * @param fcn The Full Column Name to match, taken from the query when it was parsed. This should *always* be enough to uniquely identify the attribute
	 * @return The QueryAttribute that is found, else null if not found
	 */
	public QueryAttribute findAttribute(FullColumnName fcn) {
		QueryAttribute result = null;
		Boolean matchFound = false, needTableMatch = true, needSchemaMatch = true;
		try {
			if (Utils.removeBackQuotes(fcn.getSchemaName()).trim().length() == 0) { needSchemaMatch = false;}
			if (Utils.removeBackQuotes(fcn.getTableName()).trim().length() == 0) { needTableMatch = false;}
			
				// If there's no table name then the attribute name must be sufficient to uniquely identify the attribute
				for (QueryAttribute queryAttribute : queryAttributes) {
					matchFound = false;
					if ((!needSchemaMatch || Config.getConfig().compareSchemaNames(fcn.getSchemaName(), queryAttribute.getSchemaName())) &&
						(!needTableMatch  || Config.getConfig().compareTableNames(fcn.getTableName(), queryAttribute.getContainerName())) &&	
						(Config.getConfig().compareAttributeNames(queryAttribute.getAttributeName(), fcn.getAttributeName()) == true)) {
						result = queryAttribute;
						matchFound = true;
						break;
					}
					if (matchFound == false) {
						if (queryAttribute.hasAliasName(fcn.getAttributeName())) {
							result = queryAttribute;
							matchFound = true;
							break;
						}
					}
					if (matchFound == true) {break;}
				}
		} catch (Exception ex) {
			Log.logError("QueryAttributes.findAttribute(" + fcn.toString() + "): " + ex.getLocalizedMessage()); 
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
