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

import edu.nicholdw.PhD.CodeProject.ETL.DBProcStep;

public class DBProcStepParser {
	
	public static DBProcStep parseXMLByStepName(Document doc, XPath xpath, String stepname, String xmlFilePath, String xmlFilename, String etlStage){
		
		DBProcStep stepObject=null;
		 String transname = getTransformationName(doc, xpath);
         String dbname = getDatabaseName(doc, xpath,stepname);
         String sql=getSQL(doc,xpath, stepname);	                   
         String procedure = getProcedure(doc, xpath,stepname);
         stepObject=new DBProcStep(transname, stepname, dbname, procedure, xmlFilePath, xmlFilename, etlStage);
		return stepObject;
	}

	private static String getDatabaseName(Document doc, XPath xpath, String stepname) {
        String connectionname=null;
        String dbname=null;
        try {
            //create XPathExpression object
            XPathExpression expr = xpath.compile("/transformation/step[name=\'"+stepname+"\']" + "/connection/text()");
            connectionname = (String) expr.evaluate(doc, XPathConstants.STRING);

            XPathExpression dbexpr = xpath.compile("/transformation/" + "connection[name='"+connectionname+"']/database/text()");
            dbname = (String) dbexpr.evaluate(doc, XPathConstants.STRING);

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return dbname;
    }
	
	private static String getSQL(Document doc, XPath xpath, String stepname) {
        String sql=null;
        try {
            //create XPathExpression object
            XPathExpression expr = xpath.compile("/transformation/step[name=\'"+stepname+"\']/sql/text()");
            sql = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return sql;
    }
	private static String getProcedure(Document doc, XPath xpath, String stepname) {
        String sql=null;
        try {
            //create XPathExpression object
            XPathExpression expr = xpath.compile("/transformation/step[name=\'"+stepname+"\']/procedure/text()");
            sql = (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return sql;
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
}
