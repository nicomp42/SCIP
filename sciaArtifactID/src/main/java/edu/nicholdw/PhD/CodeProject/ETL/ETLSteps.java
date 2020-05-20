/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.Iterator;

/***
 * A set of ETLStep objects.
 * @author nicomp
 *
 */
public class ETLSteps implements Iterable<ETLStep>, java.io.Serializable {
	private ArrayList<ETLStep> etlSteps;
	
	public ETLSteps() {
		etlSteps = new ArrayList<ETLStep>();
	}
	public ETLStep getETLStep(String stepName) {
		ETLStep etlStepFound = null;
		for (ETLStep etlStep : etlSteps) {
			if (etlStep.getStepName().equals(stepName)) {
				etlStepFound = new ETLStep(etlStep);
				break;
			}
		}
		return etlStepFound;
	}
	public ETLStep getETLStep(String stepName, String fileName) {
		ETLStep etlStepFound = null;
		for (ETLStep etlStep : etlSteps) {
			if (etlStep.getStepName().equals(stepName) && etlStep.getFileName().equals(fileName)) {
//				etlStepFound = new ETLStep(etlStep);
				etlStepFound = etlStep;		// ToDo: Fix ETLStep copy constructor
				break;
			}
		}
		return etlStepFound;
	}
	/***
	 * Add a new object to the set
	 * @param ETLStep The ETLStep object to be added. The reference is added. No deep copy is performed.
	 */
	public void addETLStep(ETLStep etlStep) {
//		etlSteps.add(new ETLStep(etlStep));	// ToDo: Fix the copy constructor in ETLStep
		etlSteps.add(etlStep);
	}

	public String toString() {
		String result = "";
		String crlf = "";
		for (ETLStep etlStep : etlSteps) {
			result += crlf + etlStep.toString();
			crlf = "\n";
		}
		return result;
	}

	@Override
	public Iterator<ETLStep> iterator() {
		Iterator<ETLStep> myIterator = etlSteps.iterator();
        return myIterator;
	}
}
