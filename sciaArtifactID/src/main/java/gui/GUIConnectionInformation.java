package gui;
import javafx.beans.property.SimpleStringProperty;

/***
 * Connection Information for the TableView control in the Config GUI
 * @author nicomp
 *
 */
public class GUIConnectionInformation {
	 
	private final SimpleStringProperty connectionName = new SimpleStringProperty("");
	private final SimpleStringProperty loginName = new SimpleStringProperty("");
	private final SimpleStringProperty password = new SimpleStringProperty("");
	private final SimpleStringProperty hostName = new SimpleStringProperty("");
	private final SimpleStringProperty schemaName = new SimpleStringProperty("");

	public GUIConnectionInformation() {
	        this("", "", "","","");
    }

    public GUIConnectionInformation(String connectionName, String hostName, String loginName, String password, String schemaName) {
    	setConnectionName(connectionName);
    	setLoginName(loginName);
    	setPassword(password);
    	setHostName(hostName);
    	setSchemaName(schemaName);
    }

    public String getLoginName() {
        return loginName.get();
    }
    public void setLoginName(String loginName) {
        this.loginName.set(loginName);
    }
	 
    public String getPassword() {
        return password.get();
    }
    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getConnectionName() {
        return connectionName.get();
    }    
    public void setConnectionName(String connectionName) {
        this.connectionName.set(connectionName);
    }
	    
    public String getHostName() {
        return hostName.get();
    }
    public void setHostName(String hostName) {
        this.hostName.set(hostName);
    }
	    
    public void setSchemaName(String schemaName) {
        this.schemaName.set(schemaName);
    }
    public String getSchemaName() {
        return schemaName.get();
    }
}

