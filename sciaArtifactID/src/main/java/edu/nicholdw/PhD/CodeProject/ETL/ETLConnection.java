/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.nicholdw.PhD.CodeProject.ETL;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ETLConnection {
	private String name;
	private String server;
	private String database;
	private String userName;
	private String type;
	
	//  Since the password in the XML is encrypted. We will ask for a password for the userName that was in the XML file.
	private String passwordDefinedExternally;		// TODO Get this pw from somewhere rather than hard=coding it.

	public ETLConnection(String name, String server, String database, String userName, String type) {
		this.setName(name);
		this.setDatabase(database);
		this.setServer(server);
		this.setUserName(userName);
		this.setType(type);
		setPasswordDefinedExternally("Danger42");
	}
	public ETLConnection(ETLConnection etlConnection) {
		this.setName(etlConnection.getName());
		this.setServer(etlConnection.getServer());
		this.setDatabase(etlConnection.getDatabase());
		this.setUserName(etlConnection.getUserName());
		this.setType(etlConnection.getType());
		this.setPasswordDefinedExternally(etlConnection.getPasswordDefinedExternally());
	}
	
	public String toString() {
		return getName() + ":" + getServer() + ":" + getDatabase() + ":" + getUserName() + ":" + getType();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Called host name in other places. 
	 * @return The server name / host name
	 */
	public String getServer() {
		return server;
	}
	/**
	 * Called host name in other places. 
	 * @param server The server name / host name
	 */
	public void setServer(String server) {
		this.server = server;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public static void loadTableViewWithETLConnections(TableView<gui.GUIETLConnection> tableView, ETLConnections etlConnections) {
        ObservableList<gui.GUIETLConnection> data = tableView.getItems();
        data.clear();
   		for (ETLConnection etlConnection : etlConnections) {
   	        data.add(new gui.GUIETLConnection(etlConnection.getName(), 
                       						  etlConnection.getServer(), 
   	        		                          etlConnection.getDatabase(), 
   	        		                          etlConnection.getUserName(),
   	        		                          etlConnection.getType()));
   		}
    }
	public String getPasswordDefinedExternally() {
		return passwordDefinedExternally;
	}
	public void setPasswordDefinedExternally(String passwordDefinedExternally) {
		this.passwordDefinedExternally = passwordDefinedExternally;
	}
}
