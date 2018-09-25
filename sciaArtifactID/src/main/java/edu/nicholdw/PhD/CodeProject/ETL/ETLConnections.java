package edu.nicholdw.PhD.CodeProject.ETL;

import java.util.ArrayList;
import java.util.Iterator;

/***
 * A set of ETLConnection objects.
 * @author nicomp
 *
 */
public class ETLConnections implements Iterable<ETLConnection> {
	private ArrayList<ETLConnection> ETLConnections;
	
	public ETLConnections() {
		ETLConnections = new ArrayList<ETLConnection>();
	}

	/***
	 * Add a new object to the set
	 * @param ETLConnection The ETLConnection object to be added. A deep copy is performed.
	 */
	public void addETLConnection(ETLConnection ETLConnection) {
		ETLConnections.add(new ETLConnection(ETLConnection));
	}
	
	public String toString() {
		String result = "";
		String crlf = "";
		for (ETLConnection ETLConnection : ETLConnections) {
			result += crlf + ETLConnection.toString();
			crlf = "\n";
		}
		return result;
	}

	@Override
	public Iterator<ETLConnection> iterator() {
		Iterator<ETLConnection> myIterator = ETLConnections.iterator();
        return myIterator;
	}
}
