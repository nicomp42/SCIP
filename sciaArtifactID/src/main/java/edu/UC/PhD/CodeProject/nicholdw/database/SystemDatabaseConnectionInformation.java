/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.database;

import java.io.Serializable;

public class SystemDatabaseConnectionInformation extends ConnectionInformation  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemDatabaseConnectionInformation(String hostName, String loginName, String password) {
		super(hostName, loginName, password);
	}
	

}
