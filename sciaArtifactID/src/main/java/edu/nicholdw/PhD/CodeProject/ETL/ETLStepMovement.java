/* Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * Model of how data should move between two ETLStep objects based on the type of each step
 * @author nicomp
 *
 */
public class ETLStepMovement {
//	private ETLStepType etlStepType;		// The step type we are starting from
//	private ETLStepType etlStepTypeNext; 	// The step type we would be moving to
	
/*	public ETLStepMovementToNextStep(ETLStepType etlStepType, ETLStepType etlStepTypeNext) {
		this.etlStepType = etlStepType;
		this.etlStepTypeNext = etlStepTypeNext;
	} */
	public static void FindTheMove(ETLStep etlStep, ETLStep etlStepNext) { 
		Log.logProgress("ETLStepMovementToNextStep.findTheMove(): Computing movement logic for " + etlStep.getEtlStepType().getEtlStepType() + " to " + etlStepNext.getEtlStepType().getEtlStepType());
		// If you add/edit a step type you must update the ETLStepType.etlStepTypes list
		switch (etlStepNext.getEtlStepType().getEtlStepType().trim().toLowerCase()) {

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
	/**
	 * We are moving to a "constant' step type.
	 * @param etlStep
	 * @param etlStepNext
	 */
	private static void processETLStepTypeConstant(ETLStep etlStep, ETLStep etlStepNext) {
		Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeConstant(" + etlStep.getEtlStepType().getEtlStepType() + ", " + etlStepNext.getEtlStepType().getEtlStepType() + ")");
		if (!etlStepNext.getEtlStepType().isPassThroughStep()) {
		// Where did we come from?
			switch (etlStep.getEtlStepType().getEtlStepType().trim().toLowerCase()) {
				
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
	/**
	 * We are moving to an "append' step type.
	 * @param etlStep
	 * @param etlStepNext
	 */
	public static void processETLStepTypeAppend(ETLStep etlStep, ETLStep etlStepNext) {
		Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeApppend(" + etlStep.getEtlStepType().getEtlStepType() + ", " + etlStepNext.getEtlStepType().getEtlStepType() + ")");
		if (!etlStepNext.getEtlStepType().isPassThroughStep()) {
		// Where did we come from?
			switch (etlStep.getEtlStepType().getEtlStepType().trim().toLowerCase()) {
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
		} else {
			Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeApppend(): it's a pass-through. Nothing to do here.");
		}
	}
	/**
	 * We are moving to an "Insert/Update' step type.
	 * @param etlStep
	 * @param etlStepNext
	 */
	public static void processETLStepTypeInsertUpdate(ETLStep etlStep, ETLStep etlStepNext) {
		Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeInsertUpdate(" + etlStep.getEtlStepType().getEtlStepType() + ", " + etlStepNext.getEtlStepType().getEtlStepType() + ")");
		if (!etlStepNext.getEtlStepType().isPassThroughStep()) {
		// Where did we come from?
			switch (etlStep.getEtlStepType().getEtlStepType().trim().toLowerCase()) {
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
		} else {
			Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeInsertUpdate(): it's a pass-through. Nothing to do here.");
		}
	}
	/**
	 * We are moving to a "Table Input' step type.
	 * @param etlStep
	 * @param etlStepNext
	 */
	public static void processETLStepTypeTableInput(ETLStep etlStep, ETLStep etlStepNext) {
		Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeTableInput(" + etlStep.getEtlStepType().getEtlStepType() + ", " + etlStepNext.getEtlStepType().getEtlStepType() + ")");
		if (!etlStepNext.getEtlStepType().isPassThroughStep()) {
		// Where did we come from?
			switch (etlStep.getEtlStepType().getEtlStepType().trim().toLowerCase()) {
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
		} else {
			Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeTableInput(): it's a pass-through. Nothing to do here.");
		}
	}
	/**
	 * We are moving to a "Filter Rows" step type.
	 * @param etlStep
	 * @param etlStepNext
	 */
	public static void processETLStepTypeFilterRows(ETLStep etlStep, ETLStep etlStepNext) {
		Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeFilterRows(" + etlStep.getEtlStepType().getEtlStepType() + ", " + etlStepNext.getEtlStepType().getEtlStepType() + ")");
		if (!etlStepNext.getEtlStepType().isPassThroughStep()) {
			// Where did we come from?
			switch (etlStep.getEtlStepType().getEtlStepType().trim().toLowerCase()) {
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
		} else {
			Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeFilterRows(): it's a pass-through. Nothing to do here.");
		}
	}
	/**
	 * We are moving to a "DB Lookup' step type.
	 * @param etlStep
	 * @param etlStepNext
	 */
	public static void processETLStepTypeDBLookup(ETLStep etlStep, ETLStep etlStepNext) {
		Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeDBLookup(" + etlStep.getEtlStepType().getEtlStepType() + ", " + etlStepNext.getEtlStepType().getEtlStepType() + ")");
		if (!etlStepNext.getEtlStepType().isPassThroughStep()) {
		// Where did we come from?
			switch (etlStep.getEtlStepType().getEtlStepType().trim().toLowerCase()) {
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
		} else {
			Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeDBLookup(): it's a pass-through. Nothing to do here.");
		}
	}
	/**
	 * We are moving to a "Select Values' step type.
	 * @param etlStep
	 * @param etlStepNext
	 */
	public static void processETLStepTypeSelectValues(ETLStep etlStep, ETLStep etlStepNext) {
		Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeSelectValues(" + etlStep.getEtlStepType().getEtlStepType() + ", " + etlStepNext.getEtlStepType().getEtlStepType() + ")");
		if (!etlStepNext.getEtlStepType().isPassThroughStep()) {
			// Where did we come from?
			switch (etlStep.getEtlStepType().getEtlStepType().trim().toLowerCase()) {
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
		} else {
			Log.logProgress("ETLStepMovementToNextStep.processETLStepTypeSelectValues(): it's a pass-through. Nothing to do here.");
		}
	}
}
