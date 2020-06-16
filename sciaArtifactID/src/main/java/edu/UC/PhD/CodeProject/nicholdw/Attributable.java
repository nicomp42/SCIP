package edu.UC.PhD.CodeProject.nicholdw;

/**
 * Things a table attribute and a query attribute have in common
 * @author nicomp
 *
 */
public interface Attributable {
	public String getContainerName();
	public String getSchemaName();
	public String getAttributeName();
	/**
	 * Container is a table or a query
	 * @param containerName
	 */
	public void setContainerName(String containerName);
	public void setSchemaName(String schemaName);
	public void setAttributeName(String AttributeName);
	
	public void setGraphNodeAnnotation(GraphNodeAnnotation graphNodeAnnotation);
	public GraphNodeAnnotation getGraphNodeAnnotation();
	
	public Boolean getAffectedByActionQuery();
	public void setAffectedByActionQuery(Boolean affectedByActionQuery);
	
}
