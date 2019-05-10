package edu.UC.PhD.CodeProject.nicholdw.query;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class QueryXMLParser {

	public List<Query> parseXML(String xmlFilePath){

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		List<Query> queryObjs=new ArrayList<Query>();
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFilePath);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			List<String> listOfAllFileNames=getFileNamesByType(xpath,doc);

			for(String f:listOfAllFileNames){
				System.out.println(f);
				queryObjs.addAll(buildQueryObject(xpath,doc, f));
			}
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return queryObjs;
	}

	private List<Query> buildQueryObject(XPath xpath, Document doc, String filename){
		List<String> queryInfoList=new ArrayList<String>();
		List<Query> listOfObjects=new ArrayList<Query>();
		try {
			String attrExpr="/columnImpactResult/file[@name ='"+filename+"']"
					+ "/targetColumn/sourceColumn/@name";
			String relExpr="/columnImpactResult/file[@name ='"+filename+"']"
					+ "/targetColumn/sourceColumn/@tableName";
			String dbExpr="/columnImpactResult/file[@name ='"+filename+"']"
					+ "/targetColumn/sourceColumn/@tableOwner";
			XPathExpression expr = xpath.compile(attrExpr);
			XPathExpression expr2 = xpath.compile(relExpr);
			XPathExpression db_expr = xpath.compile(dbExpr);
			NodeList attr_nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			NodeList rel_nodes = (NodeList) expr2.evaluate(doc, XPathConstants.NODESET);
			NodeList db_nodes = (NodeList) db_expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < attr_nodes.getLength(); i++){
				String attributeName=attr_nodes.item(i).getNodeValue();
				String relName=rel_nodes.item(i).getNodeValue();
				String dbName=db_nodes.item(i).getNodeValue();
				String queryObjStr=filename+","+dbName+","+relName+","+attributeName;
				if(!queryInfoList.contains(queryObjStr)){
					queryInfoList.add(queryObjStr);
					Query obj=new Query();
					obj.setQueryLabel(filename);
					obj.setAttribute(attributeName);
					obj.setRelationName(relName);
					obj.setDbName(dbName);
					listOfObjects.add(obj);
				}
			}
		}catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return listOfObjects;
	}

	private List<String> getFileNamesByType(XPath xpath, Document doc){
		List<String> fileNames=new ArrayList<String>();
		try {
			XPathExpression expr = xpath.compile("/columnImpactResult/file/@name");
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++){
				String completeFileName=nodes.item(i).getNodeValue();
				fileNames.add(completeFileName);
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return fileNames;
	}
}
