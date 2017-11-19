package edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject;

import edu.UC.PhD.CodeProject.nicholdw.Config;


/**
 * Queries written against the Data Warehouse
 * @author nicomp
 *
 */
public class DwhQueries extends SchemaChangeImpactProjectComponent implements java.io.Serializable {

	private static final long serialVersionUID = Config.getConfig().getSerialVersionUID();
	private final static String componentName = "DwhQueries";
	private String hostName = "localhost";
	private String loginName = "root";
	private String password = "Danger42";
	private String dwhSchemaName;					// Should match what is in the mySQL database

	public DwhQueries() {
		super(componentName);
	}

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
	public String getDwhSchemaName() {
		return dwhSchemaName;
	}
	public void setDwhSchemaName(String dwhSchemaName) {
		this.dwhSchemaName = dwhSchemaName;
	}



}
