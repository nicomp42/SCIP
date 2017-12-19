package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Collection of query variables. See QueryDefinition class
 * @author nicomp
 *
 */
public class QueryVariables implements Iterable<QueryVariable> {

	private ArrayList<QueryVariable> queryVariables;

	/**
	 * Constructor
	 */
	public QueryVariables() {
		queryVariables = new ArrayList<QueryVariable>();
	}

	/**
	 * Retrieve the list of QueryVariables in this Index List
	 * @return A reference to the QueryVariable list in the current object.
	 */
	public ArrayList<QueryVariable> getQueryVariables ()
	{
		return queryVariables;
	}

	public void addQueryVariable(QueryVariable queryVariable) {
		queryVariables.add(queryVariable);
	}

	@Override
	public Iterator<QueryVariable> iterator() {
		Iterator<QueryVariable> iprof = queryVariables.iterator();
        return iprof;
    }
}
