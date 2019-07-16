package edu.nicholdw.PhD.CodeProject.ETL;

public class ETLTransformationFile {
	private String fileName;	// No Path. This is just a file name.
	public enum enumETLType {ODS_IDS, IDS_DW, Unknown};
	private enumETLType etlType;
	
	public ETLTransformationFile(String fileName) {
		setFileName(fileName);
	}
	/***
	 * Copy Constructor
	 * @param etlTransformationFile
	 */
	public ETLTransformationFile(ETLTransformationFile etlTransformationFile) {
		setFileName(etlTransformationFile.getFileName());
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public enumETLType getEtlType() {
		return etlType;
	}
	public void setEtlType(enumETLType etlType) {
		this.etlType = etlType;
	}
}
