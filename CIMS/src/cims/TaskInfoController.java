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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
    private ComboBox<String> comboboxUrgency;
    @FXML
    private ComboBox<String> comboboxStatus;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private ListView<Unit> listviewUUnits;
    @FXML
    private ListView<Unit> listviewAUnits;

    ObservableList<Unit> ActiveUnits;
    ObservableList<Unit> InactiveUnits;
    private Task selectedTask;

    private boolean Simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ArrayList<String> urgency = new ArrayList();
        ArrayList<String> status = new ArrayList();
        urgency.add("Low");
        urgency.add("Medium");
        urgency.add("High");
        status.add("Ongoing");
        status.add("Finished");
        ObservableList<String> observerUrgency = FXCollections.observableArrayList(urgency);
        ObservableList<String> observerStatus = FXCollections.observableArrayList(status);
        comboboxStatus.getItems().addAll(observerUrgency);
        comboboxUrgency.getItems().addAll(observerStatus);
        
        Simulation = OperatorMainController.is_Simulation;
        if (!Simulation) {
            int ID = OperatorMainController.myController.selectedTaskID;
            selectedTask = null;
            try {
                selectedTask = OperatorMainController.myController.getTaskInfo(ID);
            } catch (IOException ex) {
                Logger.getLogger(UnitInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (selectedTask != null) {
                fillPage();
            }
        } else {
            int ID = OperatorMainController.selectedTaskID;
            selectedTask = null;

            for (Task t : OperatorMainController.active_Tasks) {
                if (t.getTaskID() == ID) {
                    selectedTask = t;
                    fillPage();
                }
            }
            for (Task t : OperatorMainController.inactive_Task) {
                if (t.getTaskID() == ID) {
                    selectedTask = t;
                    fillPage();
                }
            }
        }
    }

    /**
     * Move selected unassigned unit, available unit to the assigned units.
     *
     * @param event
     */
    @FXML
    private void buttonAdd(MouseEvent event) {
        Unit selectedUnit = (Unit) listviewUUnits.getSelectionModel().getSelectedItem();
        if (selectedUnit != null) {
            ActiveUnits.add(selectedUnit);
            InactiveUnits.remove(selectedUnit);
            listviewAUnits.getItems().add(selectedUnit);
            selectedTask.addUnit(selectedUnit);
        }
    }

    /**
     * Move selected assigned unit, unavailable unit to the unassigned units.
     *
     * @param event
     */
    @FXML
    private void buttonRemove(MouseEvent event) {
        Unit selectedUnit = (Unit) listviewAUnits.getSelectionModel().getSelectedItem();
        InactiveUnits.add(selectedUnit);
        ActiveUnits.remove(selectedUnit);

        listviewUUnits.setItems(InactiveUnits);
        listviewAUnits.setItems(ActiveUnits);

        selectedTask.delUnit(selectedUnit);
    }

    /**
     * Save changes made to task.
     *
     * @param event
     */
    @FXML
    private void buttonOK(MouseEvent event) throws IOException {
        Task editedTask = null;
        if (!Simulation) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Save changes to " + selectedTask.getName() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String newLocation = textFieldLocation.getText();
                // Edit currently selected task with new task.
                if (newLocation != null) {
                    OperatorMainController.myController.editTask(selectedTask.getTaskID(), newLocation);
                    editedTask = selectedTask;
                    editedTask.setLocation(newLocation);
                }
                // TODO assignTask database.
                if (editedTask != null) {
                    for (Unit u : ActiveUnits) {
                        editedTask.addUnit(u);
                    }
                }

            } else {
                alert.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Save changes to " + selectedTask.getName() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (this.listviewAUnits.getItems().size() != 0) {
                    int i = -1;
                    for (Unit u : this.listviewAUnits.getItems()) {
                        if (OperatorMainController.inactive_Units.contains(u)) {
                            OperatorMainController.inactive_Units.remove(u);
                            OperatorMainController.active_Units.add(u);
                        }
                    }
                }
                String type = "inactive";
                int index = -1;
                Task edit = null;
                for ( Task t : OperatorMainController.active_Tasks)
                {
                    if(t.getTaskID() == Integer.parseInt(textFieldID.getText()))
                    {
                        index = OperatorMainController.active_Tasks.indexOf(t);
                        type = "active";
                        edit = t;
                    }
                }
                
                for ( Task t : OperatorMainController.inactive_Task)
                {
                    if(t.getTaskID() == Integer.parseInt(textFieldID.getText()))
                    {
                        index = OperatorMainController.inactive_Task.indexOf(t);
                        type = "inactive";
                        edit = t;
                    }
                }
                String status = (String)comboboxStatus.getSelectionModel().getSelectedItem();
                String urgency = (String)comboboxUrgency.getSelectionModel().getSelectedItem();
                Task newTask = new Task(Integer.parseInt(textFieldID.getText()),textFieldName.getText(),urgency,status,textFieldLocation.getText(),textAreaDescription.getText());
                if(type.equals("active"))
                {
                    if(edit != null)
                    {
                    OperatorMainController.active_Tasks.remove(edit);
                    OperatorMainController.active_Tasks.add(newTask);
                    }
                }
                else
                {
                    if(edit != null)
                    {
                    OperatorMainController.inactive_Task.remove(edit);
                    OperatorMainController.inactive_Task.add(newTask);
                    }
                }
            } else {
                alert.close();
            }
        }

    }

    /**
     * Delete task.
     *
     * @param event
     */
    @FXML
    private void buttonDelete(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Save changes to " + selectedTask.getName() + "?");

        if (!Simulation) {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (selectedTask.getStatus().contains("Active")) {
                    OperatorMainController.myController.removeActiveTask(selectedTask.getTaskID());
                } else {
                    OperatorMainController.myController.removeInactiveTask(selectedTask.getTaskID());
                }
            } else {
                alert.close();
            }
        } else {
            if (selectedTask.getStatus().contains("Active")) {
                OperatorMainController.active_Tasks.remove(selectedTask);
            } else {
                OperatorMainController.inactive_Task.remove(selectedTask);
            }
        }
    }

    /**
     * Close window
     *
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
            if (!Simulation) {
                ActiveUnits = FXCollections.observableArrayList(OperatorMainController.myController.getActiveUnits());
                InactiveUnits = FXCollections.observableArrayList(OperatorMainController.myController.getInactiveUnits());
            } else {
                ActiveUnits = FXCollections.observableArrayList(OperatorMainController.active_Units);
                InactiveUnits = FXCollections.observableArrayList(OperatorMainController.inactive_Units);
            }
            if (selectedTask != null) {

                listviewAUnits.setItems(FXCollections.observableArrayList(selectedTask.getUnits()));
                listviewUUnits.setItems(InactiveUnits);
                textFieldName.setText(selectedTask.getName());
                textAreaDescription.setText(selectedTask.getDescription());
                textFieldID.setText("" + selectedTask.getTaskID());
                textFieldLocation.setText(selectedTask.getLocation());
                comboboxUrgency.setPromptText(selectedTask.getUrgency());
                comboboxStatus.setPromptText(selectedTask.getStatus());
            }

        } catch (IOException ex) {
            Logger.getLogger(TaskInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
