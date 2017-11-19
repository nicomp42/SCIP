package edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject;

import edu.UC.PhD.CodeProject.nicholdw.Config;

/**
* Data transformations from the Operational Data set to the Intermediate data set.
*/
public class OpsIds extends SchemaChangeImpactProjectComponent implements java.io.Serializable {
	private static final long serialVersionUID = Config.getConfig().getSerialVersionUID();
	private final static String componentName = "OpsIdS";

	public OpsIds() {
		super(componentName);
	}

}
