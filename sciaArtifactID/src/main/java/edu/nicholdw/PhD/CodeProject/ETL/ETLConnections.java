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
	 * Find an ETLConnection object by its' name
	 * @param name The connection name to search for
	 * @return A clone of the ETLConnection object with the same name, or null if no match
	 */
	public ETLConnection getConnection(String name) {
		ETLConnection etlConnectionFound = null;
		for (ETLConnection etlConnection : ETLConnections) {
			if (etlConnection.getName().equals(name)) {
				etlConnectionFound = new ETLConnection(etlConnection);
			}
		}
		return etlConnectionFound;
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
