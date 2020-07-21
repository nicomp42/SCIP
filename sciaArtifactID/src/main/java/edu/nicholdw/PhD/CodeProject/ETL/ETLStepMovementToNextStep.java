package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * Model of how data should move between two ETLStep objects based on the type of each step
 * @author nicomp
 *
 */
public class ETLStepMovementToNextStep {
//	private ETLStepType etlStepType;		// The step type we are starting from
//	private ETLStepType etlStepTypeNext; 	// The step type we would be moving to
	
/*	public ETLStepMovementToNextStep(ETLStepType etlStepType, ETLStepType etlStepTypeNext) {
		this.etlStepType = etlStepType;
		this.etlStepTypeNext = etlStepTypeNext;
	} */
	public static void FindTheMove(ETLStep etlStep, ETLStep etlStepNext) { 
		Log.logProgress("ETLStepMovementToNextStep.findTheMove(): Computing movement logic for " + etlStep.getEtlStepType().getEtlStepType() + " to " + etlStepNext.getEtlStepType().getEtlStepType());
		// If you add/edit a step type you must update the ETLStepType.etlStepTypes list
		switch (etlStepNext.getEtlStepType().getEtlStepType()) {
		
			case "constant":
				processETLStepTypeConstant(etlStep, etlStepNext);
				break;
			case "append":
				processETLStepTypeAppend(etlStep, etlStepNext);
				break;
			case "insertupdate": 
				processETLStepTypeInsertUpdate(etlStep, etlStepNext);
				break;
			case "tableinput":
				processETLStepTypeTableInput(etlStep, etlStepNext);
				break;
			case "filterrows":
				processETLStepTypeFilterRows(etlStep, etlStepNext);
				break;
			case "dblookup":
				processETLStepTypeDBLookup(etlStep, etlStepNext);
				break;
			case "selectvalues":
				processETLStepTypeSelectValues(etlStep, etlStepNext);
				break;
			default:
				Log.logError("ETLStepMovementToNextStep.findTheMove(): Invalid ETL Step Type (" + etlStep.getEtlStepType().getEtlStepType() + ")");
		}
	}
	private static void processETLStepTypeConstant(ETLStep etlStep, ETLStep etlStepNext) {
		Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeConstant(" + etlStep.getEtlStepType().getEtlStepType() + ", " + etlStepNext.getEtlStepType().getEtlStepType() + ")");
		if (!etlStepNext.getEtlStepType().isPassThroughStep()) {
			switch (etlStep.getEtlStepType().getEtlStepType()) {
				
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
					Log.logError("ETLStepMovementToNextStep.processETLStepTypeConstant(): Invalid ETL Step Type TO (" + etlStepNext.getEtlStepType().getEtlStepType() + ")");
			}
		} else {
			Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeConstant(): it's a pass-through. Nothing to do here.");
		}
	}
	public static void processETLStepTypeAppend(ETLStep etlStep, ETLStep etlStepNext) {
		switch (etlStepNext.getEtlStepType().getEtlStepType()) {
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
				Log.logError("ETLStepMovementToNextStep.processETLStepTypeAppend(): Invalid ETL Step Type TO (" + etlStepNext.getEtlStepType() + ")");
		}
	}
	public static void processETLStepTypeInsertUpdate(ETLStep etlStep, ETLStep etlStepNext) {
		switch (etlStepNext.getEtlStepType().getEtlStepType()) {
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
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeInsertUpdate(): Invalid ETL Step Type TO (" + etlStepNext.getEtlStepType().getEtlStepType() + ")");
	}
	}
	public static void processETLStepTypeTableInput(ETLStep etlStep, ETLStep etlStepNext) {
		switch (etlStepNext.getEtlStepType().getEtlStepType()) {
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
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeTableInput(): Invalid ETL Step Type TO (" + etlStepNext.getEtlStepType() + ")");
	}
	}
	public static void processETLStepTypeFilterRows(ETLStep etlStep, ETLStep etlStepNext) {
		switch (etlStepNext.getEtlStepType().getEtlStepType()) {
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
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeFilterRows(): Invalid ETL Step Type TO (" + etlStepNext.getEtlStepType().getEtlStepType() + ")");
	}
	}
	public static void processETLStepTypeDBLookup(ETLStep etlStep, ETLStep etlStepNext) {
		switch (etlStepNext.getEtlStepType().getEtlStepType()) {
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
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeDBLookup(): Invalid ETL Step Type TO (" + etlStepNext.getEtlStepType().getEtlStepType() + ")");
	}
	}
	public static void processETLStepTypeSelectValues(ETLStep etlStep, ETLStep etlStepNext) {
		switch (etlStepNext.getEtlStepType().getEtlStepType()) {
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
			Log.logError("ETLStepMovementToNextStep.processETLStepTypeSelectValues(): Invalid ETL Step Type TO (" + etlStepNext.getEtlStepType().getEtlStepType() + ")");
	}
	}
}
