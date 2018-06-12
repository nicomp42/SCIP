package edu.UC.PhD.CodeProject.nicholdw.database;

import java.io.Serializable;

/**
 * Configuration information for a database connection
 * @author nicomp
 *
 */
public abstract class ConnectionInformation  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loginName;
	private String password;
	private String hostName;
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
	/**
	 * Create a ConnectionInformation object
	 * @param hostName Host Name of the database
	 * @param loginName Login Name to access the database
	 * @param password Password to access the database
	 */
	public ConnectionInformation(String hostName, String loginName, String password) {
		setLoginName(loginName);
		setPassword(password);
		setHostName(hostName);
	}
	
}
