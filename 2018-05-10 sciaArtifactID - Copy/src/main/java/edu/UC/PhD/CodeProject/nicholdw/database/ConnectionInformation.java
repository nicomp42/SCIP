/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.database;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import edu.UC.PhD.CodeProject.nicholdw.Utils;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
/**
 * Configuration information for a database connection
 * @author nicomp
 *
 */
public class ConnectionInformation  implements Serializable {
	public static final String connectionNameLiteral = "connectionName", loginNameLiteral = "loginName", passwordLiteral = "password", hostNameLiteral = "hostName", schemaNameLiteral = "schemaName"; 
	private static final long serialVersionUID = 1L;
	private Hashtable<String, String> settings;
	//private String connectionName;			// Just a familiar name that the user can assign
	//private String loginName;
	//private String password;
	//private String hostName;
	//private String schemaName;			// Or database name, depending on the DB engine we are talking to
	public String getConnectionName() {
		return settings.get(connectionNameLiteral);
	}
	public void setConnectionName(String connectionName) {
		//this.connectionName = connectionName;
		settings.put(connectionNameLiteral, connectionName);
	}	
	public String getLoginName() {
		return settings.get(loginNameLiteral);
	}
	public void setLoginName(String loginName) {
		//this.loginName = loginName;
		settings.put(loginNameLiteral,  loginName);
	}
	public String getPassword() {
		return settings.get(passwordLiteral);
	}
	public void setPassword(String password) {
		//this.password = password;
		settings.put(passwordLiteral, password);
	}
	public String getHostName() {
		return settings.get(hostNameLiteral);
	}
	public void setHostName(String hostName) {
		//this.hostName = hostName;
		settings.put(hostNameLiteral, hostName);
	}
	public String getSchemaName() {
		return settings.get(schemaNameLiteral);
	}
	public void setSchemaName(String schemaName) {
		//this.schemaName = schemaName;
		settings.put(schemaNameLiteral, schemaName);
	}
	/**
	 * Create a ConnectionInformation object
	 * @param hostName Host Name of the database
	 * @param loginName Login Name to access the database
	 * @param password Password to access the database
	 */
	public ConnectionInformation(String connectionName, String hostName, String loginName, String password, String schemaName) {
		settings = new Hashtable<String, String>();
		setConnectionName(connectionName);
		setLoginName(loginName);
		setPassword(password);
		setHostName(hostName);
		setSchemaName(schemaName);
	}
	/**
	 * Copy Constructor
	 * @param connectionInformation The object to be copied
	 */
	public ConnectionInformation(ConnectionInformation connectionInformation) {
		settings = new Hashtable<String, String>();
		setConnectionName(connectionInformation.getConnectionName());
		setLoginName(connectionInformation.getLoginName());
		setPassword(connectionInformation.getPassword());
		setHostName(connectionInformation.getHostName());
		setSchemaName(connectionInformation.getSchemaName());
	}
	
	public static ConnectionInformations readXML() {
		// https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
		ConnectionInformations connectionInformations = new ConnectionInformations();
      try {
    	  String path = "/ConnectionInformation/ConnectionInformation.xml";		// The root is the resources folder in the project structure
    	 InputStream res = ConnectionInformation.class.getResourceAsStream(path);
         //File inputFile = new File(path);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(res);			//(inputFile);
         doc.getDocumentElement().normalize();
         //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nodeList = doc.getElementsByTagName("connectionInformation");
         //System.out.println("----------------------------");
         
         for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               ConnectionInformation connectionInformation = new ConnectionInformation(eElement.getAttribute("name"),
            		   																   eElement.getElementsByTagName("hostname").item(0).getTextContent(),
            		   																   eElement.getElementsByTagName("loginname").item(0).getTextContent(),
            		   																   eElement.getElementsByTagName("password").item(0).getTextContent(),
            		   																   eElement.getElementsByTagName("schema").item(0).getTextContent());
               // Here is how to read the elements without knowing their names in advance
/*             NodeList children = eElement.getChildNodes();
               Node current = null;
               int count = children.getLength();
               for (int i = 0; i < count; i++) {
                 current = children.item(i);
                 if (current.getNodeType() == Node.ELEMENT_NODE) {
                   Element element = (Element) current;
                   System.out.println(element.getTagName());
                 }
               } */
               connectionInformations.addConnectionInformation(connectionInformation);
               /*
               Log.logProgress("ConnectionInformation.readXML(): Connection Name : " + connectionInformation.getConnectionName());
               Log.logProgress("ConnectionInformation..readXML(): Host Name : " + connectionInformation.getHostName());
               Log.logProgress("ConnectionInformation.readXML(): Login Name : " + connectionInformation.getLoginName());
               Log.logProgress("ConnectionInformation.readXML(): Password : "  + connectionInformation.getPassword());
               Log.logProgress("ConnectionInformation.readXML(): Schema : "    + connectionInformation.getSchemaName());
               */
            }
         }
      } catch (Exception e) {
         Log.logError("ConnectionInformation.readXML(): "+ e.getLocalizedMessage());
      }
      return connectionInformations;
   }
	public String toString() {
		String result = "";
		result = getConnectionName() + ", " + getHostName() + ", " + getLoginName() + ", " + getPassword() + ", " + getSchemaName();  
		return result;
	}
	/***
	 * Load the ListView with database connections
	 * @param listView The ListView to be loaded
	 * @param connectionInformations The set of database connections or null if you want to load from the XML file
	 */
	public static void loadListViewWithDatabaseConnections(ListView<String> listView, ConnectionInformations connectionInformations) {
		if (connectionInformations == null) {
			connectionInformations = readXML();
		}
   		ArrayList<String> arr = new ArrayList<>();
   		for (ConnectionInformation ci : connectionInformations) {
   			arr.add(Utils.replaceNullWithEmpty(ci.toString()));
   		}
		ObservableList<String> list = FXCollections.observableArrayList(arr);
		listView.setItems(list);
	}
	/***
	 * Load the TableView with database connections
	 * @param tableView The TableView to be loaded
	 * @param connectionInformations The set of database connections or null if you want to load from the XML file
	 */
	public static void loadTableViewWithDatabaseConnections(TableView<gui.GUIConnectionInformation> tableView, ConnectionInformations connectionInformations) {
		if (connectionInformations == null) {
			connectionInformations = readXML();
		}
        ObservableList<gui.GUIConnectionInformation> data = tableView.getItems();
   		for (ConnectionInformation ci : connectionInformations) {
   	        data.add(new gui.GUIConnectionInformation(ci.getConnectionName(), ci.getHostName(), ci.getLoginName(), ci.getPassword(), ci.getSchemaName()));
//   	        data.add(new gui.ConnectionInformation(ci.getConnectionName(), ci.getHostName(), ci.getLoginName(), ci.toString(), ci.getSchemaName()));
   		}
    }		
}