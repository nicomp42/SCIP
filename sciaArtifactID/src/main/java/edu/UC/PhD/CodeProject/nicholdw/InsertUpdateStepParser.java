/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class InsertUpdateStepParser {

	public static TableOutputStep parseXMLForInsertUpdateStep(Document doc, XPath xpath, String step, String xmlFilePath, String fileName, String etlStage) {
		TableOutputStep stepObject = null;
		String transname = getTransformationName(doc, xpath);
		String dbname = getDatabaseName(doc, xpath, step);
		String tableName = getTableName(doc, xpath, step);
		List<String> fieldnames = getDatabaseFieldNames(doc, xpath, step);
		stepObject = new TableOutputStep(transname, step, "Insert/Update", dbname, tableName, fieldnames, xmlFilePath, fileName, etlStage);
		return stepObject;
	}
	private static String getDatabaseName(Document doc, XPath xpath, String stepname) {
		String connectionname = null;
		String dbname = null;

		try {

			XPathExpression expr = xpath.compile("/transformation/step[name=\'"	+ stepname + "\']/connection/text()");
			connectionname = (String) expr.evaluate(doc, XPathConstants.STRING);

			XPathExpression dbexpr = xpath.compile("/transformation/connection[name='" + connectionname + "']/database/text()");
			dbname = (String) dbexpr.evaluate(doc, XPathConstants.STRING);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return dbname;
	}
	private static List<String> getDatabaseFieldNames(Document doc,	XPath xpath, String stepname) {
		List<String> list = new ArrayList<String>();
		try {
			// create XPathExpression object
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"	+ stepname + "\']/lookup/value/name/text()");
			// evaluate expression result on XML document
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++)
				list.add(nodes.item(i).getNodeValue());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return list;
	}
	private static String getTransformationName(Document doc, XPath xpath) {
		String name = null;
		try {
			XPathExpression expr = xpath.compile("/transformation/info/name/text()");
			name = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return name;
	}
	private static String getTableName(Document doc, XPath xpath, String stepname) {
		String tablename = null;
		try {
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"	+ stepname + "\']/lookup/table/text()");
			tablename = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return tablename;
	}
}
