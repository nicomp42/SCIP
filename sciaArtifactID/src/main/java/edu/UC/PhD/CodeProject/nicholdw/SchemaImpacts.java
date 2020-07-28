package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A collection of schemaImpact objects
 * @author nicomp
 *
 */
//public class SchemaImpacts implements Iterable<SchemaImpact> {
public class SchemaImpacts {
	private ConcurrentHashMap<SchemaImpact, SchemaImpact> schemaImpacts;

	public SchemaImpacts() {
		schemaImpacts = new ConcurrentHashMap<SchemaImpact, SchemaImpact>();
	}
	public ConcurrentHashMap<SchemaImpact, SchemaImpact> getSchemaImpacts() {
		return schemaImpacts;
	}

	public void setSchemaImpacts(ConcurrentHashMap<SchemaImpact, SchemaImpact> schemaImpacts) {
		this.schemaImpacts = schemaImpacts;
	}
//	@Override
//	public Iterator<SchemaImpact> iterator() {
//		Iterator<SchemaImpact> iprof = schemaImpacts.iterator();
//       return iprof;
//  }
	public void addSchemaImpact(SchemaImpact schemaImpact) {
		schemaImpacts.put(schemaImpact, schemaImpact);
	}
}
