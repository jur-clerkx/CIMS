/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import Field_Operations.Task;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Jur
 */
public class FXMLShowTaskInfoController implements Initializable {
    @FXML
    private GridPane TaskInfo;
    @FXML
    private TextField textFieldTaskID;
    @FXML
    private TextField textFieldTaskName;
    @FXML
    private ListView listViewTaskUnits;
    @FXML
    private TextField textFieldTaskLocation;
    @FXML
    private TextField textFieldTaskUrgency;
    @FXML
    private TextField textFieldTaskStatus;
    @FXML
    private TextArea textAreaTaskDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Task currentTask = CIMSFieldOperationsUnitApp.taskInfo;
        if(currentTask != null) {
            textFieldTaskID.setText(currentTask.getTaskID() + "");
            textFieldTaskName.setText(currentTask.getName());
            textFieldTaskStatus.setText(currentTask.getStatus());
            textFieldTaskLocation.setText(currentTask.getLocation());
            textFieldTaskUrgency.setText(currentTask.getUrgency());
            textAreaTaskDescription.setText(currentTask.getDescription());
            listViewTaskUnits.getItems().clear();
            listViewTaskUnits.getItems().add(currentTask.getUnits());
        }
    }    
    
}
