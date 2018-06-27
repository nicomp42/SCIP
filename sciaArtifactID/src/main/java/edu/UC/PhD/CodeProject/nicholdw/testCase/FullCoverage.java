/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.testCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import lib.SQLUtils;

/**
 * Test all the evolution operators, all the input methods
 * @author nicomp
 *
 */
public class FullCoverage extends TestCase {
	public FullCoverage(String title, ConnectionInformation connectionInformation) throws TestCaseException {
		super(title, "FullCoverage");
		setConnectionInformation(connectionInformation);
	}
	private ConnectionInformation connectionInformation;
	/**
	 * Execute the case study from end-to-end
	 */
	@Override
	public boolean run() {
		Log.logProgress("FullCoverage.run(): starting...");
		try {
			createOperationalSchemas();
		} catch (Exception ex) {
			Log.logError("FullCoverage.run(): " + ex.getLocalizedMessage());
		}finally {
			Log.logProgress("FullCoverage.run(): done.");
		}
		return false;
	}
	/**
	 * Create the operational schemas for the test case
	 * @return True on success, false otherwise
	 */
	@Override
	public boolean createOperationalSchemas() {
		boolean status = true;
		BufferedReader reader = null;
		Log.logProgress("FullCoverage.createOperationalSchemas(): Starting...");
		try {
			// Execute all the scripts in the OperationalSchemaCreationScripts subdirectory. The resources folder is assumed. Do not put it in the path
			String path = "/" + TestCase.root + "/" + getFilePath() + "/" + "OperationalSchemaCreationScripts/" + "steps.txt";
   	    	Log.logProgress("FullCoverage.createOperationalSchemas(): reading steps from `" + path + "`" );
		    InputStream res = FullCoverage.class.getResourceAsStream(path);
	   	    reader = new BufferedReader(new InputStreamReader(res));
	   	    String line = null; 
	   	    while ((line = reader.readLine()) != null) {
	   	    	if (!line.trim().startsWith("//")) {
					path = "/" + TestCase.root + "/" + getFilePath() + "/" + "OperationalSchemaCreationScripts/" + line;
		   	    	Log.logProgress("FullCoverage.createOperationalSchemas(): processing file `" + path + "`" );
	
				    InputStream SQLInputStream = FullCoverage.class.getResourceAsStream(path);
			   	    //BufferedReader SQLreader = new BufferedReader(new InputStreamReader(SQLInputStream));
			   	    String sql = Utils.convertStreamToString(SQLInputStream);
			   	    // Submit the SQL to the database engine
		   	    	Log.logProgress("FullCoverage.createOperationalSchemas(): executing SQL " + sql);
			   	    SQLUtils.executeActionQuery(connectionInformation.getHostName(), "", connectionInformation.getLoginName(), connectionInformation.getPassword(), sql);
			   	    SQLInputStream.close();
	   	    	}
	   	    }
		} catch (Exception ex) {
			Log.logError("FullCoverage.createOperationalSchemas(): " + ex.getLocalizedMessage());
		} finally {
	   	    try {reader.close();} catch (IOException e) {}		
		}
		return status;
	}
	public ConnectionInformation getConnectionInformation() {
		return connectionInformation;
	}
	public void setConnectionInformation(ConnectionInformation connectionInformation) {
		this.connectionInformation = new  ConnectionInformation(connectionInformation);
	}
	
	private void processScripts(String path) {
		File folder = new File("/Users/you/folder/");
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        System.out.println(file.getName());
		    }
		}
	}
}
