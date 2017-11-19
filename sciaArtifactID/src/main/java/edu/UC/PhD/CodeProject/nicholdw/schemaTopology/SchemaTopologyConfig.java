package edu.UC.PhD.CodeProject.nicholdw.schemaTopology;

public class SchemaTopologyConfig {
	private boolean includeSchemaInGraph;	// if false, don't include th schema node or the relationships to that node. It does clutter the graph.
	private boolean useFriendlyNameAsDisplayName;	// If true, use the local name of the table/query rathe than a fully qualified name that includes the schema name, etc.

	/**
	 * If false, don't include the schema node or the relationships to that node. It does clutter the graph.
	 * @return the value of include schema in graph
	 */
	public boolean getIncludeSchemaInGraph() {
		return includeSchemaInGraph;
	}

	/**
	 * If false, don't include th schema node or the relationships to that node. It does clutter the graph.
	 * @param includeSchemaInGraph True or false
	 */
	public void setIncludeSchemaInGraph(boolean includeSchemaInGraph) {
		this.includeSchemaInGraph = includeSchemaInGraph;
	}

	/**
	 * If true, use the local name of the table/query/attribute rather than a fully qualified name that includes the schema name, etc.
	 * @return
	 */
	public boolean getUseLocalNameAsDisplayName() {
		return useFriendlyNameAsDisplayName;
	}
	/**
	 * If true, use the local name of the table/query/attribute rather than a fully qualified name that includes the schema name, etc.
	 * @param useFriendlyNameAsDisplayName
	 */
	public void setUseFriendlyNameAsDisplayName(boolean useFriendlyNameAsDisplayName) {
		this.useFriendlyNameAsDisplayName = useFriendlyNameAsDisplayName;
	}
}
