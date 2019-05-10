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
import org.apache.commons.lang.SerializationUtils;

/***
 * @author nicomp
 */
public class Main {

	 /**
	  * Some test code. The Neo4j Engine must be stopped for the DBs that are tested here. 
	  * @param args
	  */
	 public static void main( String[] args ) {
//		 testCase01();
//		 testCase02();
//		 testCase03();
		 testCase04();
		 System.out.println("Done.");
	 }
	 private static void testCase01() {
		 Boolean isEqual = Neo4jDB.compareDatabases("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01",
				 									"C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01a",
				 									true, null, null);
		 System.out.println("Result of comparison = " + isEqual);
	 }
	 private static void testCase02() {
		 Boolean isEqual = Neo4jDB.compareDatabases("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase02",
				 									"C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase02a",
				 									true, null, null);
		 System.out.println("Result of comparison = " + isEqual);
	 }
	 private static void testCase03() {
		 Boolean isEqual = Neo4jDB.compareDatabases("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase03",
				 									"C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase03a",
				 									true, null, null);
		 System.out.println("Result of comparison = " + isEqual);
	 }
	 private static void testCase04() {
		 Boolean isEqual = Neo4jDB.compareDatabases("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase04",
				 									"C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase04a",
				 									true, null, null);
		 System.out.println("Result of comparison = " + isEqual);
	 }
}
