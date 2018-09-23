package gui;
import edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformation;
import edu.UC.PhD.CodeProject.nicholdw.database.ConnectionInformations;
import edu.nicholdw.PhD.CodeProject.ETL.ETLStep;
import edu.nicholdw.PhD.CodeProject.ETL.ETLSteps;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/***
 * ETL Step Names Information for the TableView control in the ProcessETL GUI
 * @author nicomp
 *
 */
public class GUIETLStep {

	private final SimpleStringProperty stepName = new SimpleStringProperty("");
	private final SimpleStringProperty stepType = new SimpleStringProperty("");
	
	public GUIETLStep() {
        this("", "");
	}

    public GUIETLStep(String stepName, String stepType) {
    	setStepName(stepName);
    	setStepType(stepType);
    }

	public SimpleStringProperty getStepName() {
		return stepName;
	}

	public SimpleStringProperty getStepType() {
		return stepType;
	}	
	
	public void setStepType(String stepType) {
		this.stepType.set(stepType);
	}
	public void setStepName(String stepName) {
		this.stepName.set(stepName);
	}
	/***
	 * Load the TableView with ETL Steps
	 * @param tableView The TableView to be loaded
	 * @param etlSteps The set of ETL steps
	 */
	public static void loadTableViewWithSteps(TableView<gui.GUIETLStep> tableView, ETLSteps etlSteps) {
        ObservableList<gui.GUIETLStep> data = tableView.getItems();
   		for (ETLStep etlStep : etlSteps) {
   	        data.add(new gui.GUIETLStep(etlStep.getStepName(), etlStep.getStepType()));
   		}
    }		
}
