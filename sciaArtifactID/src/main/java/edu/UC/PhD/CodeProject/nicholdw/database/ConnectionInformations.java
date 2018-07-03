package edu.UC.PhD.CodeProject.nicholdw.database;

import java.util.ArrayList;

/***
 * A set of CollectionInformation objects. I know the gramma is bogus
 * @author nicomp
 *
 */
public class ConnectionInformations {
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
	
}
