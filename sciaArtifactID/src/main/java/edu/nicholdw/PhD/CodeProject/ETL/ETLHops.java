/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.Iterator;

/***
 * A set of ETLHop objects.
 * @author nicomp
 *
 */
public class ETLHops implements Iterable<ETLHop>, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2640962482789091882L;
	private ArrayList<ETLHop> etlHops;

	public ETLHops() {
		etlHops = new ArrayList<ETLHop>();
	}
	public ETLHop getETLHop(ETLHop etlHopTarget) {
		ETLHop ETLHopFound = null;
		for (ETLHop etlHop : etlHops) {
			if (etlHop.getToStepName().toUpperCase().equals(etlHopTarget.getToStepName().toUpperCase())
			 &&	etlHop.getFromStepName().toUpperCase().equals(etlHopTarget.getFromStepName().toUpperCase())
			 &&	etlHop.getFileName().toUpperCase().equals(etlHopTarget.getFileName().toUpperCase())) {
				ETLHopFound = new ETLHop(etlHop);
				break;
			}
		}
		return ETLHopFound;
	}
	/***
	 * Find the hop that begins at an ETL Step
	 * @param etlStep The ETL Step
	 * @return Reference to the ETLHop. No copy is performed.
	 */
	public ETLHop getETLHopWithStartStep(ETLStep etlStep) {
		ETLHop etlHopFound = null;
		for (ETLHop etlHop : etlHops) {
			if (etlStep.getStepName().equals(etlHop.getFromStepName())) {
				// We found the hop that starts at etlStep
//				etlHopFound = new ETLHop(etlHop);
				etlHopFound = etlHop;
				break;
			}
		}
		return etlHopFound;
	}
	/***
	 * Find the hop that begins at an ETL Step. This doesn't need a file name because
	 * we assume all the hops are in the same transformation file.
	 * @param etlStep The ETL Step Name
	 * @return Reference to a copy of the ETLHop
	 */
	public ETLHop getETLHop(String etlStepName) {
		ETLHop etlHopFound = null;
		for (ETLHop etlHop : etlHops) {
			if (etlStepName.equals(etlHop.getFromStepName())) {
				// We found the hop that starts at etlStep
				etlHopFound = new ETLHop(etlHop);
				break;
			}
		}
		return etlHopFound;
	}

	/***
	 * Add a new object to the set
	 * @param ETLHop The ETLHop object to be added. A deep copy is performed.
	 */
	public void addETLHop(ETLHop ETLHop) {
		etlHops.add(new ETLHop(ETLHop));
	}
	/**
	 * Add all hops from another ETLHops object. Deep copy of each ETLHop object!
	 * @param etlHops
	 */
	public void addETLHops(ETLHops etlHops) {
		for (ETLHop etlHop: etlHops) {
			addETLHop(new ETLHop(etlHop));
		}
	}
	public String toString() {
		String result = "";
		String crlf = "";
		for (ETLHop ETLHop : etlHops) {
			result += crlf + ETLHop.toString();
			crlf = "\n";
		}
		return result;
	}

	@Override
	public Iterator<ETLHop> iterator() {
		Iterator<ETLHop> myIterator = etlHops.iterator();
        return myIterator;
	}
	/**
	 * Duplicate an ETLHops object. Deep copy!
	 * @param etlHopsObject
	 */
	public void clone(ETLHops etlHopsObject) {
		this.etlHops.clear();
		for (ETLHop etlHop : etlHopsObject) {
			this.etlHops.add(etlHop);
		}
	}
}
