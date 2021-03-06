/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProject;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProjectComponent;

public class ETLProcess extends SchemaChangeImpactProjectComponent {

	private static final long serialVersionUID = -4444786651236261083L;
	private ETLKJBFiles etlKJBFiles;

	public ETLProcess() {
		super("ETL Process");
		etlKJBFiles = new ETLKJBFiles();
	}
	public ETLKJBFiles getEtlKJBFiles() {
		return etlKJBFiles;
	}
	public void setEtlKJBFiles(ETLKJBFiles etlKJBFiles) {
		this.etlKJBFiles = etlKJBFiles;
	}
	/**
	 * Crunch the ETL steps into something that can be graphed.
	 * @param scip The Schema Change Impact project to be dealt with
	 */
	public void processData(SchemaChangeImpactProject scip) {
		// Read the Transformation files for each Job file
		for (ETLKJBFile etlKJBFile: etlKJBFiles) {
			etlKJBFile.readETLKTRFiles();
			for (ETLKTRFile etlKTRFile: etlKJBFile.getEtlKTRFiles()) {
				etlKTRFile.processETLKTRFile();
			}
		}
	}
	/**
	 * Hopefully ETLProcess.processData() has already been called. 
	 * @param scip The Schema Change Impact project to be dealt with
	 */
	public void addNodesToGraph(SchemaChangeImpactProject scip) {
		// Read the Transformation files for each Job file
		for (ETLKJBFile etlKJBFile: etlKJBFiles) {
			etlKJBFile.readETLKTRFiles();
			for (ETLKTRFile etlKTRFile: etlKJBFile.getEtlKTRFiles()) {
				etlKTRFile.createGraph(scip);
			}
		}
	}
/*	public void processDataAndGenerateGraph(SchemaChangeImpactProject scip) {
		processData(scip);
		addNodesToGraph(scip);
	}
*/
}
