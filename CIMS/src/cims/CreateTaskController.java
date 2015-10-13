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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class CreateTaskController implements Initializable {

    @FXML
    private GridPane TaskInfo;
    @FXML
    private TextField textfieldTaskID;
    @FXML
    private TextField textfieldTaskName;
    @FXML
    private TextField textfieldTaskLocation;
    @FXML
    private ListView listviewAvailableUnits;
    @FXML
    private ListView listviewAssignedUnits;
    @FXML
    private Button buttonAssignUnit;
    @FXML
    private Button buttonRevokeUnit;
    @FXML
    private ComboBox comboboxUrgency;
    @FXML
    private ComboBox comboboxStatus;
    @FXML
    private TextArea textareaDescription;
    @FXML
    private Button buttonCreateUnit;
    @FXML
    private Button buttonCancel;

    private Task task;

    ObservableList<Unit> AvailableList = FXCollections.observableArrayList();
    ObservableList<Unit> AssignedList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboboxUrgency.getItems().addAll("High", "Medium", "Low");
        comboboxStatus.getItems().addAll("Open", "Closed");
        try {
            //TODO: populate available units from connection to observablelist
            AvailableList.addAll(OperatorMainController.myController.getInactiveUnits());
        } catch (IOException ex) {
            Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listviewAvailableUnits.setItems(AvailableList);
        task = null;
    }

    @FXML
    private void assignButtonClick(MouseEvent event) {
        //int selectedItem = listviewAvailableUnits.getSelectionModel().getSelectedIndex();
        //String Content = AvailableList.get(selectedItem);
        Unit selectedUnit = (Unit) listviewAvailableUnits.getSelectionModel().getSelectedItem();
        if (selectedUnit != null) {
            AvailableList.remove(selectedUnit);
            AssignedList.add(selectedUnit);
            listviewAvailableUnits.setItems(AvailableList);
            listviewAssignedUnits.setItems(AssignedList);

        }

    }

    @FXML
    private void revokeButtonClick(MouseEvent event) {
        //int selectedItem = listviewAssignedUnits.getSelectionModel().getSelectedIndex();
        //String Content = AssignedList.get(selectedItem);
        Unit selectedUnit = (Unit) listviewAssignedUnits.getSelectionModel().getSelectedItem();
        if (selectedUnit != null) {

            AssignedList.remove(selectedUnit);
            AvailableList.add(selectedUnit);
            listviewAssignedUnits.setItems(AssignedList);
            listviewAvailableUnits.setItems(AvailableList);
        }
    }

    @FXML
    private void createButtonClick(MouseEvent event) throws IOException {
        int taskID = Integer.parseInt(textfieldTaskID.getText());
        String taskName = textfieldTaskName.getText();
        String taskLocation = textfieldTaskLocation.getText();
        String urgency = comboboxUrgency.getSelectionModel().getSelectedItem().toString();
        String status = comboboxStatus.getSelectionModel().getSelectedItem().toString();
        String description = textareaDescription.getText();

        if ((taskID <= 0 || taskID != (int)taskID) || taskName == null || taskLocation == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You forgot to fill in the tasks ID, name or location.");
            alert.showAndWait();
        } else {
            task = new Task(taskID, taskName, urgency, status, taskLocation, description);
            OperatorMainController.myController.createTask(taskID, taskName, urgency, status, taskLocation, description);
            ArrayList<Unit> assignedUnits = (ArrayList<Unit>) AssignedList;
            OperatorMainController.myController.assignTask(task.getTaskID(), assignedUnits.toArray());
        }
    }

    @FXML
    private void cancelButtonClick(MouseEvent event) {

        Stage stage = (Stage) buttonCancel.getScene().getWindow();

        stage.close();

    }

}
