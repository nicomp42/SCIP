package edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject;

import edu.UC.PhD.CodeProject.nicholdw.Config;

/**
 * Data transformations from the Intermediate data set to the Data Warehouse
 * @author nicomp
 *
 */
public class IdsDwh extends SchemaChangeImpactProjectComponent implements java.io.Serializable  {
	private static final long serialVersionUID = Config.getConfig().getSerialVersionUID();
	private final static String componentName = "IdsDwh";

	public IdsDwh() {
		super(componentName);
	}

}
