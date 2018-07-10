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
import java.util.List;

import com.mysql.jdbc.CallableStatement;

import edu.UC.PhD.CodeProject.nicholdw.ETLExcelExporter;
import edu.UC.PhD.CodeProject.nicholdw.OutputStep;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.XMLParser;
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
		
		XMLParser xmlparser=new XMLParser();
		List<OutputStep> outputSteps = xmlparser.parseXMLForOutputSteps("C:\\Users\\nicomp\\Google Drive\\PhD (1)\\TestCases\\FullCoverageTestCase\\FullCoverageTestCaseSalesMappingToReconciledSchema.ktr");
		ETLExcelExporter.generateOutputStepsCsvFile("c:\\Temp\\fooo.csv", outputSteps);

		List<TableInputStep> inputSteps = xmlparser.parseXMLForInputSteps("C:\\Users\\nicomp\\Google Drive\\PhD (1)\\TestCases\\FullCoverageTestCase\\FullCoverageTestCaseSalesMappingToReconciledSchema.ktr");		
		ETLExcelExporter.generateInputStepsCsvFile("c:\\Temp\\fooi.csv", inputSteps);

		// 1. Run these Stored Procedures in this order
		// `testcasecreationscripts`.`CreateFullCoverageTestCase`
		// 2. Run the Pentaho jobs
		/// FullCoverageTestCaseSalesMappingToReconciledSchema.ktr
		// 3. Run these Stored Procedures in this order
		// `testcasecreationscripts`.`UpdateDateFieldsInReconciledWeatherTable`
		// `testcasecreationscripts`.`PopulateMonthlySalesByProductAndStore`
		// 	`testcasecreationscripts`.`PopulateWeeklySalesByProductAndStore`
		// `testcasecreationscripts`.`PopulateStoreSalesByTemperature`
		
/*		
		Log.logProgress("FullCoverage.run(): starting...");
		try {
			createOperationalSchemas();
			submitEvolutionOperators();
		} catch (Exception ex) {
			Log.logError("FullCoverage.run(): " + ex.getLocalizedMessage());
		}finally {
			Log.logProgress("FullCoverage.run(): done.");
		}
*/
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
			
			SQLUtils.callStoredProcedure(connectionInformation.getHostName(), "", connectionInformation.getLoginName(), connectionInformation.getPassword(), "{CALL `testcasecreationscripts`.`CreateFullCoverageTestCase`()}");
/*			
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
*/
		} catch (Exception ex) {
			Log.logError("FullCoverage.createOperationalSchemas(): " + ex.getLocalizedMessage());
		} finally {
//	   	    try {reader.close();} catch (IOException e) {}		
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
	@Override
	public boolean LoadTransactionLog() {
    	Log.logProgress("FullCoverage.LoadTransactionLog(): starting...");
    	try {
    		
    	} catch (Exception ex) {
        	Log.logError("FullCoverage.LoadTransactionLog(): " + ex.getLocalizedMessage());
    	}
    	
		return false;
	}
	
	/***
	 * Run all the evolution operators against the operational schemas
	 * @return
	 */
	public boolean submitEvolutionOperators() {
		boolean status = true;
		
		
		return status;
	}
	public boolean createAttributeOperationsAsStoredProcedures() {
		// Rename a table attribute
		String sql = "USE `dwattributeoperations`;\r\n" + 
					 "DROP procedure IF EXISTS `renametableattribute`;\r\n" + 
					 "\r\n" + 
					 "DELIMITER $$\r\n" + 
					 "USE `dwattributeoperations`$$\r\n" + 
					 "CREATE PROCEDURE `renametableattribute` ()\r\n" + 
					 "BEGIN\r\n" + 
					 "	Alter table sales.transaction change   dateTimeOfTransaction    datestampoftransaction datetime  ;\r\n" + 
					 "END;$$\r\n" + 
					 "\r\n" + 
					 "DELIMITER ;\r\n";
		// rename a table
		SQLUtils.executeActionQuery(connectionInformation, sql);
		sql = "CREATE DEFINER=`root`@`localhost` PROCEDURE `renametable`()\r\n" + 
				"BEGIN\r\n" + 
				"RENAME TABLE `sales`.`transaction` TO `trans`;\r\n" + 
				"END";
		SQLUtils.executeActionQuery(connectionInformation, sql);
		// delete a table
		sql = "CREATE DEFINER=`root`@`localhost` PROCEDURE `deletetable`()\r\n" + 
				"BEGIN\r\n" + 
				"DROP TABLE `sales`.`transaction` ;\r\n" + 
				"END";
		SQLUtils.executeActionQuery(connectionInformation, sql);
		// delete a query
		sql = "CREATE DEFINER=`root`@`localhost` PROCEDURE `deletequery`()\r\n" + 
				"BEGIN\r\n" + 
				"-- *************************\r\n" + 
				"-- Same as dropping a table\r\n" + 
				"-- *************************\r\n" + 
				"DROP TABLE `reconciled.mapping` ;\r\n" + 
				"END";
		SQLUtils.executeActionQuery(connectionInformation, sql);
		// Rename a query attribute. This is just a replacement of the existing query
		sql = "CREATE DEFINER=`root`@`localhost` PROCEDURE `renamequeryattribute`()\r\n" + 
				"BEGIN\r\n" + 
				"CREATE \r\n" + 
				"     OR REPLACE ALGORITHM = UNDEFINED \r\n" + 
				"    DEFINER = `root`@`localhost` \r\n" + 
				"    SQL SECURITY DEFINER\r\n" + 
				"VIEW `reconciled`.`mapping` AS\r\n" + 
				"    SELECT \r\n" + 
				"        `hr`.`employee`.`FirstName` AS `EmplFirstName`,\r\n" + 
				"        `hr`.`employee`.`LastName` AS `EmployeeLastName`,\r\n" + 
				"        `product`.`product`.`Description` AS `ProductDescription`,\r\n" + 
				"        `product`.`unit`.`Unit` AS `Unit`,\r\n" + 
				"        `product`.`product`.`SKU` AS `SKU`,\r\n" + 
				"        `sales`.`transactiondetail`.`Qty` AS `Qty`,\r\n" + 
				"        `product`.`product`.`UnitCost` AS `UnitCost`,\r\n" + 
				"        `product`.`product`.`UnitPrice` AS `UnitPrice`,\r\n" + 
				"        `product`.`manufacturer`.`Manufacturer` AS `Manufacturer`,\r\n" + 
				"        `sales`.`transaction`.`EmployeeNumber` AS `EmployeeNumber`,\r\n" + 
				"        `sales`.`transaction`.`LoyaltyNumber` AS `LoyaltyNumber`,\r\n" + 
				"        `sales`.`transaction`.`StoreNumber` AS `StoreNumber`,\r\n" + 
				"        CAST(`sales`.`transaction`.`DateTimeOfTransaction`\r\n" + 
				"            AS DATE) AS `DateOfTransaction`,\r\n" + 
				"        CAST(`sales`.`transaction`.`DateTimeOfTransaction`\r\n" + 
				"            AS TIME) AS `TimeOfTransaction`,\r\n" + 
				"        CAST(CAST(`sales`.`transaction`.`DateTimeOfTransaction`\r\n" + 
				"                AS DATE)\r\n" + 
				"            AS CHAR CHARSET UTF8) AS `DateOfTransactionString`,\r\n" + 
				"        CAST(CAST(`sales`.`transaction`.`DateTimeOfTransaction`\r\n" + 
				"                AS TIME)\r\n" + 
				"            AS CHAR CHARSET UTF8) AS `TimeOfTransactionString`,\r\n" + 
				"        WEEKDAY(`sales`.`transaction`.`DateTimeOfTransaction`) AS `WeekdayOfTransaction`,\r\n" + 
				"        MONTH(`sales`.`transaction`.`DateTimeOfTransaction`) AS `MonthOfTransaction`,\r\n" + 
				"        YEAR(`sales`.`transaction`.`DateTimeOfTransaction`) AS `YearOfTransaction`,\r\n" + 
				"        DAYNAME(`sales`.`transaction`.`DateTimeOfTransaction`) AS `WeekdayNameOfTransaction`,\r\n" + 
				"        MONTHNAME(`sales`.`transaction`.`DateTimeOfTransaction`) AS `MonthNameOfTransaction`,\r\n" + 
				"        `sales`.`transactiondetail`.`TotalPrice` AS `TotalPrice`\r\n" + 
				"    FROM\r\n" + 
				"        (((((`sales`.`transaction`\r\n" + 
				"        JOIN `sales`.`transactiondetail` ON ((`sales`.`transaction`.`TransactionID` = `sales`.`transactiondetail`.`TransactionID`)))\r\n" + 
				"        JOIN `hr`.`employee` ON ((`hr`.`employee`.`EmployeeNumber` = `sales`.`transaction`.`EmployeeNumber`)))\r\n" + 
				"        JOIN `product`.`product` ON ((`product`.`product`.`SKU` = `sales`.`transactiondetail`.`SKU`)))\r\n" + 
				"        JOIN `product`.`unit` ON ((`product`.`product`.`UnitID` = `product`.`unit`.`UnitID`)))\r\n" + 
				"        JOIN `product`.`manufacturer` ON ((`product`.`product`.`ManufacturerID` = `product`.`manufacturer`.`ManufacturerID`)));\r\n" + 
				"\r\n" + 
				"END";
		SQLUtils.executeActionQuery(connectionInformation, sql);
		
		// Drop a foreign key - MySQL also drops the corresponding index when you do this in MySQLWorkbench
		sql = "CREATE DEFINER=`root`@`localhost` PROCEDURE `dropforeignkey`()\r\n" + 
				"BEGIN\r\n" + 
				"	ALTER TABLE `sales`.`transactiondetail` DROP FOREIGN KEY `TransactionID`; \r\n" + 
				"	ALTER TABLE `sales`.`transactiondetail` DROP INDEX `TransactionID_idx`;\r\n" + 
				"END";
		SQLUtils.executeActionQuery(connectionInformation, sql);
		
		// Drop a natural key
		sql = "USE `dwattributeoperations`;\r\n" + 
				"DROP procedure IF EXISTS `dropNaturalKey`;\r\n" + 
				"\r\n" + 
				"DELIMITER $$\r\n" + 
				"USE `dwattributeoperations`$$\r\n" + 
				"CREATE DEFINER=`root`@`localhost` PROCEDURE `dropNaturalKey`()\r\n" + 
				"BEGIN\r\n" + 
				"ALTER TABLE `sales`.`transaction` \r\n" + 
				"ADD UNIQUE INDEX `NaturalKey` (`DateTimeOfTransaction` ASC, `LoyaltyNumber` ASC, `StoreNumber` ASC, `EmployeeNumber` ASC);\r\n" + 
				"END$$\r\n" + 
				"\r\n" + 
				"DELIMITER ;\r\n" + 
				"\r\n";
		SQLUtils.executeActionQuery(connectionInformation, sql);
		
		
		
		return false;
	}
	
}
