/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.lucene.queryparser.surround.parser.QueryParser;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
//import gudusoft.gsqlparser.TGSqlParser;

/***
 * List of QueryDefinition objects
 * @author nicomp
 *
 */
public class QueryDefinitions implements Iterable<QueryDefinition>,  java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3623030586811588938L;
	private ArrayList<QueryDefinition> QueryDefinitions;

	/**
	 * Constructor
	 */
	public QueryDefinitions() {
		QueryDefinitions = new ArrayList<QueryDefinition>();
	}

	/**
	 * Find a Query Definition by the name of the query and the name of the schema
	 * @param schemaName
	 * @param queryName
	 * @return The Query Definition or null if not found
	 */
	public QueryDefinition findQueryDefinitionBySchemaAndTableName(String schemaName, String queryName) {
		QueryDefinition qd = null;
		for (QueryDefinition queryDefinition : QueryDefinitions) {
			if (Config.getConfig().compareSchemaNames(queryDefinition.getSchemaName(), schemaName) && Config.getConfig().compareQueryNames(queryDefinition.getQueryName(), queryName)) {
				qd = queryDefinition;
				break;
			}
		}
		return qd;
	}

	/**
	 * Retrieve the list of QueryDefinitions in this Index List
	 * @return A reference to the QueryDefinition list in the current object.
	 */
	public ArrayList<QueryDefinition> getQueryDefinitions ()
	{
		return QueryDefinitions;
	}

	public void addQueryDefinition(QueryDefinition queryDefinition) {
		Log.logProgress("QueryDefinitions.addQueryDefinition(): adding " + queryDefinition.getSchemaName() + "." + queryDefinition.getQueryName());
		if (this.findQueryDefinitionBySchemaAndTableName(queryDefinition.getSchemaName(), queryDefinition.getQueryName()) == null) {
			QueryDefinitions.add(queryDefinition);
		} else {
			Log.logProgress("QueryDefinitions.addQueryDefinition(): duplicate query definition so it will not be added.");
		}
	}

	@Override
	public Iterator<QueryDefinition> iterator() {
		Iterator<QueryDefinition> iprof = QueryDefinitions.iterator();
        return iprof;
    }
}