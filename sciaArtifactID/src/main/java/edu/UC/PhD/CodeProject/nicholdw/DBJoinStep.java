package edu.UC.PhD.CodeProject.nicholdw;
public class DBJoinStep {
	
	private String transName;
	private String stepName;
	private String dbName;
	private String sql;
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return "DBJoinStep [transName=" + transName + ", stepName=" + stepName
				+ ", dbName=" + dbName + ", sql=" + sql + "]";
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

	public DBJoinStep(String transName, String stepName, String dbName,
			String sql) {
		super();
		this.transName = transName;
		this.stepName = stepName;
		this.dbName = dbName;
		this.sql = sql;
	}
	
}
