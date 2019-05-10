package edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject;

public abstract class SchemaChangeImpactProjectComponent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String ComponentName;

	public SchemaChangeImpactProjectComponent(String componentName) {
		setComponentName(componentName);
	}
	public String getComponentName() {
		return ComponentName;
	}
	public void setComponentName(String componentName) {
		ComponentName = componentName;
	}
}
