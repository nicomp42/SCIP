package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.Utils;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ETLTransformationFile {
	private String fileName;	// No Path. This is just a file name.
//	public enum enumETLType {ODS_IDS, IDS_DW, Undefined};
//	private enumETLType etlType;
	private String etlStage;
	public final static String etlStageUnknown = "Unknown";
	public final static String etlStageOPS_IDS = "OPS-IDS";
	public final static String etlStageIDS_DW = "IDS-DW";
	
	public ETLTransformationFile(String fileName, String etlStage) {
		setFileName(fileName);
		setEtlStage(etlStage);
	}
	/***
	 * Copy Constructor
	 * @param etlTransformationFile
	 */
	public ETLTransformationFile(ETLTransformationFile etlTransformationFile) {
		setFileName(etlTransformationFile.getFileName());
		setEtlStage(etlTransformationFile.getEtlStage());
	}
	public String getFileName() {
		return fileName;
	}
	/**
	 * Path is removed if there is one. Only the file name is stored.
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = Utils.getFilenameWithoutPath(fileName);
	}
/*	public enumETLType getEtlType() {
		return etlType;
	}
	public void setEtlType(enumETLType etlType) {
		this.etlType = etlType;
	} */
	public static void loadTableViewWithTransformationFileNames(TableView<gui.GUIetlTransformationFile> tableView, ETLTransformationFiles etlTransformationFiles) {
        ObservableList<gui.GUIetlTransformationFile> data = tableView.getItems();
        data.clear();
        for (ETLTransformationFile etlTransformationFile : etlTransformationFiles) {
   	        data.add(new gui.GUIetlTransformationFile(etlTransformationFile.getFileName(), etlTransformationFile.getEtlStage() ));
   		}
	}
	public String getEtlStage() {
		return etlStage;
	}
	public void setEtlStage(String etlStage) {
		this.etlStage = etlStage;
	}
}
