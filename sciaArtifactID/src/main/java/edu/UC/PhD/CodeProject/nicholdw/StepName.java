package edu.UC.PhD.CodeProject.nicholdw;

public class StepName {
	private String stepName;
	private String fileName;	// No path
	
	public StepName(String stepName, String fileName) {
		setStepName(stepName);
		setFileName(fileName);
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
}
