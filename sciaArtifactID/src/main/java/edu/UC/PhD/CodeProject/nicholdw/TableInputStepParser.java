/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.nicholdw.PhD.CodeProject.ETL.ETLKTRFile;

public class TableInputStepParser {
	public static TableInputStep parseXMLByStepName(Document doc, XPath xpath, String stepname, String xmlFilename, ETLKTRFile etlTransformationFile) {

		TableInputStep stepObject = null;
		String transname = getTransformationName(doc, xpath);
		String dbname = getDatabaseName(doc, xpath, stepname);
		String sql = getSQL(doc, xpath, stepname);
		stepObject = new TableInputStep(transname, stepname, dbname, sql, xmlFilename, etlTransformationFile.getFileName(), etlTransformationFile.getEtlStage());
		return stepObject;
	}

	private static String getDatabaseName(Document doc, XPath xpath, String stepname) {
		String connectionname = null;
		String dbname = null;
		try {
			// Look up the connection name for this step
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"	+ stepname + "\']/connection/text()");
			connectionname = (String) expr.evaluate(doc, XPathConstants.STRING);

			// Use the connection name from above to look up the database name that this step will use
			XPathExpression dbexpr = xpath.compile("/transformation/connection[name='" + connectionname + "']/database/text()");
			dbname = (String) dbexpr.evaluate(doc, XPathConstants.STRING);

		} catch (XPathExpressionException ex) {
			Log.logError("TableInputStepParser.getDatabaseName(); : " + ex.getLocalizedMessage());
		}
		return dbname;
	}

	private static String getSQL(Document doc, XPath xpath, String stepname) {
		String sql = null;

		try {
			// create XPathExpression object
			XPathExpression expr = xpath.compile("/transformation/step[name=\'"	+ stepname + "\']/sql/text()");
			sql = (String) expr.evaluate(doc, XPathConstants.STRING);

		} catch (XPathExpressionException ex) {
			Log.logError("TableInputStepParser.getSQL(); : " + ex.getLocalizedMessage());
		}
		return sql;
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
