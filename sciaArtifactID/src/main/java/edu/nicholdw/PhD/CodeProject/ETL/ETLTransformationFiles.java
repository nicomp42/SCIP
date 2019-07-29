/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * ETL Transformation Files
 * @author nicomp
 *
 */
public class ETLTransformationFiles implements Iterable<ETLTransformationFile> {
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
