/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;
import java.util.Iterator;

/***
 * List of indices in a table
 * @author nicomp
 *
 */
public class Indexes implements Iterable<Index> {
	
	private ArrayList<Index> indexes;

	/**
	 * Constructor
	 */
	public Indexes() {
		indexes = new ArrayList<Index>();
	}
	
	/**
	 * Retrieve the list of indexes in this Index List
	 * @return A reference to the index list in the current object. 
	 */
	public ArrayList<Index> getIndexs ()
	{
		return indexes;
	}
	
	public void addIndex(Index index) {
		indexes.add(index);
	}

	@Override
	public Iterator<Index> iterator() {
		Iterator<Index> iprof = indexes.iterator();
        return iprof; 
    }	
}
