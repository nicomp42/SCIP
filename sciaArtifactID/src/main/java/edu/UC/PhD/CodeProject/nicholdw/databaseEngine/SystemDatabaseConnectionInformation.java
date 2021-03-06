/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.databaseEngine;

import java.io.Serializable;

public class SystemDatabaseConnectionInformation extends ConnectionInformation  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemDatabaseConnectionInformation(String hostName, String loginName, String password, String schemaName) {
		super("System Database", hostName, loginName, password, schemaName);
	}
	

}
