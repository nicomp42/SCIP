package gui;

import javafx.beans.property.SimpleStringProperty;

/***
 * ETL Step Names Information for the TableView control in the ProcessETL GUI
 * @author nicomp
 *
 */
public class GUIETLConnection {

// These field names must match the cellValueFactory names in the fxml file!
//	<cellValueFactory>
//        <PropertyValueFactory property="name" />
//  </cellValueFactory>
	private final SimpleStringProperty name = new SimpleStringProperty("");
	private final SimpleStringProperty server = new SimpleStringProperty("");
	private final SimpleStringProperty database = new SimpleStringProperty("");
	private final SimpleStringProperty userName = new SimpleStringProperty("");
	private final SimpleStringProperty type = new SimpleStringProperty("");
	
	public GUIETLConnection() {
        this("", "", "", "", "");
	}

    public GUIETLConnection(String name, String server, String database, String userName, String type) {
    	setName(name);
    	setServer(server);
    	setDatabase(database);
    	setUserName(userName);
    	setType(type);
    }

	public String getServer() {
		return server.get();
	}

	public String getName() {
		return name.get();
	}	

	public void setDatabase(String database) {
		this.database.set(database);
	}

	public void setServer(String server) {
		this.server.set(server);
	}

	public String getDatabase() {
		return database.get();
	}
	public void setUserName(String userName) {
		this.userName.set(userName);
	}

	public String getUserName() {
		return userName.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}
}
