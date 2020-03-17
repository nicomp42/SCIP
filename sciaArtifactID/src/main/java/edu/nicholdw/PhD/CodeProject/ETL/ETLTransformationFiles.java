/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
/**
 * ETL Transformation Files
 * @author nicomp
 *
 */
public class ETLTransformationFiles implements Iterable<ETLTransformationFile>, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 235333715126352906L;
	public void loadETLTransformationFileNames(String filePath) {
		Log.logProgress("ETLTransformationFiles.LoadETLTransformationFileNames() Loading from " + filePath);
		try {
			final File folder = new File(filePath);
		    for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		        	this.add(new ETLTransformationFile(fileEntry.getAbsolutePath()));
		        } else {
		            System.out.println(fileEntry.getName());
		        }
		    }
		} catch (Exception ex) {
			Log.logError("ETLTransformationFiles.LoadETLTransformationFileNames(): " + ex.getLocalizedMessage());
		}
	}
	private ArrayList<ETLTransformationFile> etlTransformationFiles;
	public ETLTransformationFiles() {
		etlTransformationFiles = new ArrayList<ETLTransformationFile>();
	}
	@Override
	public Iterator<ETLTransformationFile> iterator() {
		Iterator<ETLTransformationFile> myIterator = etlTransformationFiles.iterator();
        return myIterator;
	}
	public void add(ETLTransformationFile etlTransformationFile) {
		etlTransformationFiles.add(new ETLTransformationFile(etlTransformationFile));
	}
	public void updateETLStageInETLTransformationFile(String fileName, String etlStage) {
		for (ETLTransformationFile etlTransformationFile: etlTransformationFiles) {
			if (etlTransformationFile.getFileName().trim().toUpperCase().equals(fileName.toUpperCase())) {
				etlTransformationFile.setEtlStageNumber(ETLTransformationFile.lookupETLStageNumber(etlStage));
				break;
			}
		}
	}
	/**
	 * Remove all the ETL Transformation files in the object
	 */
	public void deleteAll() {
		etlTransformationFiles.clear();
	}
}
