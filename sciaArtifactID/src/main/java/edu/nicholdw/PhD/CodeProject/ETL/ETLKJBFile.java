package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProjectComponent;

/***
 * A Pentaho .kjb file.
 * A .kjb file is a set of .ktr files and a path through them.
 * @author nicomp
 *
 */
public class ETLKJBFile extends SchemaChangeImpactProjectComponent {
	private static final long serialVersionUID = 4216338253621622243L;
	private ETLKTRFiles etlKTRFiles;
	private String fileName;
	private ETLHops etlHops;
	private ETLJobs etlJobs;
	
	public ETLKJBFile(String fileName) {
		super("ETL KJB File");
		etlKTRFiles = new ETLKTRFiles();
		setFileName(fileName);
	}
	public ETLKJBFile() {
		super("ETL KJB File");
		etlKTRFiles = new ETLKTRFiles();
		fileName = "";
	}
	public boolean loadHops() throws Exception {
		boolean status = true;
		if (fileName.trim().length() > 0) {
			try {
				XMLParser myXMLParser = new XMLParser();
				myXMLParser.getETLHopsFromPentahoKJBFile(fileName, etlHops);
			} catch (Exception ex) {
				Log.logError("ETLKJBFile.loadHops()", ex);
				status = false;
			}
		} else {
			throw new Exception("ETLKJBFile.loadHops(): no filename provided in the object");
		}
		return status;
	}
	public void readETLKTRFiles() {
		XMLParser myXMLParser = new XMLParser();
		myXMLParser.getETLKTRFiles(fileName, etlKTRFiles);		
	}
	public boolean loadJobs() throws Exception {
		boolean status = true;
		if (fileName.trim().length() > 0) {
			try {
				XMLParser myXMLParser = new XMLParser();
				myXMLParser.getETLJobs(fileName, etlJobs);
			} catch (Exception ex) {
				Log.logError("ETLKJBFile.loadJobs()", ex);
				status = false;
			}
		} else {
			throw new Exception("ETLKJBFile.loadJobs(): no filename provided in the object");
		}
		return status;
	}
	
	
	
	public ETLKTRFiles getEtlKTRFiles() {
		return etlKTRFiles;
	}

	public void setEtlKTRFiles(ETLKTRFiles etlKTRFiles) {
		this.etlKTRFiles = etlKTRFiles;
	}
	/***
	 * @return File name on the storage device, path included
	 */
	public String getFileName() {
		return fileName;
	}
	/***
	 * File name on the storage device, path included
	 * @param fileName With path
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ETLHops getEtlHops() {
		return etlHops;
	}

	public void setEtlHops(ETLHops etlHops) {
		this.etlHops = etlHops;
	}

	public ETLJobs getEtlJobs() {
		return etlJobs;
	}

	public void setEtlJobs(ETLJobs etlJobs) {
		this.etlJobs = etlJobs;
	}
	
	public String toString() {
		String myString = "";
		try {
			myString = getFileName();
		} catch (Exception ex) {}
		return myString;
	}

}
