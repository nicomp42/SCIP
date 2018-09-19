package edu.nicholdw.PhD.CodeProject.ETL;
/***
 * A step in the ETL process. Abstracted, but modeled on Pentaho for now.
 * @author nicomp
 *
 */
public class ETLStep {
	private String stepType;

	
	
	
	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}
	
}
