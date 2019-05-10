/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * Changed some default file paths to SCIP from temp.
 *
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.browser.Browser;
import edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformations;
import edu.UC.PhD.CodeProject.nicholdw.database.SystemDatabaseConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import gui.DebugController;
import lib.SQLUtils;

/**
 * Configuration class. It's set up as a Singleton in a very cool way.
 * Use getConfig() to access the stuff in this class.
 * @author nicomp
 */
public class Config implements Serializable {

/*	static void main(String args[] ) {
		System.out.println("Config.main()...");
		Config config = new Config();
		save(config);
	} */
	private  final long serialVersionUID = 1L;
	private static Config config;
    /**
     * Returns a reference to the singleton object
     * @returns The singleton object
     */
    public static synchronized Config getConfig() {
      if (config == null) {
        	config = new Config(); // If it's the first call, instantiate the object
        } 
        return config;
    }
    /**
     * Call this after loading the serialized Config object from a file
     * @param config
     */
    public synchronized static void setConfig(Config config) {
    	Config.config = config;
    }
	/**
	 * A private constructor will prevent the class from being instantiated. It's a Singleton. Not sure if this works.
	 * Defaults are used here because we don't know any better at this point in the execution of the program
	 */
	private Config() {
	}
	private final String version = "0.05";
	private final int mySQLDefaultPort = 3306;
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final Boolean useCaseSensitiveAttributeNameComparison = false;
	private final Boolean useCaseSensitiveAliasNameComparison = false;
	private final Boolean useCaseSensitiveTableNameComparison = false;
	private final Boolean useCaseSensitiveSchemaNameComparison = false;
	private final Boolean useCaseSensitiveQueryNameComparison = false;
	private final String applicationTitle = "SEQ-AM: Schema, ETL, Queries,Artifact Management";
	private final String[] ETLLayers = {"ids-dwh", "op-ids"};		// ids =  Intermediate Data Store
	private final String neo4jFilesPath_Relative = "neo4j";
	private final String attributeNamePrefix = "Attribute";
	private final String attributeID = "BufferID";
	private final String configFilename = "config" + ".ser";
	private final String CSVFileExtension = ".csv";
	private final String neo4jFilePrefix = "Neo4j_";	// Used when creating file names for CSV in the import folder of a Neo4j DB instance and used when creating queries that reference those files
	private final String SCIPFileExtension  = ".ser";	//  A SCIP file is a "Schema Change Impact Project" file. Refer to the SchemaChangeImpactProject class
	private final String attributePartsDelimiter = ".";	// schema.table.attribute for example
	private String Neo4jTableToAttributeRelationName = "contains";
	private String Neo4jQueryToTableRelationName = "contains";
	private Boolean useTestData = false;
	private Boolean enableLogging = false;		// If false, turn off all logging;
	private String initialDirectory = "";
	private String neo4jDBDefaultUser = "neo4j";
	private String neo4jDBDefaultPassword = "Danger42";
	private SchemaChangeImpactProject currentSchemaChangeImpactProject = null;		// = new SchemaChangeImpactProject();
    private DebugController debugController = null;
    private String mySQLDefaultLoginName = "root";
    private String mySQLDefaultPassword = "Danger42";
    private String mySQLDefaultDatabaseName = "World";		// ToDo: We probably don't need this
    private String mySQLDefaultHostname = "localhost";
    private boolean debug = true;
	private Boolean supressOutputToConsole = false;
	private String informationSchemaName = "information_schema";
	private String neo4jDefaultImportFilePath = "C:/SCIP/Test/Test/Neo4jTest/import/";
	private String neo4jSuffix = "Neo4j_";
	private String grassStyleSheetURL = "https://raw.githubusercontent.com/nicomp42/scipGrass/master/grass.css";
	private ArrayList<Browser> browsers;
	private SystemDatabaseConnectionInformation systemDatabaseConnectionInformation;
	private String defaultTransactionLogFilePath = "C:\\ProgramData\\MySQL\\MySQL Server 5.7\\Data";

	public String getDefaultTransactionLogFilePath() {return defaultTransactionLogFilePath;}
	public void setDefaultTransactionLogFilePath(String defaultTransactionLogFilePath) {this.defaultTransactionLogFilePath = defaultTransactionLogFilePath;}	
	public String getMySQLDefaultHostname() {return mySQLDefaultHostname;}
	public String setMySQLDefaultHostname(String mySQLDefaultHostname) {this.mySQLDefaultHostname = mySQLDefaultHostname; return mySQLDefaultHostname;}
	public String getMySQLDefaultLoginName() {return mySQLDefaultLoginName;}
	public String setMySQLDefaultLoginName(String mySQLDefaultLoginName) {this.mySQLDefaultLoginName = mySQLDefaultLoginName; return mySQLDefaultLoginName;}
	public String getMySQLDefaultPassword() {return mySQLDefaultPassword;}
	public String setMySQLDefaultPassword(String password) {mySQLDefaultPassword = password; return mySQLDefaultPassword;}
	public String getMySQLDefaultDatabaseName() {return mySQLDefaultDatabaseName;}
	public String setMySQLDefaultDatabaseName(String mySQLDefaultDatabaseName) {this.mySQLDefaultDatabaseName = mySQLDefaultDatabaseName; return mySQLDefaultDatabaseName;}
	public void Debug(String msg) {if (debug) {System.out.print(msg);}}
	public void DebugLine(String msg) {if (debug) {System.out.print(msg + "\n");}}
	public String getInformationSchemaName() {return informationSchemaName;}
	public void setInformationSchemaName(String informationSchemaName) {this.informationSchemaName = informationSchemaName;}
	public String getNeo4jDefaultImportFilePath() {return neo4jDefaultImportFilePath;}
	public void setNeo4jDefaultImportFilePath(String neo4jDefaultImportFilePath) {this.neo4jDefaultImportFilePath = neo4jDefaultImportFilePath;}
	public String getNeo4jFilePath() {return neo4jFilesPath_Relative;}
	public String[] getETLLayers() {return ETLLayers;}
	public String buildWindowBarTitleWithAProjectName() {return getApplicationTitle() + " : " + getCurrentSchemaChangeImpactProject().getProjectName();}
	
	private ConnectionInformations connectionInformations;

	/**
	 * The file extension for SCIP project files
	 * @return The file extension, including the '.'
	 */
	public String getSCIPFileExtension() {
		return SCIPFileExtension;
	}

	/**
	 * The folder in which all the Graph DBs are stored. Each DB has its' own folder in here
	 * @return The path. It has a slash at the end.
	 */
	public  String getNeo4jFilesPath_Absolute() {
		//	org.neo4j.jmx.impl.ConfigurationBean
		//String path = System.getenv("JAVA_HOME");
		String path = "C:/Users/nicomp/Google Drive/PhD (1)/MyCode/SchemaChangeImpactAnalysis/neo4j/";	// TODO generalize this.
		return path;
	}
	// The CSV files must be in the Neo4j environment or Neo4j will refuse to import them. Then we will build subfolders for each schema under that location.
	//private  final String csvFilesPath =  getNeo4jGraphDBFolder();

	/**
	 * Get the Windows configuration setting for HOMEPATH
	 * @return Home directory for the current user
	 */
	public String getUserHomeDirectory() {
		String userHomeDirectory;
    	userHomeDirectory = System.getProperty("user.home");
    	return userHomeDirectory;
	}
	/**
	 * Get the location of the Neo4j configuration file
	 * @return
	 */
	public String getNeo4jConfigurationFilePath() {
		return getUserHomeDirectory() + "\\AppData\\Roaming\\" + "Neo4j Community Edition";
	}
	/**
	 * Get the full path and file name of the Neo4j configuration file
	 * @return
	 */
	public String getNeo4jConfigurationFile() {return this.getNeo4jConfigurationFilePath() + "\\" + this.getNeo4jConfigurationFileName();}
	public String getNeo4jConfigurationFileName() {return "neo4j.conf";}

	/**
	 * Get the reference to the current project
	 * @return The reference to the current project
	 */
	public SchemaChangeImpactProject getCurrentSchemaChangeImpactProject() {
		if (currentSchemaChangeImpactProject == null) {currentSchemaChangeImpactProject = new SchemaChangeImpactProject();}
		return currentSchemaChangeImpactProject;
		}
	/**
	 * Set the reference to the current project
	 * @param currentSchemaChangeImpactProject The reference to the current project
	 */
	public void setCurrentSchemaChangeImpactProject(SchemaChangeImpactProject currentSchemaChangeImpactProject) {this.currentSchemaChangeImpactProject = currentSchemaChangeImpactProject;}
	public String getInitialDirectory() {
		if (initialDirectory == null) {
			setInitialDirectory(getUserHomeDirectory());
		}
		return initialDirectory;
		}
	public void setInitialDirectory(String initialDirectory) {this.initialDirectory = initialDirectory;}
	public String getApplicationTitle() {return applicationTitle;}
	public String getNeo4jDBDefaultUser() {return neo4jDBDefaultUser;}
	public void setNeo4jDBDefaultUser(String neo4jDBDefaultUser) {this.neo4jDBDefaultUser = neo4jDBDefaultUser;}
	public String getNeo4jDBDefaultPassword() {return neo4jDBDefaultPassword;}
	public void setNeo4jDBDefaultPassword(String neo4jDBDefaultPassword) {this.neo4jDBDefaultPassword = neo4jDBDefaultPassword;}
	public DebugController getDebugController() {return debugController;}
	public void setDebugController(DebugController debugController) {this.debugController = debugController;}
	public Boolean getUseTestData() {return useTestData;}
	public void setUseTestData(Boolean useTestData) {this.useTestData = useTestData;}

//	public void setUseCasesSensitiveAttributeNameComparison(Boolean useCaseSensitiveAttributeNameComparison) {this.useCaseSensitiveAttributeNameComparison = useCaseSensitiveAttributeNameComparison;}
//	public void setUseCaseSensitiveAliasNameComparison(Boolean useCaseSensitiveAliasNameComparison) {this.useCaseSensitiveAliasNameComparison = useCaseSensitiveAliasNameComparison;}
//	public void setUseCasesSensitiveTableNameComparison(Boolean useCaseSensitiveTableNameComparison) {this.useCaseSensitiveTableNameComparison = useCaseSensitiveTableNameComparison;}
//	public void setUseCasesSensitiveSchemaNameComparison(Boolean useCaseSensitiveSchemaNameComparison) {this.useCaseSensitiveSchemaNameComparison = useCaseSensitiveSchemaNameComparison;}

	public String getVersion() {return version;}
	public int getMySQLDefaultPort() {return mySQLDefaultPort;}
	public Boolean getUseCasesSensitiveAttributeNameComparison() {return useCaseSensitiveAttributeNameComparison;}
	public Boolean getUseCaseSensitiveAliasNameComparison() {return useCaseSensitiveAliasNameComparison;}
	public Boolean getUseCaseSensitiveTableNameComparison() {return useCaseSensitiveTableNameComparison;}
	public Boolean getUseCaseSensitiveSchemaNameComparison() {return useCaseSensitiveSchemaNameComparison;}
	public String getNeo4jTableToAttributeRelationName() {return Neo4jTableToAttributeRelationName;}
	public void setNeo4jTableToAttributeRelationName(String neo4jTableToAttributeRelationName) {Neo4jTableToAttributeRelationName = neo4jTableToAttributeRelationName;}
	public String getAttributenameprefix() {return attributeNamePrefix;}
	public String getNeo4jQueryToTableRelationName() {return Neo4jQueryToTableRelationName;}
	public void setNeo4jQueryToTableRelationName(String neo4jQueryToTableRelationName) {Neo4jQueryToTableRelationName = neo4jQueryToTableRelationName;}
	public long getSerialVersionUID() {return serialVersionUID;}
	public String getAttributeID() {return attributeID;}
	/**
	 * Save the current configuration settings to a file
	 * @param configObject
	 */
	public static void save(Config configObject, String fileName) {
		Log.logProgress("Config.save()...");
		try {
			 FileOutputStream fileOut = new FileOutputStream(fileName);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(configObject);
	         out.close();
	         fileOut.close();
	 		Log.logProgress("Config.save() done.");
		} catch (Exception ex) {Log.logError("Config.save(): " + fileName + ": "  + ex.getLocalizedMessage());}
	}
	/**
	 * Load Config object from a file
	 * @param fileName where the serialized Config object is
	 */
	public static Boolean loadConfig(String fileName) {
		Boolean result = false;
		File tmpFile = new File(fileName);
		String fileNameWithPath = tmpFile.getAbsolutePath();
		Log.logProgress("Config.load()...");
		try {
	         FileInputStream fileIn = new FileInputStream(fileName);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         setConfig((Config) in.readObject());
	         in.close();
	         fileIn.close();
	         result = true;
	 		Log.logProgress("Config.loadConfig() done.");
		} catch (Exception ex) {
			Log.logError("Config.loadConfig(): " + fileNameWithPath + ": "  + ex.getLocalizedMessage());
			Log.logProgress("Config.loadConfig(): creating new Config object from scratch.");
			setConfig(new Config());
		}
		return result;
	}
	/***
	 * Get the location of the configuration file. It will be nearby to wherever the app is stored in the file system
	 * @return A path, with filename
	 */
	public String getAbsolutePathOfConfigFile() {return new File(configFilename).getAbsolutePath();}
	public String getConfigFilename() {return configFilename;}
	public String getCSVFileExtension() {return CSVFileExtension;}
	public String getNeo4jSuffix() {
		return neo4jSuffix;
	}
	public void setNeo4jSuffix(String neo4jSuffix) {
		this.neo4jSuffix = neo4jSuffix;
	}
	public String getGrassStyleSheetURL() {
		return grassStyleSheetURL;
	}
	public void setGrassStyleSheetURL(String grassStyleSheetURL) {
		this.grassStyleSheetURL = grassStyleSheetURL;
	}
	public ArrayList<Browser> getBrowsers() {
		createBrowserList();
		return browsers;
	}
	/**
	 * Add a browser to the list of open browsers
	 * @param browser The browser to be added
	 * @return the browser that was added, same as the argument
	 */
	public Browser addBrowser(Browser browser) {
		createBrowserList();
		browsers.add(browser);
		return browser;
	}
	private void createBrowserList() {
		if (browsers == null) {
			browsers = new ArrayList<Browser>();
		}
	}
//	public void setBrowsers(ArrayList<Browser> browsers) {
//		this.browsers = browsers;
//	}
	public Boolean getSupressOutputToConsole() {
		return supressOutputToConsole;
	}
	public void setSupressOutputToConsole(Boolean supressOutputToConsole) {
		this.supressOutputToConsole = supressOutputToConsole;
	}
	public String getNeo4jFilePrefix() {return neo4jFilePrefix;}
	
	// Used in the parser/lexxer. See edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.QueryParser class
	public static final String LR_BRACKET = "(";
	public static final String RT_BRACKET = ")";
	public static final String SQL_STATEMENT_DELIMITER = ";";		// between SQL statements

	public boolean compareQueryNames(String queryName1, String queryName2) {
		boolean result = false;
		if (Config.getConfig().getUseCaseSensitiveQueryNameComparison() == true) {
			if (queryName1.trim().equals(queryName2.trim())) {
				result = true;
			}
		} else {
			if (queryName1.trim().toLowerCase().equals(queryName2.trim().toLowerCase())) {
				result = true;
			}
		}
		return result;
	}
	public boolean compareTableNames(String tableName1, String tableName2) {
		boolean result = false;
		if (Config.getConfig().getUseCaseSensitiveTableNameComparison() == true) {
			if (tableName1.trim().equals(tableName2.trim())) {
				result = true;
			}
		} else {
			if (tableName1.trim().toLowerCase().equals(tableName2.trim().toLowerCase())) {
				result = true;
			}
		}
		return result;
	}
	public boolean compareAttributeNames(String attributeName1, String attributeName2) {
		boolean result = false;
		if (Config.getConfig().getUseCasesSensitiveAttributeNameComparison() == true) {
			if (attributeName1.trim().equals(attributeName2.trim())) {
				result = true;
			}
		} else {
			if (attributeName1.trim().toLowerCase().equals(attributeName2.trim().toLowerCase())) {
				result = true;
			}
		}
		return result;
	}
	public boolean compareSchemaNames(String schemaName1, String schemaName2) {
		boolean result = false;
		if (Config.getConfig().getUseCaseSensitiveSchemaNameComparison() == true) {
			if (schemaName1.trim().equals(schemaName2.trim())) {
				result = true;
			}
		} else {
			if (schemaName1.trim().toLowerCase().equals(schemaName2.trim().toLowerCase())) {
				result = true;
			}
		}
		return result;
	}
	public boolean compareAliasNames(String aliasName1, String aliasName2) {
		boolean result = false;
		if (Config.getConfig().getUseCaseSensitiveAliasNameComparison() == true) {
			if (aliasName1.equals(aliasName2)) {
				result = true;
			}
		} else {
			String a1 = aliasName1.toLowerCase().trim();
			String a2 = aliasName2.toLowerCase().trim();
			//System.out.println(a1 + "\n" + a2);
			if (a1.equals(a2)) {
				result = true;
			}
		}
		return result;
	}
	public Boolean getUseCaseSensitiveQueryNameComparison() {
		return useCaseSensitiveQueryNameComparison;
	}
	public ConnectionInformation getSystemDatabaseConnectionInformation() {
		if (systemDatabaseConnectionInformation == null) {
			systemDatabaseConnectionInformation = new SystemDatabaseConnectionInformation("localhost", "root", "Danger42", "seq-am");
		}		
		return systemDatabaseConnectionInformation;
	}
//	public void setSystemDatabaseConnectionInformation(SystemDatabaseConnectionInformation systemDatabaseConnectionInformation) {
//		this.systemDatabaseConnectionInformation = systemDatabaseConnectionInformation;
//	}
	public ConnectionInformations getConnectionInformations() {
		if (connectionInformations == null) {
			setConnectionInformations(ConnectionInformation.readXML());
		}
		return connectionInformations;
	}
	public void setConnectionInformations(ConnectionInformations connectionInformations) {
		this.connectionInformations = connectionInformations;
	}
	/***
	 * Look up a project ID based on a project name in the System database
	 * @param projectName
	 * @return The projectID or zero if no match
	 */
	public int getProjectID(String projectName) {
		int projectID = 0;
		try {
			java.sql.Connection connection = SQLUtils.openJDBCConnection(this.getSystemDatabaseConnectionInformation());
			
			projectID = (int)SQLUtils.myDLookup("ProjectID", "`seq-am`.`tProject`", "Name = " + Utils.QuoteMeDouble(projectName.trim()), "", "", connection);
		} catch (Exception ex) {
			Log.logError("Config.getProjectID(" + projectName + "): " + ex.getLocalizedMessage());
		}
		return projectID;
	}
	public String getAttributePartsDelimiter() {
		return attributePartsDelimiter;
	}
	/**
	 * Format a LocalDateTime object for display
	 * @param localDateTime
	 * @return The formatted string
	 */
	public static String formatLocalDateTime(LocalDateTime localDateTime) {
		try {
			return localDateTime.format(dateTimeFormatter);
		} catch (Exception ex) {
			return "!!!";
		}
	}
	/**
	 * Check to see if logging is enabled
	 * @return True if logging is enabled, false otherwise
	 */
	public Boolean getEnableLogging() {
		return enableLogging;
	}
	/**
	 * Enable/Disable logging
	 * @param enableLogging True = enable logging
	 */
	public void setEnableLogging(Boolean enableLogging) {
		this.enableLogging = enableLogging;
	}
}
// List the fields that should be serialized. In this class, that's all of them that are not marked final.
// This does not work, an error is thrown on the writeObject statement.
/*  private  final ObjectStreamField[] serialPersistentFields = {
		new ObjectStreamField("Neo4jTableToAttributeRelationName", String.class),
		new ObjectStreamField("Neo4jQueryToTableRelationName", String.class),
		new ObjectStreamField("useTestData", Boolean.class),
		new ObjectStreamField("initialDirectory", String.class),
		new ObjectStreamField("neo4jDBDefaultUser", String.class),
		new ObjectStreamField("neo4jDBDefaultPassword", String.class),
		new ObjectStreamField("currentSchemaChangeImpactProject", SchemaChangeImpactProject.class),
		new ObjectStreamField("debugController", DebugController.class),
		new ObjectStreamField("mySQLDefaultLoginName", String.class),
		new ObjectStreamField("mySQLDefaultPassword", String.class),
		new ObjectStreamField("mySQLDefaultDatabaseName", String.class),
		new ObjectStreamField("mySQLDefaultHostname", String.class),
		new ObjectStreamField("debug", Boolean.class),
		new ObjectStreamField("informationSchemaName", String.class),
		//  TODO complete this list
}; */