package edu.UC.PhD.CodeProject.nicholdw.caseStudy;

/**
 * A set of queries that can be used in a specific case study
 * @author nicomp
 *
 */
public class CaseStudyEnvironment {
	private CaseStudyQuerys caseStudyQuerys;
	public CaseStudyEnvironment() {
		setCaseStudyQuerys(new CaseStudyQuerys());
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Rename a table column",
				                                                    "ALTER TABLE `storeinspection`.`storeinspection`" +  
		                                                            " CHANGE COLUMN `InspectionDateTime` `InspectionDateAndTime` DATETIME NOT NULL ;"));

		
		
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Drop a table column",
				                                                    "ALTER TABLE `storeinspection`.`storeinspection`" + 
				                                                    "DROP COLUMN `InspectionDateTime`," + 
				                                                    "DROP INDEX `Natural` ," + 
				                                                    "ADD UNIQUE INDEX `Natural` (`StoreNumber` ASC, `EmployeeNumber` ASC) VISIBLE;"));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Change data type of table column",
																	"ALTER TABLE `storeinspection`.`storeinspection`" +  
																	"CHANGE COLUMN `InspectionDateTime` `InspectionDateTime` FLOAT NOT NULL ;" ));

		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Delete a foreign key",
																	"ALTER TABLE `flight`.`tflight` " +
																	"DROP FOREIGN KEY `tFlight_tPilot`;"));
		
		caseStudyQuerys.getCaseStudyQuerys().add(new CaseStudyQuery("Rename a table",
																	"ALTER TABLE `flight`.`tflight`" + 
																	"RENAME TO  `flight`.`tflightx`;"  ));
		
//		"ALTER TABLE `flight`.`tflight` "
//		"DROP INDEX `tFlight_tPilot_idx` ;"
		

	}
	public CaseStudyQuerys getCaseStudyQuerys() {
		return caseStudyQuerys;
	}
	public void setCaseStudyQuerys(CaseStudyQuerys caseStudyQuerys) {
		this.caseStudyQuerys = caseStudyQuerys;
	}
}
