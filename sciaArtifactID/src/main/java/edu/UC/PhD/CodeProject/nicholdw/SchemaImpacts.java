package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A collection of schemaImpact objects
 * @author nicomp
 *
 */
public class SchemaImpacts implements Iterable<SchemaImpact> {
	private ArrayList<SchemaImpact> schemaImpacts;

	public SchemaImpacts() {
		schemaImpacts = new ArrayList<SchemaImpact>();
	}
	public ArrayList<SchemaImpact> getSchemaImpacts() {
		return schemaImpacts;
	}

	public void setSchemaImpacts(ArrayList<SchemaImpact> schemaImpacts) {
		this.schemaImpacts = schemaImpacts;
	}
	@Override
	public Iterator<SchemaImpact> iterator() {
		Iterator<SchemaImpact> iprof = schemaImpacts.iterator();
        return iprof;
    }
	public void addSchemaImpact(SchemaImpact schemaImpact) {
		schemaImpacts.add(schemaImpact);
	}
}
