package edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import edu.UC.PhD.CodeProject.nicholdw.ActionQuerys;
import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStep;
import edu.UC.PhD.CodeProject.nicholdw.Schemas;
import edu.UC.PhD.CodeProject.nicholdw.TableOutputStep;
import edu.UC.PhD.CodeProject.nicholdw.StepName;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.ActionQueryDefinitions;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.DatabaseGraphConfig;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.GraphResults;
import edu.UC.PhD.CodeProject.nicholdw.schemaTopology.SchemaGraph;
import edu.nicholdw.PhD.CodeProject.ETL.DBProcStep;
import edu.nicholdw.PhD.CodeProject.ETL.ETLKTRFile;
import edu.nicholdw.PhD.CodeProject.ETL.ETLProcess;

/**
 * A schema change impact project
 * @author nicomp
 */
public class SchemaChangeImpactProject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String hostName, userName, password;
	private String projectName;
	private String filePath;
	private Date dateCreated;
	private String pentahoProjectDirectory;
	private String comment;
	private DatabaseConnection databaseConnection_ops;		// Connection to Operational Data Store
	private String neo4jDBDirectory;
	private Operational operational = new Operational();
	private IdsDwh idsDwh;
	private OpsIds opsIds;
	private DwhQueries dwhQueries;
	private ETLKTRFile etlKTRFile;	// TODO needs to go away and be subsumed in etlProcess
	private ETLProcess etlProcess;
	private Schemas schemas;
	private DatabaseGraphConfig databaseGraphConfig;
	private ActionQuerys actionQuerys;
	private ActionQueryDefinitions actionQueryDefinitions;
//	private GraphTopology schemaTolopogy;
	private GraphResults graphResults;
	private static final String defaultProjectName = "[No project loaded]";	// Until the user provides a project name
	//private ArrayList<TableOutputStep> outputSteps = new ArrayList<TableOutputStep>();
	//private ArrayList<TableInputStep> tableInputSteps = new ArrayList<TableInputStep>();
	//private ArrayList<DBJoinStep> dbJoinSteps = new ArrayList<DBJoinStep>();
	//private ArrayList<StepName> stepNames = new ArrayList<StepName>();
	//private ArrayList<String> connectionNames = new ArrayList<String>();
	//private ArrayList<DBProcStep> dbProcSteps = new ArrayList<DBProcStep>();

	// Subdirectories in the project that will contain files to be processed
	public static String operationalSubDirectory = "ops";		// Operational Data Store
	public static String idsDwhSubdirectory = "ids-dwh";		// Intermediate Data Store to Data Warehouse
	public static String opsIdsSubdirectory = "ops-ids";		// Operational Data Store to Intermediate Data Store
	public static String dwhQueriesSubdirectory = "queries";	// Data Warehouse production queries and maybe other queries from various points in the process
	public static String dwhQueriesFileName = "queries";

	public SchemaChangeImpactProject() {
		init();
		try {setProjectName(defaultProjectName);} catch (Exception ex){}		// OK to eat this exception.
	}
	
	/***
	 * Populates graphResults object in this object.
	 * call getGraphResults();
	 */
	public void generateGraph() {
		// Everything we need should already be in the current scip object
		SchemaGraph schemaGraph = new SchemaGraph(this);
		schemaGraph.generateGraph();
	}
	public GraphResults getGraphResults() {return graphResults;}
	public void setGraphResults(GraphResults graphResults) {this.graphResults = graphResults;}
	
	public void setDatabaseGraphConfig(DatabaseGraphConfig databaseGraphConfig) {
		this.databaseGraphConfig = databaseGraphConfig;
	}
	public DatabaseGraphConfig getDatabaseGraphConfig() {
		return databaseGraphConfig;
	}
	public void setActionQuerys(ActionQuerys actionQuerys) {
		this.actionQuerys = actionQuerys;
	}
	public ActionQuerys getActionQuerys() {return actionQuerys;}
	/***
	 * Try to add an action query to the collection.
	 * The action query is not applied to the schemas, it's just parsed and stored. 
	 * @param actionQuerySQL The SQL  of the action query
	 * @return The QueryDefinition object representing the action query SQL, or null
	 */
	public QueryDefinition addActionQuery(String actionQuerySQL) {
		QueryDefinition qd = null;
		try {
			qd = new QueryDefinition("", "", "", null, "", actionQuerySQL, "");
//			QueryParser qp = new QueryParser();
			qd.setSql(actionQuerySQL);
			qd.crunchIt();
			actionQueryDefinitions.addActionQueryDefinition(qd);
		} catch (Exception ex) {
			Log.logError("SchemaChangeImpactProject.addActionQuery()" + ex.getLocalizedMessage() + " SQL = " + actionQuerySQL);
			qd = null;	
		}
		return qd;
	}
	public ActionQueryDefinitions GetActionQueryDefinitions() {
		return actionQueryDefinitions;
	}
	/**
	 * Build the location, with fileName, where the CSV file for the Dwh step will be written
	 * @return The filename, with full path
	 */
	public String getDwhCSVFilePath() {
		return  Utils.formatPath(Utils.formatPath(this.getFullProjectPath())
				                                + SchemaChangeImpactProject.dwhQueriesSubdirectory)
				                                + SchemaChangeImpactProject.dwhQueriesFileName + ".CSV";
	}
	/**
	 * Create a Schema Change Impact project
	 * @param name The name of the project
	 * @param filePath The file path to the project folder. Do not include the name of the project
	 * @throws Exception if name is blank
	 */
	public SchemaChangeImpactProject(String name, String filePath, String neo4jDBName, String pentahoProjectDirectory) throws Exception {
		setProjectName(name);
		setFilePath(filePath);
		setDateCreated(new Date());
		setDatabaseConnection_ops(new DatabaseConnection());
		setNeo4jDBName(neo4jDBName);
		setPentahoProjectDirectory(pentahoProjectDirectory);
		init();
	}
	/**
	 * Set up a new project. Constructors call this.
	 */
	private void init() {
		setOperational(new Operational());
		setIdsDwh(new IdsDwh());
		setOpsIds(new OpsIds());
		setDwhQueries(new DwhQueries());
		setETLKTRFile(new ETLKTRFile(null));
		actionQueryDefinitions = new ActionQueryDefinitions();
		actionQuerys = new ActionQuerys();
		setEtlProcess(new ETLProcess());
	}
	public void loadETLTransformationFiles() {
		etlKTRFile.loadETLTransformationFiles();
	}
	/***
	 * Get the path of the Neo4j project subdirectory
	 * @return The full path, ending in a subdirectory, of the Neo4j database for this project
	 */
	public String getNeo4jGraphDBFilePath() {
//		return Utils.formatPath(Utils.formatPath(getFilePath()) + getProjectName()) + getNeo4jDBDirectory();		// This is an absolute path. Do not let Neo4j see it.
		return Utils.formatPath(Utils.formatPath(getFilePath()) + getNeo4jDBDirectory());		// This is an absolute path. Do not let Neo4j see it.
	}
	/***
	 * Copy the CSV files for this project into the super-secure location in the Neo4j file structure.
	 */
	public boolean copyDirectoryStructures() {
		boolean status = true;		// hope for the best
		// This might fail if the import directory already exists
		String neo4jGraphDBFilePath = Utils.formatPath(Utils.formatPath(getFilePath()) + getNeo4jDBDirectory());		// This is an absolute path. Do not let Neo4j see it.
		try {
			// Make the import folder in the Neo4j DB file structure. Neo4j doesn't automatically do this!
			Files.createDirectory(Paths.get(Utils.formatPath(neo4jGraphDBFilePath) + "import/"));
		} catch (Exception ex) {
    		Log.logError("SchemaChangeImpactProject.copyDirectoryStructures(): error creating import directory: " + ex.getLocalizedMessage());
		}
		try {
			// Duplicate all the CSV files into the Neo4j super-secure file store.
			Utils.copyDirectoryStructure(new File(Utils.formatPath(Utils.formatPath(getFullProjectPath()) + SchemaChangeImpactProject.operationalSubDirectory)),
										 new File(Utils.formatPath(neo4jGraphDBFilePath) + "import/" + SchemaChangeImpactProject.operationalSubDirectory));
			Utils.copyDirectoryStructure(new File(Utils.formatPath(Utils.formatPath(getFullProjectPath()) + SchemaChangeImpactProject.idsDwhSubdirectory)),
										 new File(Utils.formatPath(neo4jGraphDBFilePath) + "import/" + SchemaChangeImpactProject.idsDwhSubdirectory));
			Utils.copyDirectoryStructure(new File(Utils.formatPath(Utils.formatPath(getFullProjectPath()) + SchemaChangeImpactProject.opsIdsSubdirectory)),
										 new File(Utils.formatPath(neo4jGraphDBFilePath) + "import/" + SchemaChangeImpactProject.opsIdsSubdirectory));
			Utils.copyDirectoryStructure(new File(Utils.formatPath(Utils.formatPath(getFullProjectPath()) + SchemaChangeImpactProject.dwhQueriesSubdirectory)),
										 new File(Utils.formatPath(neo4jGraphDBFilePath) + "import/" + SchemaChangeImpactProject.dwhQueriesSubdirectory));
    	} catch(Exception ex) {
    		Log.logError("SchemaChangeImpactProject.copyDirectoryStructures(): " + ex.getLocalizedMessage());
    		status = false;
    	}
		return status;
	}
	/***
	 * Set up the file structure for the project
	 * @throws Exception if something goes wrong
	 */
	public void buildFileStructure() throws Exception {
		try {
			String fullProjectPath = Utils.formatPath(getFullProjectPath()); 
    		new File(fullProjectPath + operationalSubDirectory).mkdirs();
    		new File(fullProjectPath + idsDwhSubdirectory).mkdirs();
    		new File(fullProjectPath + opsIdsSubdirectory).mkdirs();
    		new File(fullProjectPath + dwhQueriesSubdirectory).mkdirs();
    		new File(fullProjectPath + neo4jDBDirectory).mkdirs();
    		new File(fullProjectPath + pentahoProjectDirectory).mkdirs();
    		
		} catch (Exception ex) {
			throw new Exception ("SchemaChangeImpactProject.buildFileStructure()" + ex.getLocalizedMessage());
		}
	}
	/***
	 * Build a full path that includes project name.
	 * @return The path
	 */
	public String getFullProjectPath() {
		return getFilePath();					// + "/" + getProjectName();
	}
	public static SchemaChangeImpactProject load(String filePathWithFileName) {
		SchemaChangeImpactProject scip = null;
		Log.logProgress("SchemaChangeImpactProject.load(): attempting to load " + filePathWithFileName);
		try {
			 FileInputStream fileIn = new FileInputStream(filePathWithFileName);
			 ObjectInputStream in = new ObjectInputStream(fileIn);
			 scip = (SchemaChangeImpactProject) in.readObject();
			 in.close();
			 fileIn.close();
	 		Log.logProgress("SchemaChangeImpactProject.load(): " + filePathWithFileName + " is loaded.");
		} catch (Exception ex) {
			Log.logError("SchemaChangeImpactProject.load()" + ex.getLocalizedMessage() + " file = " + filePathWithFileName);
		}
		return scip;
	}
	/**
	 * Save the serialized object
	 */
	public Boolean save() {
		Boolean status = true;	// Hope for the best
		Log.logProgress("SchemaChangeImpactProject.save(): attempting to save " + getFullProjectPath() + "/" + getProjectName() + Config.getConfig().getSCIPFileExtension());
		try {
			 FileOutputStream fileOut = new FileOutputStream(getFullProjectPath() + "/" + getProjectName() + Config.getConfig().getSCIPFileExtension());
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this);
	         out.close();
	         fileOut.close();
	 		Log.logProgress("SchemaChangeImpactProject.save(): " + getFullProjectPath() + "/" + getProjectName() + Config.getConfig().getSCIPFileExtension() + " is saved.");
		} catch (Exception ex) {
			Log.logError("SchemaChangeImpactProject.save()" + ex.getLocalizedMessage() + " project name = " + this.getProjectName() + ", project file path = " + this.getFilePath() );
			status = false;
		}
		return status;
	}
	/**
	 * Get the name of the project
	 * @return The name of the project
	 */
	public String getProjectName() {return projectName;}

	/**
	 * Set the name of the project
	 * @param name The name of the project
	 * @throws Exception if name is blank
	 */
	public void setProjectName(String name) throws Exception {
		if (name.trim().length() == 0) {
			throw new Exception("SchemaChangeImpactProject: name cannot be blank");
		}
		this.projectName = name;
	}
	/**
	 * Get the file path where the project files are stored.
	 * @return The file path
	 */
	public String getFilePath() {return filePath;}

	/**
	 * Set the file path of the project
	 * @param filePath The file path of the project
	 * @throws Exception if filePath is blank
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getDateCreated() {return dateCreated;}
	public void setDateCreated(Date dateCreated) {this.dateCreated = dateCreated;}

	public String getComment() {return comment;}
	public void setComment(String comment) {this.comment = comment;}

	public DatabaseConnection getDatabaseConnection_ops() {return databaseConnection_ops;}
	public void setDatabaseConnection_ops(DatabaseConnection databaseConnection_ops) {this.databaseConnection_ops = databaseConnection_ops;}

	public String getPentahoProjectDirectory() {return pentahoProjectDirectory;}

	public void setPentahoProjectDirectory(String pentahoProjectDirectory) {this.pentahoProjectDirectory = pentahoProjectDirectory;}

	public String getNeo4jDBDirectory() {return neo4jDBDirectory;}

	public void setNeo4jDBName(String neo4jDBName) {this.neo4jDBDirectory = neo4jDBName;}

	public Operational getOperational() {return operational;}

	public void setOperational(Operational operational) {this.operational = operational;}

	public IdsDwh getIdsDwh() {return idsDwh;}

	public void setIdsDwh(IdsDwh idsDwh) {this.idsDwh = idsDwh;}

	public OpsIds getOpsIds() {return opsIds;}

	public void setOpsIds(OpsIds opsIds) {this.opsIds = opsIds;}

	public DwhQueries getDwhQueries() {return dwhQueries;}

	public void setDwhQueries(DwhQueries dwhQueries) {this.dwhQueries = dwhQueries;}
	public ETLKTRFile getETLKTRFile() {
		return etlKTRFile;
	}
	public void setETLKTRFile(ETLKTRFile etlKTRFile) {
		this.etlKTRFile = etlKTRFile;
	}
	public Schemas getSchemas() {
		return schemas;
	}
	public void setSchemas(Schemas schemas) {
		this.schemas = schemas;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ETLProcess getEtlProcess() {
		return etlProcess;
	}

	public void setEtlProcess(ETLProcess etlProcess) {
		this.etlProcess = etlProcess;
	}
}
