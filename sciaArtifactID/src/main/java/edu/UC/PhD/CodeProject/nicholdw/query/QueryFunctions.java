package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

public class QueryFunctions implements Iterable<QueryFunction> {

	private ArrayList<QueryFunction> QueryFunctions;

	/**
	 * Constructor
	 */
	public QueryFunctions() {
		QueryFunctions = new ArrayList<QueryFunction>();
	}

	/**
	 * Find a Query Function Name by the name of the function and the name of the schema
	 * @param schemaName
	 * @param queryFunctionName
	 * @return The Query Definition or null if not found
	 */
	public QueryFunction findQueryFunctionNameBySchema(String schemaName, String functionName) {
		QueryFunction queryFunction = null;
		for (QueryFunction qf : QueryFunctions) {
			if (qf.getSchemaName().equals(schemaName) && qf.getQueryFunctionName().equals(functionName)) {
				queryFunction = qf;
				break;
			}
		}
		return queryFunction;
	}

	/**
	 * Retrieve the list of QueryFunctions in this Index List
	 * @return A reference to the QueryFunction list in the current object.
	 */
	public ArrayList<QueryFunction> getQueryFunctions ()
	{
		return QueryFunctions;
	}

	public void addQueryFunction(QueryFunction QueryFunction) {
		QueryFunctions.add(QueryFunction);
	}

	@Override
	public Iterator<QueryFunction> iterator() {
		Iterator<QueryFunction> iprof = QueryFunctions.iterator();
        return iprof;
    }
}
