package edu.UC.PhD.CodeProject.nicholdw;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class CombinationLookupStepParser {

	public static CombinationLookupUpdateStep parseXMLForCombinationLookupStep(
			Document doc, XPath xpath, String stepName) {
		CombinationLookupUpdateStep stepObject = null;
		String transname = getTransformationName(doc, xpath);
		String dbname = getDatabaseName(doc, xpath, stepName);
		String tableName = getTableName(doc, xpath, stepName);
		List<String> fieldnames = getDatabaseFieldNames(doc, xpath, stepName);
		stepObject = new CombinationLookupUpdateStep(transname, stepName,
				"CombinationLookup", dbname, tableName, fieldnames);
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

			XPathExpression expr_surrogate_key = xpath
					.compile("/transformation/step[name=\'" + stepname
							+ "\']/fields/return/name/text()");
			XPathExpression expr_dbfields = xpath
					.compile("/transformation/step[name=\'" + stepname
							+ "\']/fields/key/lookup/text()");
			// evaluate expression result on XML document
			NodeList nodes = (NodeList) expr_dbfields.evaluate(doc,
					XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++)
				list.add(nodes.item(i).getNodeValue());

			String surrogatekey_field = (String) expr_surrogate_key.evaluate(
					doc, XPathConstants.STRING);

			if (surrogatekey_field != null && !surrogatekey_field.isEmpty())
				list.add(surrogatekey_field);

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

	private static String getTableName(Document doc, XPath xpath,
			String stepname) {
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

	private static String getStepName(Document doc, XPath xpath) {
		String name = null;
		try {
			XPathExpression expr = xpath
					.compile("/transformation/step[type='DimensionLookup']/name/text()");
			name = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return name;
	}

}
