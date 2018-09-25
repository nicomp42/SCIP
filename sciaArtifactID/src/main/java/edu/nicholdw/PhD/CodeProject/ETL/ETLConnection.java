package edu.nicholdw.PhD.CodeProject.ETL;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ETLConnection {
	private String name;
	private String server;
	private String database;
	private String userName;

	public ETLConnection(String name, String server, String database, String userName) {
		this.setName(name);
		this.setDatabase(database);
		this.setServer(server);
		this.setUserName(userName);
	}
	public ETLConnection(ETLConnection etlConnection) {
		this.setName(etlConnection.getName());
		this.setDatabase(etlConnection.getDatabase());
		this.setServer(etlConnection.getServer());
		this.setUserName(etlConnection.getUserName());
	}
	
	public String toString() {
		return getName() + ":" + getServer() + ":" + getDatabase() + ":" + getUserName();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getServer() {
		return server;
	}
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
	public static void loadTableViewWithETLConnections(TableView<gui.GUIETLConnection> tableView, ETLConnections etlConnections) {
        ObservableList<gui.GUIETLConnection> data = tableView.getItems();
   		for (ETLConnection etlConnection : etlConnections) {
   	        data.add(new gui.GUIETLConnection(etlConnection.getName(), 
   	        		                          etlConnection.getDatabase(), 
   	        		                          etlConnection.getServer(), 
   	        		                          etlConnection.getUserName()));
   		}
    }
}
