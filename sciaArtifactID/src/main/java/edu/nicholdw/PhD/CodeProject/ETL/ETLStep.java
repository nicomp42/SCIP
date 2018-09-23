package edu.nicholdw.PhD.CodeProject.ETL;
/***
 * A step in the ETL process. Abstracted, but modeled on Pentaho for now.
 * @author nicomp
 *
 */
public class ETLStep {
	private String stepType;
	private String stepName;
	
	public ETLStep(ETLStep etlStep) {
		setStepName(etlStep.getStepName());
		setStepType(etlStep.getStepType());
	}
	
	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
}
