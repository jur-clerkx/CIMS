/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Task;
import Field_Operations.Unit;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class TaskInfoController implements Initializable {
    @FXML
    private GridPane TaskInfo;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldLocation;
    @FXML
    private Button buttonOK;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonDelete;
    @FXML
    private ComboBox<?> comboboxUrgency;
    @FXML
    private ComboBox<?> comboboxStatus;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private ListView listviewUUnits;
    @FXML
    private ListView listviewAUnits;
    
    ObservableList<Unit> ActiveUnits;
    ObservableList<Unit> InactiveUnits;
    private Task selectedTask;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int ID = ConnectionController.selectedTaskID;
        selectedTask = null;
        // Dummy Data
        selectedTask = new Task(1, "Task 1", "High", "Open", "Eindhoven", "shiiiet");
        
        // TODO: getTaskInfo.
        /*try {
            selectedTask = ConnectionController.getTaskInfo(ID);
        } catch (IOException ex) {
            Logger.getLogger(UnitInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        if(selectedTask != null)
        {
            fillPage();
        }
    }    
    /**
     * Move selected unassigned unit, available unit to the assigned units.
     * @param event 
     */
    @FXML
    private void buttonAdd(MouseEvent event) {
        Unit selectedUnit = (Unit)listviewUUnits.getSelectionModel().getSelectedItem();
        ActiveUnits.add(selectedUnit);
        InactiveUnits.remove(selectedUnit);
        
        selectedTask.addUnit(selectedUnit);
    }
    /**
     * Move selected assigned unit, unavailable unit to the unassigned units.
     * @param event 
     */
    @FXML
    private void buttonRemove(MouseEvent event) {
        Unit selectedUnit = (Unit)listviewAUnits.getSelectionModel().getSelectedItem();
        InactiveUnits.add(selectedUnit);
        ActiveUnits.remove(selectedUnit);
        
        listviewUUnits.setItems(InactiveUnits);
        listviewAUnits.setItems(ActiveUnits);
        
        selectedTask.delUnit(selectedUnit);
    }
    /**
     * Save changes made to task.
     * @param event 
     */
    @FXML
    private void buttonOK(MouseEvent event) throws IOException {
        Task editedTask = selectedTask;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Save changes to " + selectedTask.getName() +"?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // TODO edit task
            
            String newLocation = textFieldLocation.getText();
            // Edit currently selected task with new task.
            OperatorMainController.myController.editTask(selectedTask.getTaskID(), newLocation);
            
            // TODO assignTask database.
            OperatorMainController.myController.assignTask(editedTask.getTaskID(),editedTask.getUnits().toArray());
            
            
        } else {
            alert.close();
        }
    }
    /**
     * Delete task.
     * @param event 
     */
    @FXML
    private void buttonDelete(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Save changes to " + selectedTask.getName() +"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if(selectedTask.getStatus() == "Active") {
                OperatorMainController.myController.removeActiveTask(selectedTask.getTaskID());
            } else {
                OperatorMainController.myController.removeInactiveTask(selectedTask.getTaskID());
            } 
        } else {
            alert.close();
        }
    }
    /**
     * Close window
     * @param event 
     */
    @FXML
    private void buttonCancel(MouseEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
    /**
     * Fill the page with information from selected task.
     */
    private void fillPage() {
        try {
            ActiveUnits.addAll(OperatorMainController.myController.getActiveUnits());
            InactiveUnits.addAll(OperatorMainController.myController.getInactiveUnits());
            
            if(selectedTask != null) {
                textFieldName.setText(selectedTask.getName());
                textAreaDescription.setText(selectedTask.getDescription());
                textFieldID.setText(""+selectedTask.getTaskID());
                textFieldLocation.setText(selectedTask.getLocation());
                comboboxUrgency.setPromptText(selectedTask.getUrgency());
                comboboxStatus.setPromptText(selectedTask.getStatus());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(TaskInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
