package edu.UC.PhD.CodeProject.nicholdw.dwQuery;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.Query;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttributes;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;

public class QueryExcelExporter {

	/**
	 * Process a collection of query attributes into a CSV file
	 * @param fileName The destination file of the CSV file, with path if necessary
	 * @param queryObjs The collection of query attributes
	 */
	public static void generateCsvFile(String fileName, QueryDefinition queryDefinition) {
		try {
			boolean fileExists = false;
			Log.logProgress("QueryExcelExporter.generateCsvFile(): Exporting to Excel");
			File f = new File(fileName);
			if (f.exists()) {
				Log.logProgress("QueryExcelExporter.generateCsvFile(): File exists\n" + f.getAbsolutePath());
				fileExists = true;
			} else {
				Log.logProgress("QueryExcelExporter.generateCsvFile(): File does not exist\n" + f.getAbsolutePath());
			}
			FileWriter writer = new FileWriter(fileName, true);
			if (!fileExists) {
				writer.append("QueryLabel");
				writer.append(',');
				writer.append("AttributeName");
				writer.append(',');
				writer.append("RelationName");
				writer.append(',');
				writer.append("DatabaseName");
				writer.append('\n');
			}
			QueryAttributes queryAttributes = queryDefinition.getQueryAttributes();
			for (QueryAttribute queryAttribute : queryAttributes) {
//				Log.logProgress("QueryExcelExporter.generateCsvFile(): ExcelExporter:" + q);
				String queryTitle = queryDefinition.getQueryName();
				try {
					writer.append(queryTitle.substring(queryTitle.lastIndexOf('\\') + 1, queryTitle.lastIndexOf('.'))); // Strip off the path name and extension, if any.
				} catch (Exception ex) {
					writer.append(queryTitle.toLowerCase());
				}
				writer.append(',');
				writer.append(queryAttribute.getAttributeName().toLowerCase());
				writer.append(',');
				writer.append(queryAttribute.getContainerName().toLowerCase());
				writer.append(',');
				writer.append(queryAttribute.getSchemaName().toLowerCase());
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("QueryExcelExporter.generateCsvFile(): " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}
}
