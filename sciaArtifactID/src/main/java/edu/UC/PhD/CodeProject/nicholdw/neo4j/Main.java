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
 * Neo4j DB Utilities
 * Some of these require that the DB that is started and listening on a port.
 * @author nicomp
 *
 */
public class Main {

	 /**
	  * Some test code
	  * @param args
	  */
	 public static void main( String[] args ) {
		 Boolean isEqual = Neo4jDB.compareDatabases("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01",
				 							"C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01a",
				 							true);
		 System.out.println("Result of comparison = " + isEqual);
		 System.out.println("Done.");
	 }
}
