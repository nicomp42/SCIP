package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

/**
 * A set of queries that can be used in a specific case study
 * A schema in MySQL is called a database.
 * @author nicomp
 *
 */
public class CaseStudyEnvironment {
	private CaseStudyQuerys caseStudyQuerys;
	/***
	 * Constructor. Init's the query objects that will be tested in this test case.
	 */
	public CaseStudyEnvironment() {
		setCaseStudyQuerys(new CaseStudyQuerys());
		/* Rename a schema: there's no single SQL command to do this. 
		 * You have to create a new one and copy artifacts into it.
		 */
		
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a Schema",
																	"DROP DATABASE `dw`;"));
		
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Rename a table",
																	"ALTER TABLE `flight`.`tflight`" + 
																	"RENAME TO  `flight`.`tflightx`;"  ));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Rename a table column",
				                                                    "ALTER TABLE `storeinspection`.`storeinspection`" +  
		                                                            " CHANGE COLUMN `InspectionDateTime` `InspectionDateAndTime` DATETIME NOT NULL ;"));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a table column",
																	"ALTER TABLE `flight`.`tflight DROP COLUMN `DepartureDate`"));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a table column",
				                                                    "ALTER TABLE `storeinspection`.`storeinspection`" + 
				                                                    "DROP COLUMN `InspectionDateTime`," + 
				                                                    "DROP INDEX `Natural` ," + 
				                                                    "ADD UNIQUE INDEX `Natural` (`StoreNumber` ASC, `EmployeeNumber` ASC) VISIBLE;"));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Change data type of table column",
																	"ALTER TABLE `storeinspection`.`storeinspection`" +  
																	"CHANGE COLUMN `InspectionDateTime` `InspectionDateTime` FLOAT NOT NULL ;" ));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Remove as a foreign key",
																	"ALTER TABLE `flight`.`tflight` " +
																	"DROP FOREIGN KEY `tFlight_tPilot`;"));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Delete as a primary key",
																	"ALTER TABLE `hr`.`employee` DROP PRIMARY KEY;"));
		
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a view",
																	"DROP VIEW `hr`.`vtest`"));
//		The entire view is redefined. This is problematic		
//		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Rename a view attribute",
//																	"DROP VIEW `hr`.`vtest`"));
		
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a view",
																	"DROP VIEW `hr`.`vtest`"));
//		Alter a view in MySQL is running CREATE VIEW with the same name as an existing view
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Alter a view ",
																	"CREATE OR REPLACE VIEW `hr`.`vtest` AS\r\n" + 
																	"SELECT \r\n" + 
																	"`hr`.`employee`.`EmployeeID` AS `EmployeeIDX`\r\n" + 
																	"FROM\r\n" + 
																	"`hr`.`employee`" + 
																	""));
		
		
	}
	public CaseStudyQuerys getCaseStudyQuerys() {
		return caseStudyQuerys;
	}
	public void setCaseStudyQuerys(CaseStudyQuerys caseStudyQuerys) {
		this.caseStudyQuerys = caseStudyQuerys;
	}
}
