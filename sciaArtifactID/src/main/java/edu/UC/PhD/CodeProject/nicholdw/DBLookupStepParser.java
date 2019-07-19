package edu.UC.PhD.CodeProject.nicholdw;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DBLookupStepParser {

	public static DBLookupStep parseXMLByStepName(Document doc, XPath xpath, String stepname, String etlStage) {

		DBLookupStep stepObject = null;
		String transname = getTransformationName(doc, xpath);
		String dbname = getDatabaseName(doc, xpath, stepname);
		String tablename = getTableName(doc, xpath, stepname);
		List<String> fieldnames = getDatabaseFieldNames(doc, xpath, stepname);

		stepObject = new DBLookupStep(transname, stepname, dbname, tablename, fieldnames, etlStage);

		return stepObject;

	}

	private static List<String> getDatabaseFieldNames(Document doc,
			XPath xpath, String stepname) {
		List<String> list = new ArrayList<String>();
		try {
			// create XPathExpression object
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"
					+ stepname + "\']/lookup/key/field/text()");
			// evaluate expression result on XML document
			NodeList nodes = (NodeList) expr.evaluate(doc,
					XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++)
				list.add(nodes.item(i).getNodeValue());

			XPathExpression return_expr = xpath
					.compile("/transformation/step[name=\'" + stepname
							+ "\']/lookup/value/name/text()");
			// evaluate expression result on XML document
			NodeList return_nodes = (NodeList) return_expr.evaluate(doc,
					XPathConstants.NODESET);
			for (int i = 0; i < return_nodes.getLength(); i++)
				list.add(return_nodes.item(i).getNodeValue());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return list;
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

	private static String getTableName(Document doc, XPath xpath,
			String stepname) {

		String tablename = null;

		try {
			// create XPathExpression object
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"
					+ stepname + "\']/lookup/table/text()");
			tablename = (String) expr.evaluate(doc, XPathConstants.STRING);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return tablename;
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

}
