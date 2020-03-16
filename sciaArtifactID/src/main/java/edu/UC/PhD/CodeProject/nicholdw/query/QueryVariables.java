/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Collection of query variables. See QueryDefinition class
 * @author nicomp
 *
 */
public class QueryVariables implements Iterable<QueryVariable>,  java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1799142033940943287L;
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
