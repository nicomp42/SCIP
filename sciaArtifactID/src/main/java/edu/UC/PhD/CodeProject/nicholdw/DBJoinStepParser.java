package edu.UC.PhD.CodeProject.nicholdw;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;


public class DBJoinStepParser {
	
	public static DBJoinStep parseXMLByStepName(Document doc, XPath xpath, String stepname){
		
		DBJoinStep stepObject=null;
		 String transname = getTransformationName(doc, xpath);
         String dbname = getDatabaseName(doc, xpath,stepname);
         String sql=getSQL(doc,xpath, stepname);	            
         stepObject=new DBJoinStep(transname,stepname, dbname, sql);
		return stepObject;
			
	}
	
	private static String getDatabaseName(Document doc, XPath xpath, String stepname) {
        String connectionname=null;
        String dbname=null;
        try {
            //create XPathExpression object
            XPathExpression expr = xpath.compile("/transformation/step[name=\'"+stepname+"\']"
            		              + "/connection/text()");
            connectionname = (String) expr.evaluate(doc, XPathConstants.STRING);
         
            XPathExpression dbexpr = xpath.compile("/transformation/"
            		+ "connection[name='"+connectionname+"']/database/text()");
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
            XPathExpression expr =
                xpath.compile("/transformation/step[name=\'"+stepname+"\']/sql/text()");
            sql = (String) expr.evaluate(doc, XPathConstants.STRING);
                     
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return sql;
    }
	
    private static String getTransformationName(Document doc, XPath xpath) {
	        String name = null;
	        try {
	            XPathExpression expr =
	                xpath.compile("/transformation/info/name/text()");
	            name = (String) expr.evaluate(doc, XPathConstants.STRING);
	        } catch (XPathExpressionException e) {
	            e.printStackTrace();
	        }

	        return name;
	    }
	 
		 
}
