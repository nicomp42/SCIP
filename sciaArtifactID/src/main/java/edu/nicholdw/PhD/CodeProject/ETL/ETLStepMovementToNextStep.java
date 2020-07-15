package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * Model of how data should move between ETLStepType objects
 * @author nicomp
 *
 */
public class ETLStepMovementToNextStep {
	private ETLStepType etlStepType;	// The step type we are starting from
	private ETLStepType etlStepTypeTo; // The step type we would be moving to

	public void FindTheMove() { 
		Log.logProgress("ETLStepMovementToNextStep.findTheMove(): Computing movement logic for " + etlStepType.getEtlStepType() + " to " + etlStepTypeTo.getEtlStepType());
		// If you add/edit a step type you must update the ETLStepType.etlStepTypes list
		switch (etlStepType.getEtlStepType()) {
		
			case "constant":
				processETLStepTypeConstant();
				break;
			case "append":
				processETLStepTypeAppend();
				break;
			case "insertupdate": 
				processETLStepTypeInsertUpdate();
				break;
			case "tableinput":
				processETLStepTypeTableInput();
				break;
			case "filterrows":
				processETLStepTypeFilterRows();
				break;
			case "dblookup":
				processETLStepTypeDBLookup();
				break;
			case "selectvalues":
				processETLStepTypeSelectValues();
				break;
			default:
				Log.logError("ETLStepMovementToNextStep.findTheMove(): Invalid ETL Step Type (" + etlStepType.getEtlStepType() + ")");
		}
	}
	private void processETLStepTypeConstant() {
		switch (etlStepTypeTo.getEtlStepType()) {
			
			case "constant":
				
				break;
			case "append":
				
				break;
			case "insertupdate": 
				
				break;
			case "tableinput":
				
				break;
			case "filterrows":
				
				break;
			case "dblookup":
				
				break;
			case "selectvalues":
				
				break;
			default:
				Log.logError("ETLStepMovementToNextStep.processETLStepTypeConstant(): Invalid ETL Step Type TO (" + etlStepTypeTo.getEtlStepType() + ")");
		}
	}
	public void processETLStepTypeAppend() {
		switch (etlStepTypeTo.getEtlStepType()) {
			case "constant":
				
				break;
			case "append":
				
				break;
			case "insertupdate": 
				
				break;
			case "tableinput":
				
				break;
			case "filterrows":
				
				break;
			case "dblookup":
				
				break;
			case "selectvalues":
				
				break;
			default:
				Log.logError("ETLStepMovementToNextStep.processETLStepTypeAppend(): Invalid ETL Step Type TO (" + etlStepTypeTo.getEtlStepType() + ")");
		}
	}
	public void processETLStepTypeInsertUpdate() {
		switch (etlStepTypeTo.getEtlStepType()) {
		case "constant":
			
			break;
		case "append":
			
			break;
		case "insertupdate": 
			
			break;
		case "tableinput":
			
			break;
		case "filterrows":
			
			break;
		case "dblookup":
			
			break;
		case "selectvalues":
			
			break;
		default:
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeInsertUpdate(): Invalid ETL Step Type TO (" + etlStepTypeTo.getEtlStepType() + ")");
	}
	}
	public void processETLStepTypeTableInput() {
		switch (etlStepTypeTo.getEtlStepType()) {
		case "constant":
			
			break;
		case "append":
			
			break;
		case "insertupdate": 
			
			break;
		case "tableinput":
			
			break;
		case "filterrows":
			
			break;
		case "dblookup":
			
			break;
		case "selectvalues":
			
			break;
		default:
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeTableInput(): Invalid ETL Step Type TO (" + etlStepTypeTo.getEtlStepType() + ")");
	}
	}
	public void processETLStepTypeFilterRows() {
		switch (etlStepTypeTo.getEtlStepType()) {
		case "constant":
			
			break;
		case "append":
			
			break;
		case "insertupdate": 
			
			break;
		case "tableinput":
			
			break;
		case "filterrows":
			
			break;
		case "dblookup":
			
			break;
		case "selectvalues":
			
			break;
		default:
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeFilterRows(): Invalid ETL Step Type TO (" + etlStepTypeTo.getEtlStepType() + ")");
	}
	}
	public void processETLStepTypeDBLookup() {
		switch (etlStepTypeTo.getEtlStepType()) {
		case "constant":
			
			break;
		case "append":
			
			break;
		case "insertupdate": 
			
			break;
		case "tableinput":
			
			break;
		case "filterrows":
			
			break;
		case "dblookup":
			
			break;
		case "selectvalues":
			
			break;
		default:
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeDBLookup(): Invalid ETL Step Type TO (" + etlStepTypeTo.getEtlStepType() + ")");
	}
	}
	public void processETLStepTypeSelectValues() {
		switch (etlStepTypeTo.getEtlStepType()) {
		case "constant":
			
			break;
		case "append":
			
			break;
		case "insertupdate": 
			
			break;
		case "tableinput":
			
			break;
		case "filterrows":
			
			break;
		case "dblookup":
			
			break;
		case "selectvalues":
			
			break;
		default:
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeSelectValues(): Invalid ETL Step Type TO (" + etlStepTypeTo.getEtlStepType() + ")");
	}
	}
}
