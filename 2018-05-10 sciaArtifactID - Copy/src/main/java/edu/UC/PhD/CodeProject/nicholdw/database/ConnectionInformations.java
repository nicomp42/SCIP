package edu.UC.PhD.CodeProject.nicholdw.database;

import java.util.ArrayList;
import java.util.Iterator;

/***
 * A set of CollectionInformation objects. I know the spelling is bogus but if conforms to our standard for collections of our stuff.
 * @author nicomp
 *
 */
public class ConnectionInformations implements Iterable<ConnectionInformation> {
	private ArrayList<ConnectionInformation> connectionInformations;
	
	public ConnectionInformations() {
		connectionInformations = new ArrayList<ConnectionInformation>();
	}
	
	/***
	 * Add a new object to the set
	 * @param connectionInformation The CollectionInformation object to be added. A deep copy is performed.
	 */
	public void addConnectionInformation(ConnectionInformation connectionInformation) {
		connectionInformations.add(new ConnectionInformation(connectionInformation));
	}
	
	public String toString() {
		String result = "";
		String crlf = "";
		for (ConnectionInformation ci : connectionInformations) {
			result += crlf + ci.toString();
			crlf = "\n";
		}
		return result;
	}

	@Override
	public Iterator<ConnectionInformation> iterator() {
		Iterator<ConnectionInformation> myIterator = connectionInformations.iterator();
        return myIterator;
	}
}
