package edu.UC.PhD.CodeProject.nicholdw.dwQuery;

import java.io.File;
import java.util.List;

import edu.UC.PhD.CodeProject.nicholdw.query.Query;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryXMLParser;

public class DWQueryToCSV {
	public void generateCSVFile(String src_folder, String target){
		deleteCSVFileIfExists();
		QueryXMLParser xmlparser=new QueryXMLParser();
		List<Query> queryObjs=xmlparser.parseXML(src_folder+"\\queries-xml.xml");
		//QueryExcelExporter.generateCsvFile(target, queryObjs);
	}

	public void deleteCSVFileIfExists(){
		File f = new File("C:\\Users\\usplib\\workspace\\ImpactAssessmentProject\\"	+ "csvfiles\\queries.csv");
		if (f.isFile()) {
			f.delete();
		}
	}
}
