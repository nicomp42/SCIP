/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import edu.UC.PhD.CodeProject.nicholdw.schemaChangeImpactProject.SchemaChangeImpactProjectComponent;

public class ETLKJBFiles extends SchemaChangeImpactProjectComponent implements Serializable, Iterable<ETLKJBFile> {

	private static final long serialVersionUID = 7119482959432683270L;
	private ArrayList<ETLKJBFile> etlKJBFiles;	
	
	public ETLKJBFiles() {
		super("ETL KJB Files");
		etlKJBFiles = new ArrayList<ETLKJBFile>();
	}

	public ArrayList<ETLKJBFile> getEtlKJBFiles() {
		return etlKJBFiles;
	}

	public void setEtlKJBFiles(ArrayList<ETLKJBFile> etlKJBFiles) {
		this.etlKJBFiles = etlKJBFiles;
	}

	@Override
	public Iterator<ETLKJBFile> iterator() {
		return etlKJBFiles.iterator();
	}
	public void add(ETLKJBFile etlKJBFile) {
		etlKJBFiles.add(etlKJBFile);
	}
	/***
	 * Permanently erase any KJB Files currently in the object
	 */
	public void clear() {
		etlKJBFiles.clear();
	}
}
