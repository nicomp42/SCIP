package edu.UC.PhD.CodeProject.nicholdw.mapping;

/***
 * Base class for ETL Mapping classes 
 * @author nicomp
 *
 */
public abstract class MappingBase {
	private String uniqueName;
	private String transformationName;
	private String stepName;
	private String stepType;
	private String databaseName;
	private String tableName;
	private String attributeName;

	abstract MappingBase clone(MappingBase mappingBase);

	public MappingBase(String uniqueName, String transformationName, String stepName, String stepType, String databaseName, String tableName, String attributeName) {
		setUniqueName(uniqueName);
		setTransformationName(transformationName);
		setStepName(stepName);
		setStepType(stepType);
		setDatabaseName(databaseName);
		setTableName(tableName);
		setAttributeName(attributeName);
	}
	public MappingBase(String uniqueName) {
		setUniqueName(uniqueName);
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getTransformationName() {
		return transformationName;
	}

	public void setTransformationName(String transformationName) {
		this.transformationName = transformationName;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
}
