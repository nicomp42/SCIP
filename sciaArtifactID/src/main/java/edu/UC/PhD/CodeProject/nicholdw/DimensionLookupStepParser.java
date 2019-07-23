package edu.UC.PhD.CodeProject.nicholdw;
import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

public class DimensionLookupStepParser {

	public static DimLookupUpdateStep parseXMLForDimensionLookupStep(Document doc, XPath xpath, String stepName, String etlStage) {
		DimLookupUpdateStep stepObject = null;

		String transname = getTransformationName(doc, xpath);

		String dbname = getDatabaseName(doc, xpath, stepName);
		String tableName = getTableName(doc, xpath, stepName);

		List<String> fieldnames = getDatabaseFieldNames(doc, xpath, stepName);
		boolean isUpdateStep = getStepUpdateStatus(doc, xpath, stepName);

		stepObject = new DimLookupUpdateStep(transname, stepName, "Dimension Lookup", dbname, tableName, fieldnames, isUpdateStep, etlStage);

		return stepObject;
	}

	private static boolean getStepUpdateStatus(Document doc, XPath xpath, String stepname) {
		boolean updateStatus = false;
		try {
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"	+ stepname + "\']/update/text()");
			String updateStatus_str = (String) expr.evaluate(doc, XPathConstants.STRING);
			updateStatus = (updateStatus_str.equals("Y") ? true : false);
			Log.logProgress("DimensionLookupStepParser.getStepUpdateStatus(): It is an update step:" + updateStatus);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return updateStatus;
	}

	private static String getDatabaseName(Document doc, XPath xpath, String stepname) {
		String connectionname = null;
		String dbname = null;

		try {
			// create XPathExpression object
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"	+ stepname + "\']/connection/text()");
			connectionname = (String) expr.evaluate(doc, XPathConstants.STRING);

			XPathExpression dbexpr = xpath.compile("/transformation/connection[name='" + connectionname + "']/database/text()");
			dbname = (String) dbexpr.evaluate(doc, XPathConstants.STRING);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return dbname;
	}

	private static List<String> getDatabaseFieldNames(Document doc, XPath xpath, String stepname) {
		List<String> list = new ArrayList<>();
		try {

			XPathExpression expr_lookupkey_field = xpath.compile("/transformation/step[name=\'" + stepname + "\']/fields/key/lookup/text()");
			XPathExpression expr_daterange_start = xpath.compile("/transformation/step[name=\'" + stepname + "\']/fields/date/from/text()");
			XPathExpression expr_daterange_end = xpath.compile("/transformation/step[name=\'" + stepname + "\']/fields/date/to/text()");
			XPathExpression expr_version_field = xpath.compile("/transformation/step[name=\'" + stepname + "\']/fields/return/version/text()");
			XPathExpression expr_surrogate_key = xpath.compile("/transformation/step[name=\'" + stepname + "\']/fields/return/name/text()");
			XPathExpression expr_updateOrInsertFields = xpath.compile("/transformation/step[name=\'" + stepname	+ "\']/fields/field/lookup/text()");
			// evaluate expression result on XML document
			NodeList nodes = (NodeList) expr_updateOrInsertFields.evaluate(doc,	XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {list.add(nodes.item(i).getNodeValue());}
			String lookupkey_field = (String) expr_lookupkey_field.evaluate(doc, XPathConstants.STRING);
			String daterange_start_field = (String) expr_daterange_start.evaluate(doc, XPathConstants.STRING);
			String daterange_end_field = (String) expr_daterange_end.evaluate(doc, XPathConstants.STRING);
			String version_field = (String) expr_version_field.evaluate(doc, XPathConstants.STRING);
			String surrogatekey_field = (String) expr_surrogate_key.evaluate(doc, XPathConstants.STRING);
			// for (int i = 0; i < nodes.getLength(); i++)
			// list.add(nodes.item(i).getNodeValue());
			if (lookupkey_field != null && !lookupkey_field.isEmpty()) {list.add(lookupkey_field);}
			if (daterange_start_field != null && !daterange_start_field.isEmpty()) {list.add(daterange_start_field);}
			if (daterange_end_field != null && !daterange_end_field.isEmpty()) {list.add(daterange_end_field);}
			if (version_field != null && !version_field.isEmpty()) {list.add(version_field);}
			if (surrogatekey_field != null && !surrogatekey_field.isEmpty()) {list.add(surrogatekey_field);}

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

	private static String getTableName(Document doc, XPath xpath,
			String stepname) {
		String name = null;
		try {
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"	+ stepname + "\']/table/text()");
			name = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return name;
	}

	private static String getStepName(Document doc, XPath xpath) {
		String name = null;
		try {
			XPathExpression expr = xpath.compile("/transformation/step[type='DimensionLookup']/name/text()");
			name = (String) expr.evaluate(doc, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return name;
	}

}
