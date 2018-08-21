package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.io.BufferedReader;
import java.io.FileReader;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * Process a script of Cypher statements and submit them one-by-one to the current DB
 * @author nicomp
 */
public class ProcessScript {
	/***
	 * Read cypher commands from a text file and execute them one-by-one
	 * @param fileName The text file
	 * @param neo4jFilePath The Neo4j DB to be used. Must be open. 
	 * @return the number of commands submitted to the DB
	 */
	public int processScript(String fileName, String neo4jFilePath) {
		Log.logProgress("ProcessScript.processScript() : executing commands from " + fileName + " against Neo4j Database " + neo4jFilePath);
		int totalCommands = 0;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		String command = "", tmp = "";
		try {
//			Neo4jUtils.createDB(neo4jFilePath, false);
			if (Neo4jDB.getDriver() == null) {
				Neo4jDB.setNeo4jConnectionParameters(Config	.getConfig().getNeo4jDBDefaultUser(), Config.getConfig().getNeo4jDBDefaultPassword());
				Neo4jDB.getDriver();
			}
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			tmp = bufferedReader.readLine();
			while (tmp != null) {
				if (tmp.trim().length() > 1 && tmp.trim().substring(0, 2).equals("--")) {
					// It's a comment so we execute whatever we have to this point
					if (command.trim().length() > 0) {
						Log.logProgress("ProcessScript.processScript() : executing " + command);
						Neo4jDB.ExecActionQuery(command);
						totalCommands++;
						command = "";
					}
				} else {
					command += " " + tmp;		// It's not a comment so it must be part of a cypher query
				}
				tmp = bufferedReader.readLine();			
			}
		}catch (Exception ex) {
			Log.logError("" + ex.getLocalizedMessage());
		}
		return totalCommands;
	}
	
	public static void main(String[] args) {
		ProcessScript ps = new ProcessScript();
		int totalCommands = 0;

		totalCommands = ps.processScript("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\PopulateTestCase01.txt", 
				         				 "C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01\\");
		System.out.println(totalCommands + " commands executed.");

		// The database needs to be stopped and the next database restarted!
		//totalCommands = ps.processScript("C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\PopulateTestCase01a.txt", 
		//		 						 "C:\\Users\\nicomp\\git\\SCIP\\sciaArtifactID\\TestCases\\CompareGraphs\\TestCase01a\\");
		//System.out.println(totalCommands + " commands executed.");
	}
}
