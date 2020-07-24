/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
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
	 * Constructor. Initializes the query objects that will be tested in this test case.
	 */
	public CaseStudyEnvironment() {
		setCaseStudyQuerys(new CaseStudyQuerys());
	}
	public void SetUpCaseStudy02() {
		caseStudyQuerys.getCaseStudyQuerys().clear();
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a column in a table.",
																	"ALTER TABLE `sakila_ids`.`actor` DROP COLUMN `first_name`;\r\n"
																	));
	}
	public void SetUpCaseStudy01() {
		caseStudyQuerys.getCaseStudyQuerys().clear();
		/* Rename a schema: there's no single SQL command to do this. 
		 * You have to create a new one and copy artifacts into it.
		 */
		
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a Schema",
																	"DROP DATABASE `reconciled`;"));
		
		// This SQL parses OK but still throws off a parser error. "line 1:66 mismatched input '.' expecting {<EOF>, '--'}"
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Rename a table",
																	"ALTER TABLE `reconciled`.`storeinspection` " + 
																	"RENAME TO  `reconciled`.`storeinspectionX`;"  )); 

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Rename a table column",
				                                                    "ALTER TABLE `storeinspection`.`storeinspection`" +  
		                                                            " CHANGE COLUMN `InspectionDateTime` `InspectionDateAndTime` DATETIME NOT NULL ;"));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Rename 2 table columns",
																	"ALTER TABLE `storeinspection`.`storeinspection` " + 
																	" CHANGE COLUMN `StoreNumber` `StoreNumberX` VARCHAR(10) NOT NULL ," + 
																	" CHANGE COLUMN `InspectionNotes` `InspectionNotesX` LONGTEXT NULL DEFAULT NULL ;"));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a table column",
																	"ALTER TABLE `reconciled`.`storeinspection` DROP COLUMN `InspectionDateTime`"));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop two table columns",
																	"ALTER TABLE `reconciled`.`storeinspection` " + 
																	"DROP COLUMN `InspectionDateTime`," + 
																	"DROP COLUMN `employeelastname`;"));


		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Change data type of table column",
																	"ALTER TABLE `storeinspection`.`storeinspection`" +  
																	"CHANGE COLUMN `InspectionDateTime` `InspectionDateTime` FLOAT NOT NULL ;" ));


		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Remove as a foreign key",
																	"ALTER TABLE `reconciled`.`storeinspection` " +
																	"DROP FOREIGN KEY `FK_EmployeeNumber`;"));


		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Delete as a primary key",
																	"ALTER TABLE `hr`.`employee` DROP PRIMARY KEY;"));
		
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a view",
																	"DROP VIEW `dw`.`vweeklystoreinspectionsbyemployee`;"));
																	
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Alter a view (with \"OR REPLACE\")",
																	"CREATE OR REPLACE " + 
																	"VIEW " +
																	"`dw`.`vweeklystoreinspectionsbyemployee` AS" + 
																	"    SELECT " + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`EmployeeName` AS `EmployeeName`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`StoreNumber` AS `StoreNumber`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Monday` AS `Monday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Tuesday` AS `Tuesday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Wednesday` AS `Wednesday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Thursday` AS `Thursday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Friday` AS `Friday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Saturday` AS `Saturday`" + 
																	"    FROM" + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`;" 
																	));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Alter a view (without \"OR REPLACE\")",
																	"CREATE " + 
																	"    ALGORITHM = UNDEFINED " + 
																	"    DEFINER = `root`@`localhost` " + 
																	"    SQL SECURITY DEFINER " + 
																	"VIEW " + 
																	"`dw`.`vweeklystoreinspectionsbyemployee` AS" + 
																	"    SELECT " + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`EmployeeName` AS `EmployeeName`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`StoreNumber` AS `StoreNumber`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Monday` AS `Monday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Tuesday` AS `Tuesday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Wednesday` AS `Wednesday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Thursday` AS `Thursday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Friday` AS `Friday`," + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`.`Saturday` AS `Saturday`" + 
																	"    FROM" + 
																	"        `dw`.`weeklyinspectionsbyemployeeandstore`;" 
																	));
		
	}
	public CaseStudyQuerys getCaseStudyQuerys() {
		return caseStudyQuerys;
	}
	public void setCaseStudyQuerys(CaseStudyQuerys caseStudyQuerys) {
		this.caseStudyQuerys = caseStudyQuerys;
	}
}
