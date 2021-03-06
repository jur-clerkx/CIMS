/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Domain.Task;
import Field_Operations.Domain.Unit;
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
    //PlaceHolder
    private boolean Simulation;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboboxUrgency.getItems().addAll("High", "Medium", "Low");
        comboboxStatus.getItems().addAll("Open", "Closed");

        Simulation = OperatorMainController.is_Simulation;
        if (!Simulation) {
            if (OperatorMainController.myController.user != null) {
                try {
                    AvailableList.addAll(OperatorMainController.myController.getInactiveUnits());
                    if (!AvailableList.isEmpty()) {
                        listviewAvailableUnits.setItems(AvailableList);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CreateTaskController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {

            AvailableList.addAll(OperatorMainController.inactive_Units);
            if (!AvailableList.isEmpty()) {
                listviewAvailableUnits.setItems(AvailableList);
            }

        }
    }

    @FXML
    private void assignButtonClick(MouseEvent event) {
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
        if (!Simulation) {
            int taskID = Integer.parseInt(textfieldTaskID.getText());
            String taskName = textfieldTaskName.getText();
            String taskLocation = textfieldTaskLocation.getText();
            int urgency = comboboxUrgency.getSelectionModel().getSelectedIndex();
            String urgencyCode = "";
            String statusCode = "";
            int status = comboboxStatus.getSelectionModel().getSelectedIndex();
            if (urgency == 1) {
                urgencyCode = "Low";
            } else if (urgency == 2) {
                urgencyCode = "Medium";
            } else if (urgency == 3) {
                urgencyCode = "High";
            }

            if (status == 1) {
                statusCode = "open";
            } else if (status == 2) {
                statusCode = "closed";
            }
            String description = textareaDescription.getText();

            if ((taskID <= 0 || taskID != (int) taskID) || taskName == null || taskLocation == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("You forgot to fill in the tasks ID, name or location.");
                alert.showAndWait();
            } else {
                task = new Task(taskName, urgencyCode, statusCode, taskLocation, description);
                OperatorMainController.myController.createTask(taskName, urgencyCode, description);
                ArrayList<Integer> assignedUnits = new ArrayList<>();
                assignedUnits.add((int)task.getId());
                for (Unit u : AssignedList) {
                    assignedUnits.add((int)u.getId());
                }

                OperatorMainController.myController.assignTask(assignedUnits.toArray());
            }
            Stage stage = (Stage)textareaDescription.getScene().getWindow();
            stage.close();
        }
        else
        {
            int taskID = Integer.parseInt(textfieldTaskID.getText());
            String taskName = textfieldTaskName.getText();
            String taskLocation = textfieldTaskLocation.getText();
            int urgency = comboboxUrgency.getSelectionModel().getSelectedIndex();
            String urgencyCode = "";
            String statusCode = "";
            int status = comboboxStatus.getSelectionModel().getSelectedIndex();
            if (urgency == 1) {
                urgencyCode = "Low";
            } else if (urgency == 2) {
                urgencyCode = "Medium";
            } else if (urgency == 3) {
                urgencyCode = "High";
            }

            if (status == 1) {
                statusCode = "open";
            } else if (status == 2) {
                statusCode = "closed";
            }
            String description = textareaDescription.getText();

            if ((taskID <= 0 || taskID != (int) taskID) || taskName == null || taskLocation == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("You forgot to fill in the tasks ID, name or location.");
                alert.showAndWait();
            } else {
                task = new Task(taskName, urgencyCode, statusCode, taskLocation, description);
                OperatorMainController.active_Tasks.add(task);
                ArrayList<Integer> assignedUnits = new ArrayList<>();
                assignedUnits.add((int)task.getId());
                for (Unit u : AssignedList) {
                    u.setTask(task);
                }

            }
        }
    }

    @FXML
    private void cancelButtonClick(MouseEvent event) {

        Stage stage = (Stage) buttonCancel.getScene().getWindow();

        stage.close();

    }

}
