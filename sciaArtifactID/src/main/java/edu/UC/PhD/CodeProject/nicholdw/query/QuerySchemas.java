package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.Config;

/**
 * collection of schemas in a QueryDefinition object
 * @author nicomp
 */
public class QuerySchemas implements Iterable<QuerySchema> {

	private ArrayList<QuerySchema> querySchemas;

	public QuerySchemas() {querySchemas = new ArrayList<QuerySchema>();}

	@Override
	public Iterator<QuerySchema> iterator() {
		Iterator<QuerySchema> iprof = querySchemas.iterator();
        return iprof;
	}

	public ArrayList<QuerySchema> getQuerySchemas() {return querySchemas;}

	public void addSchema(QuerySchema querySchema) {
		if (contains(querySchema.getSchemaName()) == null) {
			querySchemas.add(querySchema);
		}
	}
	public int size() {return querySchemas.size();}

	QuerySchema contains(String schemaName) {
		QuerySchema result = null;
		for (QuerySchema querySchema : querySchemas) {
			if (Config.getConfig().compareSchemaNames(querySchema.getSchemaName(), schemaName)) {
				result = querySchema;
				break;
			}
		}
		return result;
	}
}
