/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

public class QueryTerminalSymbols  implements Iterable<QueryTerminalSymbol>,  java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4389577861474836324L;
	private ArrayList<QueryTerminalSymbol> queryTerminalSymbols;

	/**
	 * Constructor
	 */
	public QueryTerminalSymbols() {
		queryTerminalSymbols = new ArrayList<QueryTerminalSymbol>();
	}

	/**
	 * Retrieve the list of QueryVariables in this Index List
	 * @return A reference to the QueryVariable list in the current object.
	 */
	public ArrayList<QueryTerminalSymbol> getQueryTerminalSymbols ()
	{
		return queryTerminalSymbols;
	}

	public void addQueryTerminalSymbol(QueryTerminalSymbol queryTerminalSymbol) {
		queryTerminalSymbols.add(queryTerminalSymbol);
	}

	@Override
	public Iterator<QueryTerminalSymbol> iterator() {
		Iterator<QueryTerminalSymbol> iprof = queryTerminalSymbols.iterator();
        return iprof;
    }
	
}
