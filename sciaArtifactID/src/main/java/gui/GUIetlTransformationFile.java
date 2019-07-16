/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package gui;
import javafx.beans.property.SimpleStringProperty;

public class GUIetlTransformationFile {
	// These field names must match the cellValueFactory names in the fxml file!
//	<cellValueFactory>
//        <PropertyValueFactory property="name" />
//  </cellValueFactory>
	private final SimpleStringProperty fileName = new SimpleStringProperty("");
	private final SimpleStringProperty etlStage = new SimpleStringProperty("");

	public GUIetlTransformationFile() {
        this("", "");
	}

    public GUIetlTransformationFile(String fileName, String etlType) {
    	setFileName(fileName);
    }

	public String getFileName() {
		return fileName.get();		
	}
	
	public void setFileName(String fileName) {
		this.fileName.set(fileName);
	}

	/***
	 * OPS_IDS or IDS_DW, etc.
	 * @return
	 */
	
	public void setEtlStage(String etlStage) {
		setEtlStage(etlStage);
	}
	public String getEtlStage() {
		return this.etlStage.get();
	}
}
