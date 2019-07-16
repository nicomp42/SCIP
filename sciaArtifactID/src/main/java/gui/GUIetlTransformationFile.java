package gui;
import javafx.beans.property.SimpleStringProperty;

public class GUIETLTransformationFile {
	// These field names must match the cellValueFactory names in the fxml file!
//	<cellValueFactory>
//        <PropertyValueFactory property="name" />
//  </cellValueFactory>
	private final SimpleStringProperty fileName = new SimpleStringProperty("");

	public GUIETLTransformationFile() {
        this("");
	}

    public GUIETLTransformationFile(String fileName) {
    	setFileName(fileName);
    }

	public SimpleStringProperty getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName.set(fileName);
	}

}
