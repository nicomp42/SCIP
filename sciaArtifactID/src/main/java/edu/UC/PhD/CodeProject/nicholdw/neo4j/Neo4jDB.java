/* 
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.exception.NotImplementedException;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.neo4j.Neo4jNode.MATCHED_STATE;

public class Neo4jDB {

	public static final int dbmsConnectorBoltPort = 7687;
	public static final int dbmsConnectorHttpPort = 7474;
	private static Driver driver = null;
	private static GraphDatabaseService graphDB = null;
	private static String user, password; // Both credentials are case-sensitive!
	private static String uri;

	public static GraphDatabaseService getGraphDatabaseService() {
		return graphDB;
	}

	public static final String filePrefix = "FILE:///";
	public static final String OK = "OK";

	private Neo4jNodes neo4jNodes;
	private String name;
	private String filePath;

	public Neo4jDB(String name, String filePath) {
		setName(name);
		setFilePath(filePath);
		neo4jNodes = new Neo4jNodes();
	}
	public Neo4jDB(String name, String filePath, Neo4jNodes neo4jNodes) {
		setName(name);
		setFilePath(filePath);
		this.neo4jNodes = neo4jNodes;
	}
	/***
	 * Write the data to a CSV file
	 * @param filePath The file 
	 * @throws Exception 
	 */
	public boolean writeToCSV(String filePath) throws Exception {
		throw new NotImplementedException("Neo4jDB.writeToCSV() not implemented");
/*		boolean result = true;
		try {
			FileWriter fileWriter = new FileWriter(filePath);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (Neo4jNode neo4jNode : neo4jNodes.getNeo4jNodes()) {
				
			}
		} catch (Exception ex) {
			Log.logError("Neo4jDB.writeToCSV(" + filePath + "): " + ex.getLocalizedMessage());
			result = false;
		}
		return false;
*/
	}
	
	public Neo4jNodes getNeo4jNodes() {
		return neo4jNodes;
	}

	public void setNeo4jNodes(Neo4jNodes neo4jNodes) {
		this.neo4jNodes = neo4jNodes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/***
	 * Compare two Neo4j graphs
	 * 
	 * @param filePath01
	 *            File path to the folder containing the first graph
	 * @param filePath02
	 *            File path to the folder containing the second graph
	 * @param writeToLog
	 *            If true, the graphs will be written to the log after they are read
	 *            and processed. Make sure logging is turned on.
	 * @return
	 */
	public static boolean compareDatabases(String filePath01, String filePath02, boolean writeToLog, Neo4jNodes neo4jNodes01, Neo4jNodes neo4jNodes02, String[] keysToIgnore) {
		Log.logProgress("Neo4jUtils.compareDatabases(): comparing " + filePath01 + " and " + filePath02);
		if (keysToIgnore != null && keysToIgnore.length > 0) {
			for (String k: keysToIgnore) {
				Log.logProgress("Neo4jUtils.compareDatabases(): ignoring the key '" + k + "'");
			}
		}
		boolean isEqual = true;
//		Neo4jNodes db01, db02;
		readDatabase(filePath01, neo4jNodes01);
		readDatabase(filePath02, neo4jNodes02);
		if (writeToLog) {
			neo4jNodes01.log();
			neo4jNodes02.log();
		}
//		try (org.neo4j.graphdb.Transaction tx = graphDB.beginTx()) {
		try {
			neo4jNodes01.clearMatchedFlags();
			neo4jNodes02.clearMatchedFlags();
			// First look for complete matches
			Log.logProgress("Neo4jUtils.compareDatabases(): Checking for complete matches of unmatched nodes...");
			for (Neo4jNode neo4jNode : neo4jNodes01.getNeo4jNodes()) {
				Neo4jNode foundNode;
				if (neo4jNode.getMatchedState() == MATCHED_STATE.Unmatched) {
					foundNode = findNode(neo4jNode, neo4jNodes02, keysToIgnore, MATCHED_STATE.NodeAndRelationships);
					if (foundNode == null) {
						Log.logProgress("Neo4jUtils.compareDatabases(): node " + neo4jNode.toString() + " complete match not found.");
						isEqual = false;
					} else {
						Log.logProgress("Neo4jUtils.compareDatabases(): node " + neo4jNode.toString() + " complete match FOUND.");
						neo4jNode.setMatchedState(MATCHED_STATE.NodeAndRelationships);
						foundNode.setMatchedState(MATCHED_STATE.NodeAndRelationships);
					}
				}
			}
			// Next, check for a match of just the node name/properties, ignoring the relationships
			Log.logProgress("Neo4jUtils.compareDatabases(): Checking for node name/property matches of unmatched nodes...");
			for (Neo4jNode neo4jNode: neo4jNodes01.getNeo4jNodes()) {
				Neo4jNode foundNode;
				if (neo4jNode.getMatchedState() == MATCHED_STATE.Unmatched) {
					foundNode = findNode(neo4jNode, neo4jNodes02, keysToIgnore, MATCHED_STATE.NodeOnly);
					if (foundNode == null) {
						Log.logProgress("Neo4jUtils.compareDatabases(): node " + neo4jNode.toString() + " node match not found.");
						isEqual = false;
					} else {
						Log.logProgress("Neo4jUtils.compareDatabases(): node " + neo4jNode.toString() + " node match FOUND.");
						neo4jNode.setMatchedState(MATCHED_STATE.NodeOnly);
						foundNode.setMatchedState(MATCHED_STATE.NodeOnly);
					}
				}
			}
			if (neo4jNodes01.countUnmatchedNodes(MATCHED_STATE.NodeAndRelationships) == 0 && neo4jNodes02.countUnmatchedNodes(MATCHED_STATE.NodeAndRelationships) == 0) {
				Log.logProgress("Neo4jDB.compareDatabases(): No unmatched nodes"); 
			} else {
				isEqual = false;
				Log.logProgress("Neo4jDB.compareDatabases(): " + neo4jNodes01.countUnmatchedNodes(MATCHED_STATE.NodeAndRelationships) + " unmatched nodes in first DB, " + neo4jNodes02.countUnmatchedNodes(MATCHED_STATE.NodeAndRelationships) + " unmatched nodes in second DB");
				neo4jNodes01.printUnmatchedNodes();
				neo4jNodes02.printUnmatchedNodes();
//				if (neo4jNodes01 != null) {db01.copyUnmatchedNodes(neo4jNodes01);}
//				if (neo4jNodes02 != null) {db02.copyUnmatchedNodes(neo4jNodes02);}
			}
		} catch (Exception ex) {
			Log.logError("Neo4jUtils.compareDatabases(): " + ex.getMessage());
		}
		return isEqual;
	}
	/***
	 * Read the database into something we can deal with
	 * 
	 * @param filePath
	 *            The folder containing the database. Make sure it's not running.
	 * @param neo4jNodes the data structure containing the rows in the DB
	 */
	public static void readDatabase(String filePath, Neo4jNodes neo4jNodes) {
		Result result = null;
		GraphDatabaseService gds = null;
		try {
			gds = Neo4jDB.createDB(filePath, false);
			Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
			if (Neo4jDB.getDriver() == null) {
				Log.logError("Neo4jUtils.readDatabase(): Could not connect to Neo4j. Make sure that the database is *not* running.");
			} else {
				String query = "MATCH (n) RETURN n;";
				try (org.neo4j.graphdb.Transaction tx = getGraphDatabaseService().beginTx()) {
					result = getGraphDatabaseService().execute(query);
					while (result.hasNext()) {
						Map<String, Object> row = result.next();
						// printRow(row);
						for (Entry<String, Object> column : row.entrySet()) { // Each row should have one column but... just in case, we'll loop
							Node node = (Node) column.getValue();
							Neo4jNode.cloneNode(node, neo4jNodes);
						}
					}
					tx.success();
					tx.close();
					Neo4jDB.getDriver().close();
				} catch (Exception ex) {
					Log.logError("Neo4jUtils.readDatabase(): " + ex.getLocalizedMessage(), ex.getStackTrace());
				}
			}
		} catch (Exception ex) {
			Log.logError("Neo4jUtils.readDatabase(): " + ex.getLocalizedMessage());
		} finally {
			try {
				gds.shutdown();
			} catch (Exception ex) {
				Log.logError("Neo4jUtils.readDatabase(): gds.shutdown(): " + ex.getLocalizedMessage());
			}
		}
	}

	/***
	 * Read the database into something we can deal with
	 * 
	 * @param filePath
	 *            The folder containing the database. Make sure it's not running.
	 * @return The data structure containing all the rows in the database.
	 */
	public static Neo4jNodes readDatabase(String filePath) {
		Neo4jNodes neo4jNodes = new Neo4jNodes();
		readDatabase(filePath, neo4jNodes);
		return neo4jNodes;
	}
	/**
	 * Delete all the nodes and relationships in an open DB
	 */
	public static void clearDB() {
		String sql = "MATCH (n) DETACH DELETE n";
		try {
			Neo4jDB.getDriver();
			ExecActionQuery(sql);
		} catch (Exception e) {
			Log.logError("Neo4jUtils.clearDB(): ", e.getStackTrace());
		}
	}
	/**
	 * Execute an action query against the database. It must be started and
	 * listening on port #dbmsConnectorBoltPort.
	 * 
	 * @param cypher
	 *            The action query to be executed.
	 * @throws Exception
	 */
	public static void ExecActionQuery(final String cypher) throws Exception {
		// Don't call getDriver here. The driver should already be configured by
		// whomever called this method
		// getDriver();
		Log.logNeo4jQueryHistory(cypher);
		Log.logProgress("Neo4jUtils.ExecActionQuery(): " + "query = " + cypher);
		try (Session session = driver.session()) {
			Log.logProgress("Neo4jUtils.ExecActionQuery(): " + " session is " + (session.isOpen() ? "" : "not ") + "open");
			// If there is no active db, then this method call causes all kind of errors that we can't catch.
			// We will eventually end up in the catch block, below, but only after numerous
			// output has been generated by Neo4j classes.
			// TODO: Note that the Neo4j transaction manager will try to run the query
			// multiple times. This could be a problem.
			session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					try {					
						// This toLower is redundant if we are coming from submitNeo4jQuery
						if (Config.getConfig().getConvertNeo4jQueriesToLowerCase()) {
							tx.run(cypher.toLowerCase());
						} else {
							tx.run(cypher);
						}
						tx.success();
						tx.close();
					} catch (Exception ex) {
						Log.logError("Neo4jUtils.ExecActionQuery().execute(): " + ex.getLocalizedMessage());
					}
					return OK;
				}
			});
		} catch (Exception ex) {
			Log.logError("Neo4jUtils.ExecActionQuery(): " + ex.getLocalizedMessage(), ex.getStackTrace());
			throw new Exception(ex);
		}
	}

	/***
	 * Create a Neo4j Database in a folder. The DB should not be started **even** if
	 * it already exists. If the DB is already started you will get the error "Error
	 * starting org.neo4j.kernel.impl.factory.GraphDatabaseFacadeFactory"
	 * 
	 * @param dbPath
	 *            The folder name where the DB will be created. Must end in a
	 *            directory that exists.
	 * @param eraseFirst
	 *            If true, recursively delete the contents of the folder first
	 * @throws Exception
	 */
	public static GraphDatabaseService createDB(String dbPath, boolean eraseFirst /* , String newPassword */)
			throws Exception {
		try {
			// System.out.println("Clearing DB " + dbPath);
			if (eraseFirst) {
				deleteDBFileStructure(dbPath);
			}
			GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
			graphDB = dbFactory.newEmbeddedDatabase(new File(dbPath));
			// This doesn't work: "There is no procedure with the name
			// `dbms.security.changePassword` registered for this database instance."
			// graphDB.execute("CALL dbms.security.changePassword(" + "\"" + "Danger42" + "\")" );

			// TODO: At this point the DB will need to be manually started from the Neo4j
			// desktop app because I don't know another way to do it programmatically.
			// After it's started I can programmatically change the password and run other
			// transactions.
		} catch (Exception ex) {
			Log.logProgress("Neo4jDB.GraphDatabaseService(); " + ex.getLocalizedMessage());
			throw new Exception(ex);
		}
		return graphDB;
	}

	public static void closeConnection() {
		try {
			driver.close();
		} catch (Exception ex) {
		}
	}

	/**
	 * Delete all the files in the Neo4j folder. Be careful!
	 * 
	 * @param dbPath
	 */
	public static void deleteDBFileStructure(String dbPath) {
		try {
			FileUtils.deleteRecursively(new File(dbPath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Driver getDriver() {
		try {
			if (driver == null) {
				driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
			}
		} catch (Exception ex) {
			Log.logError("Neo4jUtils.getDriver(): " + ex.getLocalizedMessage());
		}
		return driver;
	}

	/**
	 * Define the connection parameters for the neo4j DB that should be listening on
	 * the default port that Bolt uses.
	 * 
	 * @param user
	 *            User name
	 * @param password
	 *            Password
	 */
	public static void setNeo4jConnectionParameters(String user, String password /* , String uri */) {
		Neo4jDB.user = user;
		Neo4jDB.password = password;
		Neo4jDB.uri = "bolt://localhost:" + dbmsConnectorBoltPort;
	}

/*	public static Neo4jNode findNode(Neo4jNode targetNode, Neo4jNodes db) {
		Neo4jNode foundNode = null;
		for (Neo4jNode neo4jNode : db.getNeo4jNodes()) {
			if (Neo4jNode.compareNodes(targetNode, neo4jNode) == true) {
				foundNode = neo4jNode;
				break;
			}
		}
		return foundNode;
	}
*/
 	public static Neo4jNode findNode(Neo4jNode targetNode, Neo4jNodes db, String[] keysToIgnore, MATCHED_STATE desiredMatchedState) {
 		Neo4jNode foundNode = null;
 		for (Neo4jNode neo4jNode: db.getNeo4jNodes()) {
 			if (neo4jNode.getMatchedState() == MATCHED_STATE.Unmatched) {
 				if (Neo4jNode.compareNodes(targetNode, neo4jNode, keysToIgnore) == desiredMatchedState) {foundNode = neo4jNode; break;}
 			}
 		}
 		return foundNode;
 	}
	/**
	 * This is useful for finding attributes in a schema topology graph that are not
	 * referenced by any queries
	 * 
	 * @return The recordset containing Attribute nodes that have exactly one
	 *         relationship. That would be the relationship to the table that
	 *         contains the attribute.
	 */
	public static StatementResult MatchAttributeNodesWithOneRelationship() {
		StatementResult statementResult = null;
		String query = "MATCH (a:Attribute)<-[r]-() WITH a, count(r) as rel_cnt WHERE rel_cnt = 1 RETURN a.name as myName, a.key as myKey;";
		Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(),
				Config.getConfig().getNeo4jDBDefaultPassword() /* , URI */);
		Driver myDriver = Neo4jDB.getDriver();
		try (Session session = myDriver.session()) {
			statementResult = session.run(query, Collections.singletonMap("x", "y")); // A formal parameter and the
																						// value it will take. In this
																						// case there are no parameters
																						// to this particular query.
			/*
			 * while (statementResult.hasNext()) { Record r = statementResult.next();
			 * System.out.println(r.get("myKey")); System.out.println(r.get("myName")); }
			 */
		} catch (Exception ex) {
			Log.logError("Neo4jUtils.MatchAttributeNodesWithOneRelationship(): " + ex.getLocalizedMessage(),
					ex.getStackTrace());
		}
		return statementResult;
	}

	/***
	 * Process all the stuff in a row of a database
	 * 
	 * @param row
	 *            The row to be processed
	 */
	public static void printRow(Map<String, Object> row) {
		String buffer = "";
		// Each column in the row
		for (Entry<String, Object> column : row.entrySet()) {
			buffer += column.getKey() + ": " + column.getValue() + "; ";
			Node node = (Node) column.getValue();
			for (org.neo4j.graphdb.Label label : node.getLabels()) {
				System.out.print(label.name() + " ");
			}
			Map<String, Object> properties = node.getAllProperties();
			System.out.printf("%d properties: %n", properties.entrySet().size());
			Neo4jNode.printNodeProperties(node);
			System.out.println("\nRelationships:");
			// for (org.neo4j.graphdb.Relationship relationship:
			// node.getRelationships(org.neo4j.graphdb.Direction.BOTH)) {
			for (org.neo4j.graphdb.Relationship relationship : node.getRelationships()) {
				System.out.print(" " + relationship.getType());
				if (relationship.getStartNodeId() == node.getId()) {
					// It's an outgoing relationship
					System.out.print("--->");
				} else {
					System.out.print("<---");
				}
				System.out.print(" [" + relationship.getEndNode().getLabels().iterator().next() + "]");
				RelationshipType rt = relationship.getType();
				Node endNode = relationship.getOtherNode(node);
				Neo4jNode.printNodeProperties(endNode);
				System.out.println();
			}
			System.out.println();
		}
	}

	/**
	 * Change the password by editing the file where the password is stored. Yikes.
	 * 
	 * @return True if the logic thinks the pw was changed, false otherwise.
	 * @throws Exception
	 *             if something goes wrong.
	 */
	public static boolean changePassword() throws Exception {
		boolean status = false;
		if (status == false) {
			throw new Exception("Neo4jUtils.changePassword(): not implemented"); // TODO need to implement this
		}
		return status;
	}
	public static boolean renameNodeLabel(String key, String oldLabel, String newLabel) {
		boolean success = true;
		Neo4jDB.submitNeo4jQuery("MATCH (n:" + 
		                         "\"" + oldLabel + "\"" + 
				                 "{key:" + "\"" + key + "\"" + "})" +		                         
		                         " REMOVE n:" + "\"" + oldLabel + "\"" + 
		                         " SET n:" + "\"" + newLabel + "\"");
		return success;
	}
	/**
	 * Use this and it calls executeActionQuery.
	 * @param query
	 * @return
	 */
	public static boolean submitNeo4jQuery(String query) {
		boolean status = true;		// Hope for the best
		try {
			Neo4jDB.getDriver();
			if (Config.getConfig().isReplaceBackslashInNeo4jQuery()) {
				query = query.replace("\\", "/");
			}
			if (Config.getConfig().getConvertNeo4jQueriesToLowerCase()) {
				Neo4jDB.ExecActionQuery(query.toLowerCase());
			} else {
				Neo4jDB.ExecActionQuery(query);
			}
		} catch (Exception ex) {
			status = false;
		}
		return status;
	}
	/**
	 * Process all the Neo4jNodes in the object. The graph must be open in Neo4j.
	 */
	public void exportToGraph() {
//		if (Neo4jDB.getDriver() == null) {
			driver = null;
			Neo4jDB.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
			Neo4jDB.getDriver();
//		}		
		Neo4jDB.clearDB();
		int newID  = 1;
		ArrayList<String> usedNodeIDs = new ArrayList<String>();
		try {
			// Add all the nodes first
			for (Neo4jNode neo4jNode: neo4jNodes.getNeo4jNodes()) {
				String nodeLabel;			// See the SCIP.grass file at https://github.com/nicomp42/scipGrass/blob/master/grass.css for node types!
				switch (neo4jNode.getMatchedState()) {
				case Unmatched:
					nodeLabel = "Unmatched";
					neo4jNode.setNewID(newID++);
					addNodeToGraph(neo4jNode, nodeLabel);
					break;
				case NodeOnly:
					nodeLabel = "Matched";
					neo4jNode.setNewID(newID++);
					addNodeToGraph(neo4jNode, nodeLabel);
					newID++;
					break;
				case NodeAndRelationships:
					nodeLabel = "Matched";
					neo4jNode.setNewID(newID);
					addNodeToGraph(neo4jNode, nodeLabel);
					break;
				default:
					// we should never get here
					break;
				}
			}
			// Now add the relationships. There will be duplicates
			for (Neo4jNode neo4jNode: neo4jNodes.getNeo4jNodes()) {
				String relationshipLabel;			// See the SCIP.grass file at https://github.com/nicomp42/scipGrass/blob/master/grass.css for relationship types!
				for (Neo4jRelationship neo4jRelationship: neo4jNode.getNeo4jRelationships().getNeo4jRelationships()) {
					if (!usedNodeIDs.contains(String.valueOf(neo4jRelationship.getID()))) {
						if (neo4jRelationship.isMatched()) {
							relationshipLabel = "Matched";
						} else {
							relationshipLabel = "Unmatched";
						}
						addRelationshipToGraph(neo4jNode, neo4jRelationship, relationshipLabel);
						usedNodeIDs.add(String.valueOf(neo4jRelationship.getID()));
					}
				}
			}
		} catch (Exception ex) {
			Log.logError("Neo4jDB.exportToGraph(): " + ex.getLocalizedMessage());
		}
		Utils.openBrowserWindow();
	}
	/**
	 * Don't call this until all the nodes have been added.
	 * (Since Neo4j relationships do not have labels... we add it to the relationship type)
	 * @param neo4jNode The node that contains the relationship
	 * @param neo4jRelationship The relationship to be added to the graph
	 * @param relationshipLabel The label that tells us if it's a matched or unmatched relationship.
	 */
	public static Boolean addRelationshipToGraph(Neo4jNode neo4jNode, Neo4jRelationship neo4jRelationship, String relationshipLabel) {
//		MATCH (a:Artist),(b:Album)
//		WHERE a.Name = "Strapping Young Lad" AND b.Name = "Heavy as a Really Heavy Thing"
//		CREATE (a)-[r:RELEASED]->(b)
		Boolean status = true;
		String properties = neo4jRelationship.getProperties().formatNeo4jProperties();
		// We have to match two nodes
		String cypher = "MATCH (a),(b)" +
						" WHERE a.OriginalID = " +
						Utils.QuoteMeDouble(String.valueOf(neo4jRelationship.getStartNodeID())) +
						" AND" +
						" b.OriginalID = " + Utils.QuoteMeDouble(String.valueOf(neo4jRelationship.getEndNodeID())) +
						" CREATE (a)-[r:" +
						Utils.QuoteMeBack(neo4jRelationship.getRelationshipType() +	"(" + relationshipLabel + ")") +
						"{" + 
						((properties.trim().length() > 0) ? (properties + ",") : "") + 
						" `OriginalID`:" + Utils.QuoteMeDouble(neo4jRelationship.getID()) +
						"}" +
						"]->(b)";
		try {
			Neo4jDB.ExecActionQuery(cypher);
			status = true;
		} catch (Exception ex) {
			Log.logError("Neo4jDB.addRelationshipToGraph(): " + ex.getLocalizedMessage());
			status = false;
		} finally {}
		return status;
	}
	public static Boolean addNodeToGraph(Neo4jNode neo4jNode, String nodeLabel) {
		// CREATE (n:MyLabel:`AppleLabel`{ `Name` : "MAC the Node", `Feel`:"cRUNCHY", `State`:"Ripe",  Disposition : "Happy" })
		Boolean status = true;
		String nodeProperties = Neo4jNode.formatNeo4jProperties(neo4jNode);
		String cypher = "CREATE (n:" + 
						Neo4jNode.formatLabels(neo4jNode) +
						":" + nodeLabel +
						"{" +
						((nodeProperties.trim().length() > 0) ? nodeProperties + ", " : "") + 
						"`NewID`:" + Utils.QuoteMeDouble(neo4jNode.getNewID()) +
						", `OriginalID`:" + Utils.QuoteMeDouble(neo4jNode.getNodeID()) +
						"}" + 
						")";
		try {
			Neo4jDB.ExecActionQuery(cypher);
			status = true;
		} catch (Exception ex) {
			Log.logError("Neo4jDB.addNodeToGraph(): " + ex.getLocalizedMessage());
			status = false;
		} finally {}
		return status;
	}
	public static Boolean setNodeProperty(String label, String key, String property, String propertyValue) {
		Boolean status = true;
		String cypher = "MERGE (n:" + label + 
						"{" +
						"key: " +
						Utils.quoteMeSingle(key) +
						"}) " + 
						"SET n." +
						property +
						"=" + 
						Utils.quoteMeSingle(propertyValue) ; 

		try {
			Neo4jDB.submitNeo4jQuery(cypher);
			status = true;
		} catch (Exception ex) {
			Log.logError("Neo4jDB.setNodeProperty(): " + ex.getLocalizedMessage());
			status = false;
		} finally {}
		return status;
	}
	public static void changeNodeLabel(String key, String oldLabel, String newLabel) {
//		match (n:attribute {key:'temporary.store.cityid'}) remove n:attribute set n:attribute_affected
		Neo4jDB.submitNeo4jQuery("match (n:"
				                + oldLabel 
				                + "{key:"
				                + "'" + key + "'"
				                + "}) remove n:"
				                + oldLabel
				                + " set n:" 
				                + newLabel);
	}
}

	
	