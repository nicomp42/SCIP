package edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject;

import java.io.Serializable;

public class DatabaseConnection implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String hostName;
	private String loginName;
	private String password;
	private String schema;
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}


}
