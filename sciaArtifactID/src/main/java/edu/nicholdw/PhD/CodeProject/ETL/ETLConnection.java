package edu.nicholdw.PhD.CodeProject.ETL;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ETLConnection {
	private String name;
	private String server;
	private String database;
	private String userName;
	private String type;

	public ETLConnection(String name, String server, String database, String userName, String type) {
		this.setName(name);
		this.setDatabase(database);
		this.setServer(server);
		this.setUserName(userName);
		this.setType(type);
	}
	public ETLConnection(ETLConnection etlConnection) {
		this.setName(etlConnection.getName());
		this.setServer(etlConnection.getServer());
		this.setDatabase(etlConnection.getDatabase());
		this.setUserName(etlConnection.getUserName());
		this.setType(etlConnection.getType());
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
}
