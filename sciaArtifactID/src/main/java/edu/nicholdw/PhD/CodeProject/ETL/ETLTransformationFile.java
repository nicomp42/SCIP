package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.Utils;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ETLTransformationFile {
	private String fileName;	// No Path. This is just a file name.
	private int etlStageNumber;
//	public enum enumETLType {ODS_IDS, IDS_DW, Undefined};
//	private enumETLType etlType;
//	private String etlStage;
	public final static String etlStageUnknown = "Unknown";
	public final static String etlStageOPS_IDS = "OPS-IDS";
	public final static String etlStageIDS_DW = "IDS-DW";
	public final static String[] etlStageList = new String[] { etlStageUnknown, etlStageOPS_IDS, etlStageIDS_DW};
	
	public ETLTransformationFile(String fileName, int etlStageNumber) {
		setFileName(fileName);
		setEtlStageNumber(etlStageNumber);
	}
	public ETLTransformationFile(String fileName) {
		setFileName(fileName);
		setEtlStageNumber(0);
	}
	/***
	 * Copy Constructor
	 * @param etlTransformationFile
	 */
	public ETLTransformationFile(ETLTransformationFile etlTransformationFile) {
		setFileName(etlTransformationFile.getFileName());
		setEtlStageNumber(etlTransformationFile.getEtlStageNumber());
	}
	/**
	 * Is the ETL Stage 'unknown' ?
	 * @param etlStage True if unknown, false otherwise
	 * @return
	 */
	public static Boolean isStageUnknown(String etlStage) {
		return etlStage.trim().toLowerCase().equals(etlStageUnknown.toLowerCase());
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
		return etlStageList[etlStageNumber];
	}
//	public void setEtlStage(String etlStage) {
//		this.etlStage = etlStage;
//	}
	public int getEtlStageNumber() {
		return etlStageNumber;
	}
	public void setEtlStageNumber(int etlStageNumber) {
		this.etlStageNumber = etlStageNumber;
	}
	public String incrementETLStageNumber() {
		etlStageNumber++;
		if (etlStageNumber >= etlStageList.length) {etlStageNumber = 0;}
		return getEtlStage();
	}
	/**
	 * If you have a stage and want to know the next stage in the rotation.
	 * This method supports the ETL Processing GUI because it has controls that display ETL Transformation File objects
	 * @param etlStage Whatever your current stage is
	 * @return The next etlStage in the rotation
	 */
	public static String getNextETLStage(String etlStage) {
		int idx = 0;
		for (idx = 0; idx < etlStageList.length; idx++) {
			if (etlStageList[idx].toUpperCase().equals(etlStage.toUpperCase())) {
				break;
			}
		}
		if (idx < etlStageList.length) {
			// We found a match
			idx++;
			if (idx >= etlStageList.length) {idx = 0;}
		}
		return etlStageList[idx];
	}
}
