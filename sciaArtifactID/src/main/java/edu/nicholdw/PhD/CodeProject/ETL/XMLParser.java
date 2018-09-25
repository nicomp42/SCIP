package edu.nicholdw.PhD.CodeProject.ETL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.UC.PhD.CodeProject.nicholdw.CombinationLookupStepParser;
import edu.UC.PhD.CodeProject.nicholdw.CombinationLookupUpdateStep;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStep;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStepParser;
import edu.UC.PhD.CodeProject.nicholdw.DBLookupStep;
import edu.UC.PhD.CodeProject.nicholdw.DBLookupStepParser;
import edu.UC.PhD.CodeProject.nicholdw.DimLookupUpdateStep;
import edu.UC.PhD.CodeProject.nicholdw.DimensionLookupStepParser;
import edu.UC.PhD.CodeProject.nicholdw.InsertUpdateStepParser;
import edu.UC.PhD.CodeProject.nicholdw.OutputStep;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStepParser;
import edu.UC.PhD.CodeProject.nicholdw.TableOutputStepParser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

public class XMLParser {
	public static Document dom;
	
	public static void main(String[] args) {
		Log.logProgress("XMLParser.main(): ");
		try {
			XMLParser myXNMLParser = new XMLParser();
			List<OutputStep> os = myXNMLParser.parseXMLForOutputSteps("c:\\temp\\foop.xml");
			List<TableInputStep> is = myXNMLParser.parseXMLForInputSteps("c:\\temp\\foop.xml");
			List<DBJoinStep> js = myXNMLParser.parseXMLForDBJoinSteps("c:\\temp\\foop.xml");
			Log.logProgress("XMLParser.main(): parsing complete.");
		} catch (Exception ex) {
			Log.logError("XMLParser.main(): " + ex.getLocalizedMessage());
		}
	}
	public List<OutputStep> parseXMLForOutputSteps(String xmlFilePath){
		ArrayList<OutputStep> outputSteps = new ArrayList<OutputStep>();
		parseXMLForOutputSteps(xmlFilePath, outputSteps);
		return outputSteps;
	}
	public void parseXMLForOutputSteps(String xmlFilePath, List<OutputStep> outputSteps) {
		Log.logProgress("XMLParser.parseXMLForOutputSteps(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		OutputStep outputStep=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several steps */
			List<String> listOfAllSteps=getStepNames(xpath,doc);

			for(String step:listOfAllSteps){
				int stepType=getStepType(xpath, doc, step);
				switch(stepType){
				case 0:
					outputStep = InsertUpdateStepParser.parseXMLForInsertUpdateStep(doc, xpath, step);
					outputSteps.add(outputStep);
					break;
				case 1:
					outputStep = TableOutputStepParser.parseXMLForTableOutputStep(doc, xpath, step);
					outputSteps.add(outputStep);
					break;
				default: break;
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForOutputSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		//return outputSteps;
	}
	public List<TableInputStep> parseXMLForInputSteps(String xmlFilePath){
		ArrayList<TableInputStep> inputSteps = new ArrayList<TableInputStep>();
		parseXMLForInputSteps(xmlFilePath, inputSteps);
		return inputSteps;
	}
	public void parseXMLForInputSteps(String xmlFilePath, ArrayList<TableInputStep> inputSteps){
		Log.logProgress("XMLParser.parseXMLForInputSteps(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="TableInput";
		TableInputStep tableinputStep=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				tableinputStep=TableInputStepParser.parseXMLByStepName(doc, xpath, stepname);
				inputSteps.add(tableinputStep);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForInputSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}
	private static List<String> getStepNamesByType(XPath xpath, Document doc, String steptype){
		Log.logProgress("XMLParser.getStepNamesByType(" + xpath.toString() + ")");
		List<String> stepNames=new ArrayList<String>();
		try {
			XPathExpression expr = xpath.compile("/transformation/step[type='"+steptype+"']/name/text()");

			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				stepNames.add(nodes.item(i).getNodeValue());
			}
		}catch (XPathExpressionException e) {
			Log.logError("XMLParser.getStepNamesByType(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return stepNames;
	}
	public List<DBLookupStep> parseXMLForDBLookupSteps(String xmlFilePath){
		Log.logProgress("XMLParser.parseXMLForDBLookupSteps(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="DBLookup";
		DBLookupStep dblookupstep=null;
		List<DBLookupStep> dblookupsteps=new ArrayList<DBLookupStep>();
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Database lookup steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				dblookupstep=DBLookupStepParser.parseXMLByStepName(doc, xpath, stepname);
				dblookupsteps.add(dblookupstep);
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForDBLookupSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return dblookupsteps;
	}
	public List<DBJoinStep> parseXMLForDBJoinSteps(String xmlFilePath){
		ArrayList<DBJoinStep> dbJoinSteps = new ArrayList<DBJoinStep>();
		parseXMLForDBJoinSteps(xmlFilePath, dbJoinSteps);
		return dbJoinSteps;
	}

	public void parseXMLForDBJoinSteps(String xmlFilePath, ArrayList<DBJoinStep> dbJoinSteps){
		Log.logProgress("XMLParser.parseXMLForDBJoinSteps(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="DBJoin";
//		String steptype="MergeJoin";
		DBJoinStep dbjoinstep=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				dbjoinstep=DBJoinStepParser.parseXMLByStepName(doc, xpath, stepname);
				dbJoinSteps.add(dbjoinstep);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForDBJoinSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}

	public List<DimLookupUpdateStep> parseXMLForDimLookupUpdateSteps(String xmlFilePath){
		Log.logProgress("XMLParser.parseXMLForDimLookupUpdateSteps(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="DimensionLookup";
		DimLookupUpdateStep dimlookupstep=null;
		List<DimLookupUpdateStep> dimlookupupdatesteps = new ArrayList<DimLookupUpdateStep>();
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				dimlookupstep=DimensionLookupStepParser.parseXMLForDimensionLookupStep(
						doc, xpath, stepname);
				// A Dimension Lookup/Update step can be a read-only step (Lookup) or
				// write step (Update)
				dimlookupupdatesteps.add(dimlookupstep);
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForDimLookupUpdateSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return dimlookupupdatesteps;
	}
	public List<CombinationLookupUpdateStep> parseXMLForCombinationLookupUpdateSteps(String xmlFilePath){
		Log.logProgress("XMLParser.parseXMLForCombinationLookupUpdateSteps(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="CombinationLookup";
		CombinationLookupUpdateStep comblookupstep=null;
		List<CombinationLookupUpdateStep> comblookupupdatesteps = new ArrayList<CombinationLookupUpdateStep>();
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				comblookupstep=CombinationLookupStepParser.parseXMLForCombinationLookupStep(doc, xpath, stepname);
				comblookupupdatesteps.add(comblookupstep);
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForCombinationLookupUpdateSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return comblookupupdatesteps;
	}
	/***
	 * Read the value of some attribute from a step in the XML file
	 * @param xpath
	 * @param doc
	 * @param stepname The step to read from
	 * @param thing The thing to read
	 * @return The value of the thing that was read
	 */
	public String getSomethingInAStep(XPath xpath, Document doc, String stepname, String thing) {
		Log.logProgress("XMLParser.getSomething(" + xpath.toString() + ")");
		String result = "Error";
		String cleanStepName=stepname.replace("'", "");
		try {
			XPathExpression expr = xpath.compile("/transformation/step[name='"+cleanStepName+"']/" + thing + "/text()");
			result = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			Log.logError("XMLParser.getSomething(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return result;
	}
	/***
	 * Read the value of some attribute from a connection in the XML file
	 * @param xpath
	 * @param doc
	 * @param connectionName The connection to read from
	 * @param thing The thing to read
	 * @return The value of the thing that was read
	 */
	public String getSomethingInAConnection(XPath xpath, Document doc, String connectionName, String thing) {
		Log.logProgress("XMLParser.getSomethingInAConnection(" + xpath.toString() + ")");
		String result = "Error";
		String cleanConnectionName=connectionName.replace("'", "");
		try {
			XPathExpression expr = xpath.compile("/transformation/connection[name='"+cleanConnectionName+"']/" + thing + "/text()");
			result = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			Log.logError("XMLParser.getSomethingInAConnection(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return result;
	}

	public String getSQL(XPath xpath, Document doc, String stepname) {
		Log.logProgress("XMLParser.getSQL(" + xpath.toString() + ")");
		String sql = "Error";
		String cleanStepName=stepname.replace("'", "");
		try {
			XPathExpression expr = xpath.compile("/transformation/step[name='"+cleanStepName+"']/sql/text()");
			sql = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			Log.logError("XMLParser.getSQL(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return sql;
	}
	public String getStepTypeAsString(XPath xpath, Document doc, String stepname) {
		Log.logProgress("XMLParser.getStepTypeAsString(" + xpath.toString() + ")");
		String stepType = "Error";
		String cleanStepName=stepname.replace("'", "");
		try {
			XPathExpression expr = xpath.compile("/transformation/step[name='"+cleanStepName+"']/type/text()");
			stepType = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			Log.logError("XMLParser.getStepTypeAsString(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return stepType;
	}

	private int getStepType(XPath xpath, Document doc, String stepname) {
		Log.logProgress("XMLParser.getStepType(" + xpath.toString() + ")");
		int stepType=-1;
		String cleanStepName=stepname.replace("'", "");
		try {
			XPathExpression expr = xpath.compile("/transformation/step[name='"+cleanStepName+"']/type/text()");
			String str = (String) expr.evaluate(doc, XPathConstants.STRING);

			if(str.equals("InsertUpdate")) {
				stepType=0;
			} else {
				if(str.equals("TableOutput")) stepType=1;
			}

		} catch (XPathExpressionException e) {
			Log.logError("XMLParser.getStepType(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return stepType;
	}
	/***
	 * Read the complete list of steps in the job
	 * @param xmlFilePath The location of the XML file, as exported from Pentaho
	 * @return The list of step names
	 */
	public void getStepNames(String xmlFilePath, ArrayList<String> listOfAllSteps) {
		Log.logProgress("XMLParser.getStepNames(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several steps */
			List<String> tmpListOfAllSteps;
			tmpListOfAllSteps = (ArrayList<String>) getStepNames(xpath,doc);
			for (String s: tmpListOfAllSteps) {listOfAllSteps.add(s);}
			} catch (Exception ex) {
				Log.logProgress("XMLParser.getStepNames(): " + ex.getLocalizedMessage());
			}
	}
	public void getConnectionNames(String xmlFilePath, List<String> connectionNames){
		Log.logProgress("XMLParser.getConnectionNames(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile("/transformation/connection/name/text()");
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				connectionNames.add(nodes.item(i).getNodeValue());
			}
		}catch (Exception e) {
			Log.logError("XMLParser.getConnectionNames(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}
	private List<String> getStepNames(XPath xpath, Document doc){
		Log.logProgress("XMLParser.getStepNames(" + xpath + ")");
		List<String> stepNames=new ArrayList<String>();
		try {
			XPathExpression expr = xpath.compile("/transformation/step/name/text()");

			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++)
				stepNames.add(nodes.item(i).getNodeValue());

		}catch (XPathExpressionException e) {
			Log.logError("XMLParser.getStepNames(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return stepNames;
	}
	public void megaParser(String stepType) {
		switch (stepType) {
		 
		}
	}
	
}
