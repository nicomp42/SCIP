/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;
/**
 * A "Hop" from a Pentaho Transformation File. Connects two steps. Has a direction: from -> to
 * @author nicomp
 *
 */
public class ETLHop {
	private String fromStepName;
	private String toStepName;
	private Boolean enabled;
	private String fileName;	// No path, just the filename
	
	public ETLHop(String from, String to, Boolean enabled, String fileName) {
		setFromStepName(from);
		setToStepName(to);
		setEnabled(enabled);
		setFileName(fileName);
	}
	public ETLHop(ETLHop etlHop) {
		this.setFromStepName(etlHop.getFromStepName());
		this.setToStepName(etlHop.getToStepName());
		this.setEnabled(etlHop.getEnabled());
		this.setFileName(etlHop.getFileName());
	}
	public String toString() {
		return "from: " + getFromStepName() 
		      + " to: " + getToStepName()
		      + " enabled: " + getEnabled()
		      + " fileName: " + getFileName();
	}
	public String getFromStepName() {
		return fromStepName;
	}
	public void setFromStepName(String from) {
		this.fromStepName = from.trim();
	}
	public String getToStepName() {
		return toStepName;
	}
	public void setToStepName(String to) {
		this.toStepName = to.trim();
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getFileName() {
		return fileName.trim();
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
