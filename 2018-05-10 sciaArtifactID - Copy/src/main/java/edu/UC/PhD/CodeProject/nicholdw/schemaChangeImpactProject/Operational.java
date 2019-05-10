package edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject;

import edu.UC.PhD.CodeProject.nicholdw.Config;
/**
 * Queries and connection information related to the Operational data set
 * @author nicomp
 *
 */
public class Operational extends SchemaChangeImpactProjectComponent implements java.io.Serializable {
	private static final long serialVersionUID = Config.getConfig().getSerialVersionUID();
	private final static String componentName = "Operational";
	private String hostName = "localhost";
	private String loginName = "root";
	private String password = "Danger42";
	private String operationalSchemaName;					// Should match what is in the  mySQL database

	public Operational() {
		super(componentName);
		setHostName("");
		setLoginName("");
		setPassword("");
		setOperationalSchemaName("");
	}
	public Operational(String hostName, String loginName, String password) {
		super(componentName);
		setHostName(hostName);
		setLoginName(loginName);
		setPassword(password);
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

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getOperationalSchemaName() {
		return operationalSchemaName;
	}

	public void setOperationalSchemaName(String operationalSchemaName) {
		this.operationalSchemaName = operationalSchemaName;
	}



}
