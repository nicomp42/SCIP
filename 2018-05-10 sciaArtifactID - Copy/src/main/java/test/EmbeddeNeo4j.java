
// http://docs.spring.io/spring-data/neo4j/docs/3.1.1.RELEASE/reference/html/neo4j.html
package test;

import java.io.File;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.GraphDatabase;

import java.io.IOException;
import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.io.fs.FileUtils;

public class EmbeddeNeo4j {

	private static final String DB_PATH = "c:/temp/yuk";
	static String greeting = "Create Database at " + DB_PATH + " and add a few nodes...";
	GraphDatabaseService graphDb;
	Node firstNode;
	Node secondNode;
	Relationship relationship;

	private static enum RelTypes implements RelationshipType {KNOWS}

	public static void main(final String[] args) {

		testConnectionToExistingDatabase();


		EmbeddeNeo4j hello = new EmbeddeNeo4j();
		hello.createDb();
//		hello.removeData();
		hello.changePassword();
		hello.shutDown();
		System.out.println(greeting);
	}
	private static void testConnectionToExistingDatabase() {
		org.neo4j.driver.v1.Driver driver;
		driver = GraphDatabase.driver( DB_PATH, AuthTokens.basic( "neo4j", "neo4j" ) );


	}
	private void changePassword() {

	}
	void createDb() {
		System.out.println("Clearing DB " + DB_PATH);
		clearDb();
		// start DB
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		graphDb = dbFactory.newEmbeddedDatabase(new File(DB_PATH));
		registerShutdownHook(graphDb);

		// start Transaction
		Transaction tx = graphDb.beginTx();
		try {
			// adding data
			firstNode = graphDb.createNode();
			firstNode.setProperty("message", "Hello, ");
			secondNode = graphDb.createNode();
			secondNode.setProperty("message", "World!");

			relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
			relationship.setProperty("message", "brave Neo4j ");

			// reading data
			System.out.println(firstNode.getProperty("message"));
			System.out.println(relationship.getProperty("message"));
			System.out.println(secondNode.getProperty("message"));

			greeting = (String) firstNode.getProperty("message") + (String) relationship.getProperty("message") + (String) secondNode.getProperty("message");

			Iterator<Relationship> it = firstNode.getRelationships().iterator();
			System.out.println("Reading nodes");
			while(it.hasNext()) {
				Relationship r = it.next();
				Node[] nodes = r.getNodes();
				System.out.println(nodes[0].getProperty("message") + " " + r.getProperty("message") + " " + nodes[1].getProperty("message"));
			}

			tx.success();
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		} finally {
			//tx.finish();
		}
		/*
		System.out.println("Traversing...");
		TraversalDescription traversalDescription = Traversal.description()
				   .depthFirst()
				   .relationships(KNOWS)
				   .relationships(LIKES, Direction.INCOMING)
				   .evaluator(Evaluators.toDepth(5));
				for (Path position : traversalDescription.traverse(myStartNode)) {
				   System.out.println("Path from start node to current position is " + position);
				}
		*/

	}

	private void clearDb() {
		try {
			FileUtils.deleteRecursively(new File(DB_PATH));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	void removeData() {
		Transaction tx = graphDb.beginTx();
		try {
			// removing data
			firstNode.getSingleRelationship(RelTypes.KNOWS, Direction.OUTGOING).delete();
			firstNode.delete();
			secondNode.delete();

			tx.success();
		} finally {
			//tx.finish();
		}
	}

	void shutDown() {
		System.out.println("\nShutting down database");
		// shutdown server
		graphDb.shutdown();
	}

	// shutdown hook
	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

}
