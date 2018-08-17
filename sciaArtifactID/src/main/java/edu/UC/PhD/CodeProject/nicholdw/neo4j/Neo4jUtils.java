/*
 * CREATE CONSTRAINT ON (user:User) ASSERT user.name IS UNIQUE
 *
 */


package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.cypher.internal.javacompat.ExecutionEngine;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;

import com.google.common.collect.HashMultiset;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import net.sourceforge.htmlunit.corejs.javascript.ast.Label;
/***
 * Neo4j DB Utilities
 * Some of these require that the DB that is started and listening on a port.
 * @author nicomp
 *
 */
public class Neo4jUtils {

	public static final int dbmsConnectorBoltPort = 7687;
	public static final int dbmsConnectorHttpPort = 7474;
	private static Driver driver = null;
	private static GraphDatabaseService graphDB = null;
	private static String user, password;	// Both credentials are case-sensitive!
	private static String uri;
	public static GraphDatabaseService getGraphDatabaseService() {return graphDB;}
	public static final String filePrefix = "FILE:///";
	public static final String OK = "OK";

	/***
	 * Process all the stuff in a row of a database
	 * @param row The row to be processed
	 */
	public static void processRow(Map<String, Object> row) {
		String buffer = "";
        // Each column in the row
        for (Entry<String,Object> column : row.entrySet()) {
            buffer += column.getKey() + ": " + column.getValue() + "; ";
            Node node = (Node) column.getValue();
            for (org.neo4j.graphdb.Label label: node.getLabels()) {
            	System.out.print(label.name() + " ");
            }
            Map<String, Object> properties = node.getAllProperties();
            System.out.printf("%d properties: %n", properties.entrySet().size());
            printNodeProperties(node);
            System.out.println("\nRelationships:");
//            for (org.neo4j.graphdb.Relationship relationship: node.getRelationships(org.neo4j.graphdb.Direction.BOTH)) {
            for (org.neo4j.graphdb.Relationship relationship: node.getRelationships()) {
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
            	printNodeProperties(endNode);
            	System.out.println();
            }				            	
            System.out.println();
        }
	}
	/***
	 * Print the name/value pairs for all the properties of a node
	 * @param node The node to be processed
	 */
	public static void printNodeProperties(Node node) {
        Map<String, Object> properties = node.getAllProperties();
        for (Map.Entry<String, Object> property: properties.entrySet()) {
        	System.out.print("(" + property.getKey() + "= " + property.getValue() + ")");
        }
	}
	/***
	 * Read the database into something we can deal with
	 * @param filePath The folder containing the database. Make sure it's not running. 
	 * @return The data structure containing all the rows in the database.
	 */
	public static ArrayList<Map<String,Object> > readDatabase(String filePath) {
		ArrayList<Map<String,Object> > db = new ArrayList<Map<String,Object> >();
		Result result = null;
		ResourceIterator<Node> resultIterator = null;
		try {
			Neo4jUtils.createDB(filePath, false);
			Neo4jUtils.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
			if (Neo4jUtils.getDriver() == null) {
				Log.logError("Neo4jUtils.readDatabase(): Could not connect to Neo4j. Make sure that the database is *not* running.");
			} else {
				 String query = "MATCH (n) RETURN n;";
				 try ( org.neo4j.graphdb.Transaction tx = getGraphDatabaseService().beginTx() )
				 {
					 result = getGraphDatabaseService().execute(query);
					 while (result.hasNext()) {
				        Map<String,Object> row = result.next();
				        db.add(MapUtils.clone(row));	// We need a deep copy here. We can't access the row outside the transaction or something like that. 
				        processRow(row);
				     }
					 tx.success();
				 }
			}
		} catch (Exception ex) {
	    	 Log.logError("Neo4jUtils.readDatabase(): " + ex.getLocalizedMessage(),ex.getStackTrace());
		}
		return db;
	}

	public static void closeConnection() {
		try {driver.close();} catch (Exception ex) {}
	}
	/**
	 * Delete all the nodes and relationships in an open DB
	 */
	public static void clearDB() {
		String sql = "MATCH (n) DETACH DELETE n";
		try {
			ExecActionQuery(sql);
		} catch (Exception e) {
			Log.logError("Neo4jUtils.clearDB(): ", e.getStackTrace());
		}
	}
	/***
	 * Create a Neo4j Database in a folder. The DB should not be started **even** if it already exists.
	 * If the DB is already started you will get the error "Error starting org.neo4j.kernel.impl.factory.GraphDatabaseFacadeFactory"
	 * @param dbPath The folder name where the DB will be created. Must end in a directory that exists.
	 * @param eraseFirst If true, recursively delete the contents of the folder first
	 * @throws Exception
	 */
	public static GraphDatabaseService createDB(String dbPath, boolean eraseFirst /*, String newPassword*/) throws Exception {
		try {
			//System.out.println("Clearing DB " + dbPath);
			if (eraseFirst){deleteDBFileStructure(dbPath);}
			GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
			graphDB = dbFactory.newEmbeddedDatabase(new File(dbPath));
			// This doesn't work: "There is no procedure with the name `dbms.security.changePassword` registered for this database instance."
			//graphDB.execute("CALL dbms.security.changePassword(" + "\"" + "Danger42" + "\")" );

			// TODO: At this point the DB will need to be manually started from the Neo4j desktop app because I don't know another way to do it programmatically.
			// After it's started I can programmatically change the password and run other transactions.
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return graphDB;
	}
	/**
	 * Delete all the files in the Neo4j folder. Be careful!
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
				driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
			}
		} catch (Exception ex) {
			Log.logError("Neo4jUtils.getDriver(): " + ex.getLocalizedMessage());
		}
		return driver;
	}
	/**
	 * Define the connection parameters for the neo4j DB that should be listening on the default port that Bolt uses.
	 * @param user User name
	 * @param password Password
	 */
	public static void setNeo4jConnectionParameters(String user, String password  /*, String uri*/) {
		Neo4jUtils.user = user;
		Neo4jUtils.password = password;
		Neo4jUtils.uri = "bolt://localhost:" + dbmsConnectorBoltPort;
	}
	/**
	 * Execute an action query against the database. It must be started and listening on port #dbmsConnectorBoltPort.
	 * @param sql The action query to be executed.
	 * @throws Exception
	 */
	 public static void ExecActionQuery(final String sql ) throws Exception  {
//		Don't call getDriver here. The driver should already be configured by whomever called this method
//		getDriver();
	    try ( Session session = driver.session() ) {
    		 Log.logProgress("Neo4jUtils.ExecActionQuery(): " + "query = " + sql);
    		 Log.logProgress("Neo4jUtils.ExecActionQuery(): " + "session is " + (session.isOpen()? "" : "not ")  + "open");
    		 // If there is no active db, then this method call causes all kind of errors that we can't catch.
    		 // We will eventually end up in the catch block, below, but only after numerous output has been generated by Neo4j classes.
    		 // TODO: Note that the Neo4j transaction manager will try to run the query multiple times. This could be a problem.
    		 session.writeTransaction( new TransactionWork<String>()
	         {
	             @Override
	             public String execute( Transaction tx ) {
	                 try {
//	                	 StatementResult result = tx.run( sql );
	                	 Log.logNeo4jQueryHistory(sql);
	                	 tx.run(sql);
	                	 tx.success();
	                	 tx.close();
	                 } catch (Exception ex) {
	            		 Log.logError("Neo4jUtils.ExecActionQuery().execute(): " + ex.getLocalizedMessage());
	                 }
	                 return OK;
	             }
	         } );
	     } catch (Exception ex) {
	    	 Log.logError("Neo4jUtils.ExecActionQuery(): " + ex.getLocalizedMessage(),ex.getStackTrace());
	    	 throw new Exception(ex);
	     }
	 }
	 /**
	  * This is useful for finding attributes in a schema topology graph that are not referenced by any queries
	  * @return The recordset containing Attribute nodes that have exactly one relationship. That would be the relationship to the table that contains the attribute.
	  */
	 public static StatementResult MatchAttributeNodesWithOneRelationship() {
		 StatementResult statementResult = null;
		 String query = "MATCH (a:Attribute)<-[r]-() WITH a, count(r) as rel_cnt WHERE rel_cnt = 1 RETURN a.name as myName, a.key as myKey;";
		 Neo4jUtils.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword()  /*, URI*/);
		 Driver myDriver = Neo4jUtils.getDriver();
		 try (Session session = myDriver.session()) {
		    statementResult = session.run(query, Collections.singletonMap("x", "y"));	// A formal parameter and the value it will take. In this case there are no parameters to this particular query.
/*		    while (statementResult.hasNext()) {
		    	Record r = statementResult.next();
		        System.out.println(r.get("myKey"));
		        System.out.println(r.get("myName"));
		    }
*/
		} catch (Exception ex) {
	    	 Log.logError("Neo4jUtils.MatchAttributeNodesWithOneRelationship(): " + ex.getLocalizedMessage(),ex.getStackTrace());
		}
		 return statementResult;
	 }

	 private static void testCreateNewDatabase() {
		 try {
			 Neo4jUtils.createDB("c:/Temp/Clunk", true);
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	 }

	 /*
	 # Hello World Program in Bash Shell

	 echo "Hello World!"
	 SALT="03C73F743E3D0B86B2CACFFA9DFB9357"
	 HEX_SALT=$(echo -n $SALT | sed -r 's/(.{2})/\\x\1/g')
	 PASS=Danger42
	 HEX_PASS=$(echo -n $PASS | hexdump -v -e '"\\\x" 1/1 "%02X"')
	 echo $HEX_PASS
	 read -a PASSWORD_HASH_ARRAY <<< $(printf $HEX_SALT$HEX_PASS | sha256sum)
	 PASSWORD_HASH="${PASSWORD_HASH_ARRAY[0]}"
	 echo $PASWORD_HASH
	 */

	 /*
	  * Original auth file with default pw = neo4j
	 neo4j:SHA-256,37874CA49B3CAA5921720A9BC5D934A8631B95726A30554D3EA7C49384892196,03C73F743E3D0B86B2CACFFA9DFB9357:password_change_required

	 auth file With PW changed to Danger42
	 neo4j:ef6b3a140c57c90384313cce35a560f90cb27052c2225997caabff68acb147ad,03C73F743E3D0B86B2CACFFA9DFB9357

	 */
	 /**
	  * Change the password by editing the file where the password is stored. Yikes.
	  * @return True if the logic thinks the pw was changed, false otherwise.
	  * @throws Exception if something goes wrong.
	  */
 	public static boolean changePassword() throws Exception {
 		boolean status = false;
 		if (status == false) {
 			throw new Exception ("Neo4jUtils.changePassword(): not implemented");	// TODO need to implement this
 		}
 		return status;
 	}
	 	
 	public static boolean compareDatabases(String filePath01, String filePath02) {
 		Log.logProgress("Neo4jUtils.compareDatabases(): comparing " + filePath01 + " and " + filePath02);
 		boolean isEqual = true;
 		ArrayList<Map<String, Object>> db01, db02;
 		db01 = readDatabase(filePath01);
 		db02 = readDatabase(filePath02);
 		try (org.neo4j.graphdb.Transaction tx = graphDB.beginTx()) {
	 		for (Map<String,Object> row: db01) {
	 	        for (Entry<String,Object> column : row.entrySet()) {
	 	            Node node = (Node) column.getValue();
	 	            Node foundNode;
	 	            foundNode = findNode(node, db02, getGraphDatabaseService());
	 	            if (foundNode == null) {
	 	            	Log.logProgress("Neo4jUtils.compareDatabases(): node " + node.toString() + " not found.");
	 	            	isEqual = false;
	 	            }
	 	        }
	 		}
 		} catch (Exception ex) {
 			Log.logError("Neo4jUtils.compareDatabases(): " + ex.getMessage());
 		}
		return isEqual;
	}
 	public static Node findNode(Node targetNode, ArrayList<Map<String, Object>> db, GraphDatabaseService graphDB) {
 		Node foundNode = null;
 		for (Map<String,Object> row: db) {
 	        for (Entry<String,Object> column : row.entrySet()) {
 	            Node node = (Node) column.getValue();
 	            if (compareNodes(targetNode, node, graphDB) == true) {foundNode = node; break;}
 	        }
 		}
 		return foundNode;
 	}
 	public static boolean compareNodes(Node n1, Node n2, GraphDatabaseService graphDB) {
 		Boolean isEqual = false;
 		try {
	 		// Compare labels, if any
			HashMultiset<org.neo4j.graphdb.Label> n1Labels = HashMultiset.create(n1.getLabels());
			HashMultiset<org.neo4j.graphdb.Label> n2Labels = HashMultiset.create(n2.getLabels());
	 		if (n1Labels.equals(n2Labels)) {
	 	 		// Compare properties, if any
	 			HashMultiset<Map<String, Object>> n1Properties = HashMultiset.create();
	 			n1Properties.addAll((Collection<? extends Map<String, Object>>) n1.getAllProperties());
	 		
	 			HashMultiset<Map<String, Object>> n2Properties = HashMultiset.create();
	 			n2Properties.addAll((Collection<? extends Map<String, Object>>) n2.getAllProperties());
	
	 			if (n1Properties.equals(n2Properties)) {
	 	 	 		// Compare relationships
	
	 				
	 				// If we get this far, the nodes are equal. Woo hoo
	 	 			isEqual = true;
	 			}
	 		}
 		} catch (Exception ex) {
 			Log.logError("Neo4jUtils.compareNodes(): " + ex.getLocalizedMessage());
 		}
 		return isEqual;
 	}
	 /**
	  * Some test code
	  * @param args
	  */
	 public static void main( String[] args ) {

		 
//		 ArrayList<Map<String, Object>> db = Neo4jUtils.readDatabase("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01");
		 Boolean isEqual = compareDatabases("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01",
				 							"C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01a");
		 System.out.println("Result of comparison = " + isEqual);
		 
//		 MatchAttributeNodesWithOneRelationship();
//		 testCreateNewDatabase();
/*
		 Neo4jUtils.setNeo4jConnectionParameters(Config.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
		 Driver myDriver = Neo4jUtils.getDriver();

		 // Change the password and add a few nodes.
//		 String URI = "bolt://localhost:" + dbmsConnectorBoltPort;
//		 String newPassword = "Qbert";
//		 System.out.println("Changing Neo4j Password for user " + user + " in database listening on " + URI + " from " + password + " to " + newPassword);
//		 try {ExecActionQuery("CALL dbms.security.changePassword(\"" + newPassword + "\");" ); } catch (Exception ex) {ex.printStackTrace();}
//		 try {ExecActionQuery("create (n:Cat:Bruno)" ); } catch (Exception ex) {ex.printStackTrace();}

		 String query = "match (t:Table ) return t.key as myKey, t.name as myName";	// {myName} is a formal parameter
//		 String query = "match (t:Table {name:{myName}}) return t.key as myKey, t.name as myName";	// {myName} is a formal parameter
		 try (Session session = myDriver.session()) {
			    StatementResult result = session.run(query, Collections.singletonMap("myName", "tbeta"));	// A formal parameter and the value it will take
			    while (result.hasNext()) {
			    	Record r = result.next();
			        System.out.println(r.get("myKey"));
			        System.out.println(r.get("myName"));
//			        System.out.println(result.next().get("myKey"));
			    }
			}
*/
		 System.out.println("Done.");
	 }




}



