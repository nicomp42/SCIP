/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
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
import edu.UC.PhD.CodeProject.nicholdw.ExecuteSQLScriptStep;
import edu.UC.PhD.CodeProject.nicholdw.TableOutputStep;
import edu.UC.PhD.CodeProject.nicholdw.TableInputStep;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryAttribute;
import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.queryParserANTLR4.QueryParser;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;;

public class ETLExcelExporter {

	public static void generateOutputStepsCsvFile(String sFileName, List<TableOutputStep> steps) {
		try {
			boolean fileExists = false;
			Log.logProgress("ETLExcelExporter.generateOutputStepsCsvFile(): Exporting to CSV");
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
				writer.append("ETLStage");
				writer.append('\n');
			}
 			for (TableOutputStep stepObj : steps) {
				Log.logProgress("ETLExcelExporter.generateOutputStepsCsvFile(): " + stepObj.getStepName());
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
					writer.append(',');
					writer.append(stepObj.getEtlStage());
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
	 * @param steps The ETL input steps
	 */
	public static void generateInputStepsCsvFile(String sFileName, List<TableInputStep> steps) {
		try {
			boolean fileExists = false;
			Log.logProgress("ETLExcelExporter.generateInputStepsCsvFile(): Exporting to CSV");
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
				writer.append(',');
				writer.append("ETLStage");
				writer.append('\n');
			}

			for (TableInputStep stepObj : steps) {
				Log.logProgress("ETLExcelExporter.generateInputStepsCsvFile(): Step " + stepObj.getStepName());			
				QueryDefinition qd = new QueryDefinition("", "", "", new QueryTypeSelect(), stepObj.getTransName() + ":" + stepObj.getStepName(), stepObj.getSql(), stepObj.getDbName());		// Query Name, SQL, Schema Name
				QueryParser qp = new QueryParser();
				qd.crunchIt();

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
				writer.append(',');
				writer.append(stepObj.getEtlStage());			// Schema name
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateInputStepsCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}

	public static void generateDBJoinCsvFile(String sFileName, List<DBJoinStep> steps) {
		try {
			boolean fileExists = false;
			Log.logProgress("ETLExcelExporter.generateDBJoinCsvFile(): Exporting to CSV");
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
				writer.append(',');
				writer.append("ETLStage");

				writer.append('\n');
			}
			for (DBJoinStep stepObj : steps) {
				Log.logProgress("ETLExcelExporter.generateDBJoinCsvFile(): Step " + stepObj.getStepName());			

				writer.append(stepObj.getTransName().trim());
				writer.append(',');
				writer.append(stepObj.getStepName().trim());
				writer.append(',');
				writer.append("DBJoin");
				writer.append(',');
				writer.append(stepObj.getDbName().trim());
				writer.append(',');
				writer.append("\"" + stepObj.getSql().replaceAll("[\\t\\n\\r\\s]", " ").trim() + "\"");
//				writer.append(',');
//				writer.append(stepObj.getEtlStage());
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateDBJoinCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}
	public static void generateExecuteSQLScriptCsvFile(String sFileName, List<ExecuteSQLScriptStep> steps) {
		try {
			boolean fileExists = false;
			Log.logProgress("ETLExcelExporter.generateExecuteSQLScriptCsvFile(): Exporting to CSV");
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
				writer.append(',');
				writer.append("ETLStage");

				writer.append('\n');
			}
			for (ExecuteSQLScriptStep stepObj : steps) {
				Log.logProgress("generateExecuteSQLScriptCsvFile.generateDBJoinCsvFile(): Step " + stepObj.getStepName());			

				writer.append(stepObj.getTransName().trim());
				writer.append(',');
				writer.append(stepObj.getStepName().trim());
				writer.append(',');
				writer.append("DBJoin");
				writer.append(',');
				writer.append(stepObj.getDbName().trim());
				writer.append(',');
				writer.append("\"" + stepObj.getSQL().replaceAll("[\\t\\n\\r\\s]", " ").trim() + "\"");
				writer.append(',');
				writer.append(stepObj.getEtlStage());
				writer.append('\n');
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateExecuteSQLScriptCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}

public static void generateExecuteSQLScriptAttributesCsvFile(String sFileName, List<ExecuteSQLScriptStep> steps) {
	try {
		boolean fileExists = false;
		Log.logProgress("ETLExcelExporter.generateExecuteSQLScriptCsvFile(): Exporting to CSV");
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
			writer.append("ETLStage");
			writer.append('\n');
		}
		for (ExecuteSQLScriptStep stepObj : steps) {
			Log.logProgress("generateExecuteSQLScriptCsvFile.generateDBJoinCsvFile(): Step " + stepObj.getStepName());			
			// Parse the SQL and append the table attributes referenced in that SQL to some CSV file
			QueryDefinition qd = new QueryDefinition("", "", "", new QueryTypeSelect(), stepObj.getTransName() + ":" + stepObj.getStepName(), stepObj.getSQL(), stepObj.getDbName());		// Query Name, SQL, Schema Name
			qd.crunchIt();
			// Now we have all the attributes from the SQL in this ETL step.

			for(QueryAttribute qa: qd.getQueryAttributes()) {
				writer.append(stepObj.getTransName().trim());
				writer.append(',');
				writer.append(stepObj.getStepName().trim());
				writer.append(',');
				writer.append("ExecuteETLScript");
				writer.append(',');
				writer.append(qa.getSchemaName());
				writer.append(',');
				writer.append(qa.getContainerName());
				writer.append(',');
				writer.append(qa.getAttributeName());
				writer.append(',');
				writer.append(stepObj.getEtlStage());
				writer.append('\n');
			}
		}
		writer.flush();
		writer.close();
	} catch (IOException e) {
		Log.logError("ETLExcelExporter.generateExecuteSQLScriptCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
	}
}
	public static void generateDBLookupCsvFile(String sFileName, List<DBLookupStep> steps, String etlStage) {
		try {
			boolean fileExists = false;
			Log.logProgress("ETLExcelExporter.generateDBLookupCsvFile(): Exporting to Excel");			
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
				writer.append("ETLStage");
				writer.append('\n');
			}
			for (DBLookupStep stepObj : steps) {
				Log.logProgress("ETLExcelExporter.generateDBLookupCsvFile(): Step " + stepObj.getStepName());			
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
					writer.append(',');
					writer.append(stepObj.getEtlStage());
					writer.append('\n');
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateDBLookupCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}

	public static void generateDimLookupUpdateCsvFile(String sFileName,	List<DimLookupUpdateStep> steps) {
		try {
			boolean fileExists = false;
			Log.logProgress("ETLExcelExporter.generateDimLookupUpdateCsvFile(): Exporting to Excel");			
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
				writer.append(',');
				writer.append("ETLStage");
				writer.append('\n');
			}
			for (DimLookupUpdateStep stepObj : steps) {
				Log.logProgress("ETLExcelExporter.generateDimLookupUpdateCsvFile(): Step " + stepObj.getStepName());			
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
					if (stepObj.isUpdateStep() == true) {
						writer.append("Update");
					} else {
						writer.append("Lookup");
					}
					writer.append(',');
					writer.append(stepObj.getEtlStage());
					writer.append('\n');
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			Log.logError("ETLExcelExporter.generateDimLookupUpdateCsvFile: " + e.getLocalizedMessage(), e.getStackTrace());
		}
	}

	public static void generateCombLookupUpdateCsvFile(String sFileName, List<CombinationLookupUpdateStep> steps) {
		try {
			boolean fileExists = false;
			Log.logProgress("ETLExcelExporter.generateCombLookupUpdateCsvFile(): Exporting to Excel");			
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
				writer.append("ETLStage");
				writer.append('\n');
			}

			for (CombinationLookupUpdateStep stepObj : steps) {
				Log.logProgress("ETLExcelExporter.generateCombLookupUpdateCsvFile(): Step " + stepObj.getStepName());			
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
					writer.append(',');
					writer.append(stepObj.getEtlStage());
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
