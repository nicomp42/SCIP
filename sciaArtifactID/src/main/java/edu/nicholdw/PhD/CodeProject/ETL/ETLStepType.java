/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/**
 * Characterize a type of step in a Pentaho transformation
 * @author nicomp
 *
 */
public class ETLStepType {
//	@SuppressWarnings("unchecked")
	public static ArrayList<String> etlStepTypes;
	static {
		// If you add/edit a step type here you must update  ETLStepMovement.findTheMove()
		etlStepTypes = new ArrayList<String>();
		etlStepTypes.add("constant"); 
		etlStepTypes.add("append"); 
		etlStepTypes.add("insertupdate"); 
		etlStepTypes.add("tableinput");
		etlStepTypes.add("filterrows");
		etlStepTypes.add("dblookup");
		etlStepTypes.add("selectvalues");
		etlStepTypes.add("ifnull");
		etlStepTypes.add("dimensionlookup");
		etlStepTypes.add("valuemapper");
	}
	
	private String etlStepType;

	/**
	 * Copy Constructor
	 * @param etlStepType
	 */
	public ETLStepType(ETLStepType etlStepType) {
		setEtlStepType(etlStepType.getEtlStepType()); 
	}
	
	/**
	 * Is the type of step valid? Does it appear in the known list of step types?
	 * @param etlStepType
	 * @return True if the ETL Step Type is recognized, false otherwise
	 */
	public static Boolean isValid(String etlStepType) {
		Boolean result = true;
		if (!etlStepTypes.contains(etlStepType.toLowerCase().trim())) {
			result = false;
			Log.logError("ETLStepType.isValid(etlStepType): Unrecognized ETL Step Type: " + etlStepType);
		}
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
			Log.logError("ETLStepType.setEtlStepType(): Invalid Step Type (" + etlStepType.toLowerCase().trim() + ")");
		}
	}
	/***
	 * Is this a step we don't actually care about? 
	 * @return True if this step is a nothing burger as far as we're concerned. False otherwise or on error.
	 */
	public boolean isPassThroughStep() {
		Boolean passThroughStep = false;
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
			case "ifnull":
				passThroughStep = true;
				break;
			case "dimensionlookup":
				passThroughStep = true;
				break;
			case "valuemapper":
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
