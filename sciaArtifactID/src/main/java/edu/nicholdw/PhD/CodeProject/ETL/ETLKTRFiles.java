/* Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A set of ETL KTR files
 * @author nicomp
 *
 */
public class ETLKTRFiles implements Iterable<ETLKTRFile>  {

	private ArrayList<ETLKTRFile> etlKTRFiles;

	public ETLKTRFiles() {
		etlKTRFiles = new ArrayList<ETLKTRFile>();
	}
	
	public ArrayList<ETLKTRFile> getEtlKTRFiles() {
		return etlKTRFiles;
	}

	public void setEtlKTRFiles(ArrayList<ETLKTRFile> etlKTRFiles) {
		this.etlKTRFiles = etlKTRFiles;
	}
	public void addETLKTRFile(ETLKTRFile etlKTRFile) {
		etlKTRFiles.add(etlKTRFile);
	}
	@Override
	public Iterator<ETLKTRFile> iterator() {
		return etlKTRFiles.iterator();
	}
	
}
