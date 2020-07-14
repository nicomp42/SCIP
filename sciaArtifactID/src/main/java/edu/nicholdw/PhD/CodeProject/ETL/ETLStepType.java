package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.List;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Characterize a type of step in a Pentaho transformation
 * @author nicomp
 *
 */
public class ETLStepType {
//	@SuppressWarnings("unchecked")
	public static ArrayList<String> etlStepTypes;
	static {
		etlStepTypes.add("constant"); 
		etlStepTypes.add("append"); 
		etlStepTypes.add("insertupdate"); 
		etlStepTypes.add("tableinput");
		etlStepTypes.add("filterrows");
		etlStepTypes.add("dblookup");
		etlStepTypes.add("selectvalues");
	}
	
	private String etlStepType;

	/**
	 * Copy Constructor
	 * @param etlStepType
	 */
	public ETLStepType(ETLStepType etlStepType) {
		this.etlStepType = etlStepType.getEtlStepType(); 
	}
	
	/**
	 * Is the type of step valid? Does it appear in the know list of step types?
	 * @param etlStepType
	 * @return True if the ETL Step Type is recognized, false otherwise
	 */
	public static Boolean isValid(String etlStepType) {
		Boolean result = true;
		if (!etlStepTypes.contains(etlStepType)) {result = false;}
		return result;
	}
	public ETLStepType(String etlStepType) {
		setEtlStepType(etlStepType);
	}
	public String getEtlStepType() {
		return etlStepType;
	}

	public void setEtlStepType(String etlStepType) {
		if (isValid(etlStepType)) {
			this.etlStepType = etlStepType;
		} else {
			Log.logError("ETLStepType.setEtlStepType(): Invalid Step Type (" + etlStepType.toLowerCase().trim());
		}
	}
	public boolean isPassThroughStep() {
		Boolean passThroughStep = true;
		try {
			switch (etlStepType.toLowerCase()) {
			case "constant":
				passThroughStep = true;
				break;
			case "append":
				passThroughStep = true;
				break;
			case "insertupdate":
				passThroughStep = false;
				break;
			case "tableinput":
				passThroughStep = false;
				break;
			case "filterrows":
				passThroughStep = true;
				break;
			case "dblookup":
				passThroughStep = true;
				break;
			case "selectvalues":
				passThroughStep = true;
				break;
			default:
				Log.logError("ETLStep.isPassThroughStep(): Unrecognized step type (" + etlStepType + ")" );
			}
		} catch (Exception ex) {
			Log.logError("ETLStep.isPassThroughStep(): ", ex);		
		}
		return passThroughStep;
	}
}
