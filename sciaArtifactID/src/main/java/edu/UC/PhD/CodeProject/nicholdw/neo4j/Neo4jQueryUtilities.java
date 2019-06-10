/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.neo4j;

public class Neo4jQueryUtilities {
	private String filterByNodeTypeQuery = "match (t) where t:# return t";
	private String filterByTablesAndTheirAttributes = "MATCH (t:Table)-[*]-(a:Attribute) RETURN t,a";
	
	public String filterByNodeType(String nodeType) {
		return filterByNodeTypeQuery.replace("#",  nodeType);
	}
	
	
}
