/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.UC.PhD.CodeProject.nicholdw.CombinationLookupStepParser;
import edu.UC.PhD.CodeProject.nicholdw.CombinationLookupUpdateStep;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStep;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStepParser;
import edu.UC.PhD.CodeProject.nicholdw.DBLookupStep;
import edu.UC.PhD.CodeProject.nicholdw.DBLookupStepParser;
import edu.UC.PhD.CodeProject.nicholdw.DBProcStepParser;
import edu.UC.PhD.CodeProject.nicholdw.DimLookupUpdateStep;
import edu.UC.PhD.CodeProject.nicholdw.DimensionLookupStepParser;
import edu.UC.PhD.CodeProject.nicholdw.ExecuteSQLScriptStep;
import edu.UC.PhD.CodeProject.nicholdw.ExecuteSQLScriptStepParser;
import edu.UC.PhD.CodeProject.nicholdw.InsertUpdateStepParser;
import edu.UC.PhD.CodeProject.nicholdw.TableOutputStep;
import edu.UC.PhD.CodeProject.nicholdw.StepName;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStepParser;
import edu.UC.PhD.CodeProject.nicholdw.TableOutputStepParser;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
public class XMLParser {
	public static Document dom;
	private String xmlDirectory;
/*	
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
	}*/
	/***
	 * Lop off the file name from the end of a fully qualified path or a relative path 
	 * @param filePath
	 * @return Everything but the file name
	 */
	public static String extractFilePathPrefix(String filePath) {
		int idx = filePath.lastIndexOf("\\");
		return filePath.substring(0, idx);
	}
	public List<TableOutputStep> parseXMLForOutputSteps(String fileName){
		ArrayList<TableOutputStep> outputSteps = new ArrayList<TableOutputStep>();
		parseXMLForTableOutputSteps(new ETLTransformationFile(fileName, 0), outputSteps);
		return outputSteps;
	}
	public void parseXMLForTableOutputSteps(ETLTransformationFile etlTransformationFile, List<TableOutputStep> outputSteps) {
		Log.logProgress("XMLParser.parseXMLForOutputSteps(): " + this.getxmlDirectory() + etlTransformationFile.getFileName());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		TableOutputStep outputStep=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.getxmlDirectory() + etlTransformationFile.getFileName());
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several steps */
			List<String> listOfAllSteps=getStepNames(xpath,doc);

			for(String step:listOfAllSteps){
				int stepType=getStepType(xpath, doc, step);
				switch(stepType){
					case 0:
						outputStep = InsertUpdateStepParser.parseXMLForInsertUpdateStep(doc, xpath, step, this.getxmlDirectory(), etlTransformationFile.getFileName(), etlTransformationFile.getEtlStage());
						outputSteps.add(outputStep);
						break;
					case 1:
						outputStep = TableOutputStepParser.parseXMLForTableOutputStep(doc, xpath, step, this.getxmlDirectory(), etlTransformationFile.getFileName(), etlTransformationFile.getEtlStage());
						outputSteps.add(outputStep);
						break;
					default: 
						break;
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForOutputSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		//return outputSteps;
	}
	public List<TableInputStep> parseXMLForInputSteps(String xmlFilePath, String fileName){
		List<TableInputStep> inputSteps = new ArrayList<TableInputStep>();
		parseXMLForTableInputSteps(new ETLTransformationFile(fileName, 0),inputSteps);
		return inputSteps;
	}
	public void parseXMLForTableInputSteps(ETLTransformationFile etlTransformationFile, List<TableInputStep> inputSteps){
		Log.logProgress("XMLParser.parseXMLForInputSteps(" + this.xmlDirectory + etlTransformationFile.getFileName() + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="TableInput";
		TableInputStep tableinputStep=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.xmlDirectory + etlTransformationFile.getFileName());
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				tableinputStep=TableInputStepParser.parseXMLByStepName(doc, xpath, stepname, this.xmlDirectory, etlTransformationFile);
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
	public List<DBLookupStep> parseXMLForDBLookupSteps(ETLTransformationFile etlTransformationFile, List<DBLookupStep> dbLookupSteps){
		Log.logProgress("XMLParser.parseXMLForDBLookupSteps(" + etlTransformationFile.getFileName() + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="DBLookup";
		DBLookupStep dblookupstep=null;
		List<DBLookupStep> dblookupsteps=new ArrayList<DBLookupStep>();
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.xmlDirectory + etlTransformationFile.getFileName());
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Database lookup steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				dblookupstep=DBLookupStepParser.parseXMLByStepName(doc, xpath, stepname, etlTransformationFile.getEtlStage() );
				dblookupsteps.add(dblookupstep);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForDBLookupSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return dblookupsteps;
	}
	public void parseXMLForDBProcSteps(ETLTransformationFile etlTransformationFile, ArrayList<DBProcStep> dbProcSteps){
		Log.logProgress("XMLParser.parseXMLForDBProcSteps(): " + this.xmlDirectory + etlTransformationFile.getFileName());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="DBProc";
		DBProcStep dbProcstep=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.xmlDirectory + etlTransformationFile.getFileName());
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				dbProcstep=DBProcStepParser.parseXMLByStepName(doc, xpath, stepname, this.xmlDirectory, etlTransformationFile.getFileName(), etlTransformationFile.lookupETLStage());
				dbProcSteps.add(dbProcstep);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForDBJoinSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}
	/**
	 * Read hop elements from the Transformation file
	 * @param etlTransformationFile
	 * @param etlHops
	 */
	public void parseXMLForHops(ETLTransformationFile etlTransformationFile, ETLHops etlHops){
		Log.logProgress("XMLParser.parseXMLForHops(): " + this.xmlDirectory + etlTransformationFile.getFileName());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.xmlDirectory + etlTransformationFile.getFileName());
			doc.getDocumentElement().normalize();			
			NodeList myHops = doc.getElementsByTagName("hop");
			for (int temp = 0; temp < myHops.getLength(); temp++) {
 				Node nNode = myHops.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					etlHops.addETLHop(new ETLHop(eElement.getElementsByTagName("from").item(0).getTextContent(), 
							               eElement.getElementsByTagName("to").item(0).getTextContent(),
							               (eElement.getElementsByTagName("enabled").item(0).getTextContent().equals("Y") ? true : false),
							               etlTransformationFile.getFileName()));
				}
			}
			Log.logProgress("XMLParser.parseXMLForHops: Hops read");
		} catch (Exception ex) {
			Log.logError("XMLParser.parseXMLForHops(): " + ex.getLocalizedMessage());
		}
	}

	/**
	 * If you call this you don't get to define an ETL Stage. It defaults to unknown.
	 * @param xmlFilePath
	 * @param fileName
	 * @return
	 */
	public List<DBJoinStep> parseXMLForDBJoinSteps(String xmlFilePath, String fileName){
		ArrayList<DBJoinStep> dbJoinSteps = new ArrayList<DBJoinStep>();
		parseXMLForDBJoinSteps(new ETLTransformationFile(fileName, 0), dbJoinSteps);
		return dbJoinSteps;
	}	
	public void parseXMLForDBJoinSteps(ETLTransformationFile etlTransformationFile, List<DBJoinStep> dbJoinSteps){
		Log.logProgress("XMLParser.parseXMLForDBJoinSteps(): " + this.xmlDirectory + etlTransformationFile.getFileName());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
//		String steptype="DBJoin";
		String steptype="MergeJoin";		// Pentaho uses this
		DBJoinStep dbjoinstep=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.xmlDirectory + etlTransformationFile.getFileName());
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				dbjoinstep=DBJoinStepParser.parseXMLByStepName(doc, xpath, stepname, this.xmlDirectory + etlTransformationFile.getFileName());
				dbJoinSteps.add(dbjoinstep);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForDBJoinSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}
	public void parseXMLForExecuteSQLScriptSteps(ETLTransformationFile etlTransformationFile, List<ExecuteSQLScriptStep> executeSQLScriptSteps) {
		Log.logProgress("XMLParser.parseXMLForDBJoinSteps(): " + this.xmlDirectory + etlTransformationFile.getFileName());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
//		String steptype="DBJoin";
		String steptype="ExecSQL";		// Pentaho uses this
		ExecuteSQLScriptStep executeSQLScriptStep = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.xmlDirectory + etlTransformationFile.getFileName());
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				executeSQLScriptStep = ExecuteSQLScriptStepParser.parseXMLByStepName(doc, xpath, stepname, this.xmlDirectory + etlTransformationFile.getFileName());
				executeSQLScriptSteps.add(executeSQLScriptStep);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForDBJoinSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}
	public void parseXMLForDimLookupUpdateSteps(ETLTransformationFile etlTransformationFile, List<DimLookupUpdateStep> dimlookupupdatesteps){
		Log.logProgress("XMLParser.parseXMLForDimLookupUpdateSteps(): " + this.getxmlDirectory() + etlTransformationFile.getFileName());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		String steptype="DimensionLookup";
		DimLookupUpdateStep dimlookupstep=null;
//		List<DimLookupUpdateStep> dimlookupupdatesteps = new ArrayList<DimLookupUpdateStep>();
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.getxmlDirectory() + etlTransformationFile.getFileName());
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several Table Input steps */
			List<String> listOfAllStepNames=getStepNamesByType(xpath,doc,steptype);

			for(String stepname:listOfAllStepNames){
				dimlookupstep=DimensionLookupStepParser.parseXMLForDimensionLookupStep(doc, xpath, stepname, etlTransformationFile.lookupETLStage());
				// A Dimension Lookup/Update step can be a read-only step (Lookup) or
				// write step (Update)
				dimlookupupdatesteps.add(dimlookupstep);
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			Log.logError("XMLParser.parseXMLForDimLookupUpdateSteps(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
//		return dimlookupupdatesteps;
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
				comblookupstep=CombinationLookupStepParser.parseXMLForCombinationLookupStep(doc, xpath, stepname, ETLTransformationFile.lookupETLStage(0));
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
				if(str.equals("TableOutput")) {
					stepType=1;
				}
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
	public void getStepNames(ETLTransformationFile etlTransformationFile, ArrayList<StepName> listOfAllSteps) {
		Log.logProgress("XMLParser.getStepNames(" + this.xmlDirectory + etlTransformationFile.getFileName() + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.xmlDirectory + etlTransformationFile.getFileName());
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* One transformation is composed of several steps */
			List<String> tmpListOfAllSteps;
			tmpListOfAllSteps = (ArrayList<String>) getStepNames(xpath,doc);
			for (String s: tmpListOfAllSteps) {listOfAllSteps.add(new StepName(s, etlTransformationFile.getFileName(), etlTransformationFile.getEtlStage()));}
		} catch (Exception ex) {
			Log.logProgress("XMLParser.getStepNames(): " + ex.getLocalizedMessage());
		}
	}
	/***
	 * Read the complete list of jobs in the job
	 * @param xmlFilePath The location of the XML file, as exported from Pentaho
	 * @return The list of job names
	 */
	public void getETLJobs(String xmlFilePath, ETLJobs etlJobs) {
		Log.logProgress("XMLParser.getETLJobs(" + xmlFilePath + ")");
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
			ETLJobs tmpETLJobs;
			tmpETLJobs = getETLJobs(xpath, doc);
			for (ETLJob etlJob: tmpETLJobs) {etlJobs.addETLJob(new ETLJob(etlJob));}
		} catch (Exception ex) {
			Log.logProgress("XMLParser.getJobNames(): " + ex.getLocalizedMessage());
		}
	}
	public void getConnectionNames(ETLTransformationFile etlTransformationFile, List<String> connectionNames){
		Log.logProgress("XMLParser.getConnectionNames(" + this.getxmlDirectory() + etlTransformationFile.getFileName() + ")");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(this.getxmlDirectory() + etlTransformationFile.getFileName());
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
			for (int i = 0; i < nodes.getLength(); i++) {
				stepNames.add(nodes.item(i).getNodeValue());
			}
		}catch (XPathExpressionException e) {
			Log.logError("XMLParser.getStepNames(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return stepNames;
	}
	private ETLJobs getETLJobs(XPath xpath, Document doc){
		Log.logProgress("XMLParser.getETLJobs(" + xpath + ")");
		ETLJobs etljobs = new ETLJobs();
		try {
			// /*/item[id/@isInStock='true']/category/text()
			//  //job/entries/entry[./type[.='JOB']]
//			XPathExpression expr = xpath.compile("/job/entries/entry/name/text()");
			XPathExpression expr = xpath.compile("/job/entries/entry[./type[.='JOB']]/name/text()");

			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				String name;
				name = nodes.item(i).getNodeValue();
				String pattern;
				//        "/job/entries/entry[./type[.='JOB']]/name/text()"
//				pattern = "/job/entries/entry[./type[.='JOB']]/[./name[.='" + name + "']]/filename/text()";
				pattern = "/job/entries/entry[./type[.='JOB'] and ./name[.='" + name + "']]/filename/text()";
				XPathExpression exprFilename = xpath.compile(pattern);
				String filename;
				NodeList filenameNodes;
				filenameNodes = (NodeList) exprFilename.evaluate(doc, XPathConstants.NODESET);
				filename = filenameNodes.item(0).getNodeValue();
				String description;
				NodeList descriptionNodes;
				pattern = "/job/entries/entry[./type[.='JOB'] and ./name[.='" + name + "']]/description/text()";
				XPathExpression exprDescription = xpath.compile(pattern);
				descriptionNodes = (NodeList) exprDescription.evaluate(doc, XPathConstants.NODESET);
				description = "";
				try {
					description = descriptionNodes.item(0).getNodeValue();
				} catch (Exception ex) {
					// If the description is null we will end up here. 
					Log.logError("XMLParser.getETLJobs(): Null description. This is probably not a problem. " + ex.getLocalizedMessage());
				}
				etljobs.addETLJob(new ETLJob(name, filename, description));		// new nodes.item(i).getNodeValue());
			}
		}catch (XPathExpressionException e) {
			Log.logError("XMLParser.getJobNames(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return etljobs;
	}
	
	/**
	 * Read fields for an ETLStep that is an OutputStep 
	 * @param xpath
	 * @param doc
	 * @param stepName
	 * @return
	 */
	public ETLFields getETLFields(XPath xpath, Document doc, String stepName){
		Log.logProgress("XMLParser.getFields(" + xpath + ")");
		String cleanStepName=stepName.replace("'", "");
		ETLFields etlFields = new ETLFields();
		try {
			String myPath = "/transformation/step[name='" + cleanStepName + "']/fields/field";
			XPathExpression expr = xpath.compile(myPath);

			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				
//				System.out.println(node.toString() + " " + node.getNodeName());
				NodeList children = node.getChildNodes();
/*			
				NodeList children = nodes.item(i).getChildNodes();
				NamedNodeMap nnm; 
				nnm = nodes.item(i).getAttributes();
				System.out.println("nnm length = " + nnm.getLength() + " children length = " + children.getLength()); */
				String streamName = "", columnName = "";
				streamName  = ""; columnName = "";
				for (int k = 0; k < children.getLength(); k++) {
					if (children.item(k).getNodeName().equals("#text")) {continue;}
//					System.out.println("Node Name : " + children.item(k).getNodeName());
//					System.out.println("Node Value : " + children.item(k).getNodeValue());
//					System.out.println("Local Name : " + children.item(k).getLocalName());
//					System.out.println("Text Content : " + children.item(k).getTextContent());
					if (children.item(k).getNodeName().equals("column_name") ) {
						columnName = children.item(k).getTextContent();
					}
					if (children.item(k).getNodeName().equals("stream_name")) {
						streamName = children.item(k).getTextContent();
					}
				}
				etlFields.addETLField(new ETLField(columnName, streamName));						
			}
		}catch (Exception e) {
			Log.logError("XMLParser.getETLFields(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
		return etlFields;
	}
	public void megaParser(String stepType) {
		switch (stepType) {
		 
		}
	}
	public String getxmlDirectory() {
		return xmlDirectory;
	}
	public void setxmlDirectory(String xmlDirectory) {
		this.xmlDirectory = xmlDirectory;
		if (!this.xmlDirectory.endsWith("\\") && !this.xmlDirectory.endsWith("/")) {
			this.xmlDirectory += "\\";
		}
	}
	
}
