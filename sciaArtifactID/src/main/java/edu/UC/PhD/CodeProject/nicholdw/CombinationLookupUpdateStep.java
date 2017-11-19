package edu.UC.PhD.CodeProject.nicholdw;
import java.util.ArrayList;
import java.util.List;

public class CombinationLookupUpdateStep {

	private String transName;
	private String stepName;
	private String dbName;
	private String tableName;
	private List<String> attributes = new ArrayList<String>();
	private String stepType;

	@Override
	public String toString() {
		return "CombinationLookupUpdateStep [transName=" + transName
				+ ", stepName=" + stepName + ", dbName=" + dbName
				+ ", tableName=" + tableName + ", attributes=" + attributes
				+ "]";
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public CombinationLookupUpdateStep(String transName, String stepName,
			String stepType, String dbName, String tableName,
			List<String> attributes) {
		super();
		this.transName = transName;
		this.stepName = stepName;
		this.stepType = stepType;
		this.dbName = dbName;
		this.tableName = tableName;
		this.attributes = attributes;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

}
