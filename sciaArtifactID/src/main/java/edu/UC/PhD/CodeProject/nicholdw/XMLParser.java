package edu.UC.PhD.CodeProject.nicholdw;
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
		Log.logProgress("XMLParser.parseXMLForOutputSteps(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		OutputStep outputStep=null;
		List<OutputStep> outputsteps=new ArrayList<OutputStep>();
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
					outputsteps.add(outputStep);
					break;
				case 1:
					outputStep = TableOutputStepParser.parseXMLForTableOutputStep(doc, xpath, step);
					outputsteps.add(outputStep);
					break;
				default: break;
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForOutputSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return outputsteps;
	}

	public List<TableInputStep> parseXMLForInputSteps(String xmlFilePath){
		Log.logProgress("XMLParser.parseXMLForInputSteps(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="TableInput";
		TableInputStep tableinputStep=null;
		List<TableInputStep> inputsteps=new ArrayList<TableInputStep>();
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				tableinputStep=TableInputStepParser.parseXMLByStepName(doc, xpath, stepname);
				inputsteps.add(tableinputStep);
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForInputSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return inputsteps;
	}

	private List<String> getStepNamesByType(XPath xpath, Document doc, String steptype){
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
		Log.logProgress("XMLParser.parseXMLForDBJoinSteps(" + xmlFilePath + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="DBJoin";
		DBJoinStep dbjoinstep=null;
		List<DBJoinStep> dbjoinsteps=new ArrayList<DBJoinStep>();
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				dbjoinstep=DBJoinStepParser.parseXMLByStepName(doc, xpath, stepname);
				dbjoinsteps.add(dbjoinstep);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForDBJoinSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return dbjoinsteps;
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
}
