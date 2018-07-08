package edu.UC.PhD.CodeProject.nicholdw.mapping;

import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.query.CompoundAlias;

/***
 * ETL Mappings
 * @author nicomp
 *
 */
public class Mappings implements Iterable<Mapping> {
	private ArrayList<Mapping> mappings;
	
	public Mappings() {
		mappings = new ArrayList<Mapping>();
	}
	
	public void clear() {mappings.clear();}
	
	@Override
	public Iterator<Mapping> iterator() {
		Iterator<Mapping> myIterator = mappings.iterator();
        return myIterator;
    }
	/**
	 * Retrieve the list of Mappings in this Mapping List
	 * @return A reference to the Mapping list in the current object.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Mapping> getMappings (){return (ArrayList<Mapping>) mappings.clone();}
	public Mapping getMapping(int i) {return mappings.get(i);}	
	
	/***
	 * Add a mapping to the collection
	 * @param mapping
	 */
	public void add(Mapping mapping) {
		mappings.add(new Mapping(mapping));
	}
	
}
