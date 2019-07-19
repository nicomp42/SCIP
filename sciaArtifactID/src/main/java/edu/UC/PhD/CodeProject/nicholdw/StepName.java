package edu.UC.PhD.CodeProject.nicholdw;

public class StepName {
	private String stepName;
	private String fileName;	// No path
	private String etlStage;
	
	public StepName(String stepName, String fileName, String etlStage) {
		setStepName(stepName);
		setFileName(fileName);
		setEtlStage(etlStage);
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String toString() {
		return stepName + " : " + fileName;
	}
	public String getEtlStage() {
		return etlStage;
	}
	public void setEtlStage(String etlStage) {
		this.etlStage = etlStage;
	}
}
