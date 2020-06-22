package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProjectComponent;

public class ETLProcess extends SchemaChangeImpactProjectComponent {
	private ETLKTRFiles etlKTRFiles;
	private String fileName;
	
	public ETLProcess() {
		super("ETL Process");
		etlKTRFiles = new ETLKTRFiles();
		fileName = "";
	}

	public ETLKTRFiles getEtlKTRFiles() {
		return etlKTRFiles;
	}

	public void setEtlKTRFiles(ETLKTRFiles etlKTRFiles) {
		this.etlKTRFiles = etlKTRFiles;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
