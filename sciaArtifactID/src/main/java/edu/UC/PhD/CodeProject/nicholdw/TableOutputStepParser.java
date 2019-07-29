/**
 * Bill Nicholson nicholdw@ucmail.uc.edu
 * Dippy Agarwal dippyaggarwal@gmail.com
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

public class TableOutputStepParser {

	public static TableOutputStep parseXMLForTableOutputStep(Document doc, XPath xpath, String stepname, String xmlFilePath, String fileName, String etlStage) {
		TableOutputStep stepObject = null;

		String transname = getTransformationName(doc, xpath);

		String dbname = getDatabaseName(doc, xpath, stepname);
		String tableName = getTableName(doc, xpath, stepname);
		String schemaName = getSchemaName(doc, xpath, stepname);

		List<String> fieldnames = getDatabaseFieldNames(doc, xpath, stepname);

		stepObject = new TableOutputStep(transname, stepname, "TableOutput", dbname, tableName, fieldnames, xmlFilePath, fileName, etlStage, schemaName);

		return stepObject;
	}

	private static String getDatabaseName(Document doc, XPath xpath,
			String stepname) {
		String connectionname = null;
		String dbname = null;

		try {
			// create XPathExpression object
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"
					+ stepname + "\']/connection/text()");
			connectionname = (String) expr.evaluate(doc, XPathConstants.STRING);

			XPathExpression dbexpr = xpath
					.compile("/transformation/connection[name='"
							+ connectionname + "']/database/text()");
			dbname = (String) dbexpr.evaluate(doc, XPathConstants.STRING);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return dbname;
	}

	private static List<String> getDatabaseFieldNames(Document doc,
			XPath xpath, String stepname) {
		List<String> list = new ArrayList<>();
		try {
			// create XPathExpression object
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"
					+ stepname + "\']/fields/field/column_name/text()");
			// evaluate expression result on XML document
			NodeList nodes = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);
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
			XPathExpression expr = xpath
					.compile("/transformation/info/name/text()");
			name = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return name;
	}

	private static String getTableName(Document doc, XPath xpath, String stepname) {
		String name = null;
		try {
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"
					+ stepname + "\']/table/text()");
			name = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return name;
	}
	private static String getSchemaName(Document doc, XPath xpath, String stepname) {
		String name = null;
		try {
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"
					+ stepname + "\']/schema/text()");
			name = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return name;
	}

}
