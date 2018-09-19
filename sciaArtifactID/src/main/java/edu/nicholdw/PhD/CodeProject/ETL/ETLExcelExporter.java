// Be sure to care about this: https://superuser.com/questions/52157/how-do-i-get-excel-to-import-a-csv-file-with-commas-in-some-of-the-content-field
// The Excel parser can't handle spaces after commas in the CSV file!

package edu.nicholdw.PhD.CodeProject.ETL;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.UC.PhD.CodeProject.nicholdw.CombinationLookupUpdateStep;
import edu.UC.PhD.CodeProject.nicholdw.DBJoinStep;
import edu.UC.PhD.CodeProject.nicholdw.DBLookupStep;
import edu.UC.PhD.CodeProject.nicholdw.DimLookupUpdateStep;
import edu.UC.PhD.CodeProject.nicholdw.OutputStep;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.QueryParser;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;;

public class ETLExcelExporter {

	public static void generateOutputStepsCsvFile(String sFileName, List<OutputStep> steps) {
		try {
			boolean fileExists = false;
			Log.logProgress("ETLExcelExporter.generateOutputStepsCsvFile(): Exporting to Excel");
			File f = new File(sFileName);
			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(sFileName, true);
			if (!fileExists) {
				writer.append("TransformationName");
				writer.append(',');
				writer.append("StepName");
				writer.append(',');
				writer.append("StepType");
				writer.append(',');
				writer.append("DatabaseName");
				writer.append(',');
				writer.append("TableName");
				writer.append(',');
				writer.append("AttributeName");
				writer.append('\n');
			}
 			for (OutputStep stepObj : steps) {
				Log.logProgress("ETLExcelExporter.generateOutputStepsCsvFile(): " + stepObj);
				for (int index = 0; index < stepObj.getAttributes().size(); index++) {
					writer.append(stepObj.getTransName());
					writer.append(',');
					writer.append(stepObj.getStepName());
					writer.append(',');
					writer.append(stepObj.getStepType());
					writer.append(',');
					writer.append(stepObj.getDbName());
					writer.append(',');
					writer.append(stepObj.getTableName());
					writer.append(',');
					writer.append(stepObj.getAttributes().get(index));
					writer.append('\n');
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateOutputStepsCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}
	/**
	 * Create the CSV files for ETL input steps
	 * @param sFileName The target file for the output
	 * @param steps The
	 */
	public static void generateInputStepsCsvFile(String sFileName, List<TableInputStep> steps) {
		try {
			boolean fileExists = false;
			System.out.println("Exporting to Excel");
			File f = new File(sFileName);
			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(sFileName, true);
			if (!fileExists) {

				writer.append("TransformationName");
				writer.append(',');
				writer.append("StepName");
				writer.append(',');
				writer.append("DatabaseName");
				writer.append(',');
				writer.append("SQL");

				writer.append('\n');
			}

			for (TableInputStep stepObj : steps) {
				// TODO need to parse this query
				QueryDefinition qd = new QueryDefinition("", "", "", new QueryTypeSelect(), stepObj.getTransName() + ":" + stepObj.getStepName(), stepObj.getSql(), stepObj.getDbName());		// Query Name, SQL, Schema Name
				QueryParser qp = new QueryParser();
				qp.parseQuery(qd);

				// After it's parsed, we need to store it somewhere in a CSV file, including each attribute.
				writer.append(stepObj.getTransName().trim());
				writer.append(',');
				writer.append(stepObj.getStepName().trim());
				writer.append(',');

				writer.append(stepObj.getDbName().trim());			// Schema name
				writer.append(',');
				// TODO this SQL will need to be parsed. As it currently works, it doesn't work.
				String tmp = "\"" + stepObj.getSql().replaceAll("[\\t\\n\\r\\s]", " ").trim() + "\"";
				writer.append(tmp);
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateInputStepsCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}

	public static void generateDBJoinCsvFile(String sFileName,
			List<DBJoinStep> steps) {
		try {
			boolean fileExists = false;
			System.out.println("Exporting to Excel");
			File f = new File(sFileName);
			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(sFileName, true);
			if (!fileExists) {

				writer.append("TransformationName");
				writer.append(',');
				writer.append("StepName");
				writer.append(',');
				writer.append("StepType");
				writer.append(',');
				writer.append("DatabaseName");
				writer.append(',');
				writer.append("SQL");

				writer.append('\n');
			}

			for (DBJoinStep stepObj : steps) {
				// System.out.println("ExcelExporter:"+stepObj);

				writer.append(stepObj.getTransName().trim());
				writer.append(',');
				writer.append(stepObj.getStepName().trim());
				writer.append(',');
				writer.append("DBJoin");
				writer.append(',');

				writer.append(stepObj.getDbName().trim());
				writer.append(',');

				writer.append("\"" + stepObj.getSql().replaceAll("[\\t\\n\\r\\s]", " ").trim() + "\"");
				writer.append('\n');

			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateDBJoinCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}

	public static void generateDBLookupCsvFile(String sFileName,
			List<DBLookupStep> steps) {
		try {
			boolean fileExists = false;
			System.out.println("Exporting to Excel");
			File f = new File(sFileName);
			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(sFileName, true);
			if (!fileExists) {

				writer.append("TransformationName");
				writer.append(',');
				writer.append("StepName");
				writer.append(',');
				writer.append("StepType");
				writer.append(',');
				writer.append("DatabaseName");
				writer.append(',');
				writer.append("TableName");
				writer.append(',');
				writer.append("AttributeName");
				writer.append('\n');
			}

			for (DBLookupStep stepObj : steps) {
				System.out.println("\tExcelExporter:" + stepObj);

				for (int index = 0; index < stepObj.getAttributes().size(); index++) {

					writer.append(stepObj.getTransName());
					writer.append(',');
					writer.append(stepObj.getStepName());
					writer.append(',');
					writer.append("DBLookup");
					writer.append(',');
					writer.append(stepObj.getDbName());
					writer.append(',');
					writer.append(stepObj.getTableName());
					writer.append(',');
					writer.append(stepObj.getAttributes().get(index));
					writer.append('\n');

				}

			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateDBLookupCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}

	public static void generateDimLookupUpdateCsvFile(String sFileName,
			List<DimLookupUpdateStep> steps) {
		try {
			boolean fileExists = false;
			System.out.println("Exporting to Excel");
			File f = new File(sFileName);
			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(sFileName, true);
			if (!fileExists) {

				writer.append("TransformationName");
				writer.append(',');
				writer.append("StepName");
				writer.append(',');
				writer.append("StepType");
				writer.append(',');
				writer.append("DatabaseName");
				writer.append(',');
				writer.append("TableName");
				writer.append(',');
				writer.append("AttributeName");
				writer.append(',');
				writer.append("isUpdateStep");
				writer.append('\n');
			}

			for (DimLookupUpdateStep stepObj : steps) {
				System.out.println("\tExcelExporter:" + stepObj);

				for (int index = 0; index < stepObj.getAttributes().size(); index++) {

					writer.append(stepObj.getTransName());
					writer.append(',');
					writer.append(stepObj.getStepName());
					writer.append(',');
					writer.append("DimensionLookup");
					writer.append(',');
					writer.append(stepObj.getDbName());
					writer.append(',');
					writer.append(stepObj.getTableName());
					writer.append(',');
					writer.append(stepObj.getAttributes().get(index));
					writer.append(',');
					if (stepObj.isUpdateStep() == true)
						writer.append("Update");
					else
						writer.append("Lookup");
					writer.append('\n');

				}

			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateDimLookupUpdateCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}

	public static void generateCombLookupUpdateCsvFile(String sFileName,
			List<CombinationLookupUpdateStep> steps) {
		try {
			boolean fileExists = false;
			System.out.println("Exporting to Excel");
			File f = new File(sFileName);
			if (f.exists()) {fileExists = true;}

			FileWriter writer = new FileWriter(sFileName, true);
			if (!fileExists) {

				writer.append("TransformationName");
				writer.append(',');
				writer.append("StepName");
				writer.append(',');
				writer.append("StepType");
				writer.append(',');
				writer.append("DatabaseName");
				writer.append(',');
				writer.append("TableName");
				writer.append(',');
				writer.append("AttributeName");
				writer.append('\n');
			}

			for (CombinationLookupUpdateStep stepObj : steps) {
				System.out.println("\tExcelExporter:" + stepObj);

				for (int index = 0; index < stepObj.getAttributes().size(); index++) {

					writer.append(stepObj.getTransName());
					writer.append(',');
					writer.append(stepObj.getStepName());
					writer.append(',');
					writer.append(stepObj.getStepType());
					writer.append(',');
					writer.append(stepObj.getDbName());
					writer.append(',');
					writer.append(stepObj.getTableName());
					writer.append(',');
					writer.append(stepObj.getAttributes().get(index));
					writer.append('\n');

				}

			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateCombLookupUpdateCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}
}
