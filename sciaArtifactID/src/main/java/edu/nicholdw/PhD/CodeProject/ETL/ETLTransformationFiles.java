package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.Iterator;

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
			if (etlTransformationFile.getFileName().trim().toUpperCase().equals(fileName)) {
				etlTransformationFile.setEtlStageNumber(ETLTransformationFile.lookupETLStageNumber(etlStage));
				break;
			}
		}
	}
}
