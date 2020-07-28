/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A collection of schemaImpact references.
 * Implemented with a concurrent-ready data type so we can add references while iterating over it
 * @author nicomp
 *
 */
public class SchemaImpacts {
	// We're using a concurrent-ready data type because we are adding SchemaImpact objects to the collection
	//  as we are iterating over it.
	private ConcurrentHashMap<SchemaImpact, SchemaImpact> schemaImpacts;

	/**
	 * Constructor
	 */
	public SchemaImpacts() {
		schemaImpacts = new ConcurrentHashMap<SchemaImpact, SchemaImpact>();
	}
	/**
	 * @return The collection of SchemaImpact references
	 */
	public ConcurrentHashMap<SchemaImpact, SchemaImpact> getSchemaImpacts() {
		return schemaImpacts;
	}
	/**
	 * Define the set of SchemaImpact references
	 * @param schemaImpacts The new set of SchemaImpact references
	 */
	public void setSchemaImpacts(ConcurrentHashMap<SchemaImpact, SchemaImpact> schemaImpacts) {
		this.schemaImpacts = schemaImpacts;
	}
	/**
	 * Add a SchemaImpact object reference to the collection
	 * @param schemaImpact The reference to add. No copy is performed.
	 */
	public void addSchemaImpact(SchemaImpact schemaImpact) {
		schemaImpacts.put(schemaImpact, schemaImpact);
	}
}
