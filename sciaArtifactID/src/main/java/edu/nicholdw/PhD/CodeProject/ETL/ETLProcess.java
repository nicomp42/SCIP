/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

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

	public void process() {
		// Read the Transformation files for each Job file
		for (ETLKJBFile etlKJBFile: etlKJBFiles) {
			etlKJBFile.readETLKTRFiles();
		}
	}

}
