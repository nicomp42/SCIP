// https://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
package edu.UC.PhD.CodeProject.nicholdw.neo4j;

import java.io.File;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;


public class Neo4jXML {

	public static boolean WriteGraphDBToXMLFile(String filePath, Neo4jDB neo4jDB) {
		boolean result = true;
		try {
			Attr attr;
			Element nodeIDElement;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Neo4jDatabase");	// No spaces allowed in element name!
			doc.appendChild(rootElement);
			// set attribute for root element
			attr = doc.createAttribute("FilePath");	// No spaces allowed in attribute name!
			attr.setValue(neo4jDB.getFilePath());
			rootElement.setAttributeNode(attr);
			for (Neo4jNode neo4jNode: neo4jDB.getNeo4jNodes().getNeo4jNodes()) {
				Element neo4jNodeElement = doc.createElement("Node");
				rootElement.appendChild(neo4jNodeElement);
				nodeIDElement = doc.createElement("NodeID");
				nodeIDElement.appendChild(doc.createTextNode(String.valueOf(neo4jNode.getNodeID())));
				neo4jNodeElement.appendChild(nodeIDElement);
				Element neo4jNodeLabelElement;
				neo4jNodeLabelElement = doc.createElement("Label");
				neo4jNodeElement.appendChild(neo4jNodeLabelElement);
				for (String label: neo4jNode.getLabels()) {
					Element neo4jNodeLabelValueElement = null;
					neo4jNodeLabelValueElement = doc.createElement("Value");
					neo4jNodeLabelElement.appendChild(neo4jNodeLabelValueElement);
					neo4jNodeLabelValueElement.appendChild(doc.createTextNode(String.valueOf(label)));
				}
				Element neo4jPropertyElement;
				for (Entry<String, Neo4jProperty> neo4jProperty: neo4jNode.getProperties().getNeo4jProperties().entrySet()) {
					neo4jPropertyElement = doc.createElement("Property");
					Element neo4jPropertyNameElement = doc.createElement("Name");
					neo4jPropertyNameElement.appendChild(doc.createTextNode(neo4jProperty.getKey()));	// The name of the property
					neo4jPropertyElement.appendChild(neo4jPropertyNameElement);
					Element neo4jPropertyValueElement;
					for (String neo4jPropertyValue: neo4jProperty.getValue().getNeo4jPropertyValues().getPropertyValues()) {
						neo4jPropertyValueElement = doc.createElement("Value");
						neo4jPropertyValueElement.appendChild(doc.createTextNode(neo4jPropertyValue));	// The name of the property
						neo4jPropertyElement.appendChild(neo4jPropertyValueElement);
					}
					neo4jNodeElement.appendChild(neo4jPropertyElement);
				}
				Element neo4jRelationshipElement;
				for (Neo4jRelationship neo4jRelationship: neo4jNode.getNeo4jRelationships().getNeo4jRelationships()) {
					neo4jRelationshipElement = doc.createElement("Relationship");
					neo4jNodeElement.appendChild(neo4jRelationshipElement);
					Element relationshipStartNodeIDElement, relationshipEndNodeIDElement;
					relationshipStartNodeIDElement = doc.createElement("StartNode");
					relationshipEndNodeIDElement = doc.createElement("EndNode");
					neo4jNodeElement.appendChild(relationshipStartNodeIDElement);
					neo4jNodeElement.appendChild(relationshipEndNodeIDElement);
					relationshipStartNodeIDElement.appendChild(doc.createTextNode(String.valueOf(neo4jRelationship.getStartNodeID())));
					relationshipEndNodeIDElement.appendChild(doc.createTextNode(String.valueOf(neo4jRelationship.getEndNodeID())));
					
					Element neo4jRelationshipTypeElement;
					neo4jRelationshipTypeElement = doc.createElement("RelationshipType");
					neo4jNodeElement.appendChild(neo4jRelationshipTypeElement);
					neo4jRelationshipTypeElement.appendChild(doc.createTextNode(neo4jRelationship.getRelationshipType().toString()));
					
					Element neo4jRelationshipPropertyElement;
					for (Entry<String, Neo4jProperty> neo4jProperty: neo4jNode.getProperties().getNeo4jProperties().entrySet()) {
						neo4jRelationshipPropertyElement = doc.createElement("Property");
						Element neo4jPropertyNameElement = doc.createElement("Name");
						neo4jPropertyNameElement.appendChild(doc.createTextNode(neo4jProperty.getKey()));	// The name of the property
						neo4jRelationshipPropertyElement.appendChild(neo4jPropertyNameElement);
						Element neo4jPropertyValueElement;
						for (String neo4jPropertyValue: neo4jProperty.getValue().getNeo4jPropertyValues().getPropertyValues()) {
							neo4jPropertyValueElement = doc.createElement("Value");
							neo4jPropertyValueElement.appendChild(doc.createTextNode(neo4jPropertyValue));	// The name of the property
							neo4jRelationshipPropertyElement.appendChild(neo4jPropertyValueElement);
						}
						neo4jNodeElement.appendChild(neo4jRelationshipPropertyElement);
					}				
				}
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File(filePath));		
			transformer.transform(source, streamResult);
			
		} catch (Exception ex) {
			Log.logError("Neo4jXML.WriteGraphDBToXMLFile();" + ex.getLocalizedMessage());
			result = false;
		}
		return result;
	}
}
