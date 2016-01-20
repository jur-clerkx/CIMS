/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Progress;
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
    private ComboBox<String> comboboxUrgency;
    @FXML
    private ComboBox<String> comboboxStatus;
    @FXML
    private TextArea textAreaDescription;

    ObservableList<Unit> ActiveUnits;
    ObservableList<Unit> InactiveUnits;
    private Task selectedTask;

    private boolean Simulation;
    @FXML
    private ListView<Unit> lvAvailable;
    @FXML
    private ListView<Unit> lvAssigned;
    @FXML
    private TextArea txtFeedback;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ActiveUnits = FXCollections.observableArrayList();

        Simulation = OperatorMainController.is_Simulation;
        if (!Simulation) {
            int ID = OperatorMainController.myController.selectedTaskID;
            selectedTask = null;
            try {
                selectedTask = OperatorMainController.myController.getTaskInfo(ID);
                ArrayList<Progress> message = OperatorMainController.myController.getProgress(ID);
                txtFeedback.clear();
                String fullMessage = "";
                for(Progress s : message)
                {
                    fullMessage = "send by :   "+s.getUser().getFirstname()+ " :       " + s.getMessage()+ "\r\n" + fullMessage;
                }
                txtFeedback.setText(fullMessage);
            } catch (IOException ex) {
                Logger.getLogger(UnitInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (selectedTask != null) {
                fillPage();
            }
        } else {
            int ID = OperatorMainController.selectedTaskID;
            selectedTask = null;
            InactiveUnits = FXCollections.observableArrayList();
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

            //listviewAUnits.setItems(FXCollections.observableArrayList(OperatorMainController.inactive_Units));
        }
    }

    /**
     * Move selected unassigned unit, available unit to the assigned units.
     *
     * @param event
     */
    @FXML
    private void buttonAdd(MouseEvent event) {
        Unit selectedUnit = (Unit) lvAvailable.getSelectionModel().getSelectedItem();
        lvAssigned.getItems().add(selectedUnit);
        lvAvailable.getItems().remove(selectedUnit);

        //selectedTask.addUnit(selectedUnit);
    }

    /**
     * Move selected assigned unit, unavailable unit to the unassigned units.
     *
     * @param event
     */
    @FXML
    private void buttonRemove(MouseEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Save changes to " + selectedTask.getName() + "?");

            if (!Simulation) {
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (selectedTask.getStatus() == "Active") {
                        OperatorMainController.myController.removeActiveTask((int) selectedTask.getTaskID());
                    } else {
                        OperatorMainController.myController.removeInactiveTask((int) selectedTask.getTaskID());
                    }
                } else {
                    alert.close();
                }
            } else {
                if (selectedTask.getStatus() == "Active") {
                    int index = -1;
                    for (Task t : OperatorMainController.active_Tasks) {
                        if (t.getTaskID() == selectedTask.getTaskID()) {
                            index = OperatorMainController.active_Tasks.indexOf(t);
                        }
                    }
                    OperatorMainController.active_Tasks.remove(index);
                } else {
                    int index = -1;
                    for (Task t : OperatorMainController.inactive_Task) {
                        if (t.getTaskID() == selectedTask.getTaskID()) {
                            index = OperatorMainController.inactive_Task.indexOf(t);
                        }
                    }
                    OperatorMainController.inactive_Task.remove(index);
                }
            }
        } catch (Exception ex) {
        }
    }

    /**
     * Save changes made to task.
     *
     * @param event
     */
    @FXML
    private void buttonOK(MouseEvent event) throws IOException {
        Task editedTask = null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Save changes to " + selectedTask.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (!OperatorMainController.is_Simulation) {
                String newLocation = textFieldLocation.getText();
                // Edit currently selected task with new task.
                if (newLocation != null) {
                    editedTask = selectedTask;
                    if (!textFieldName.getText().equals(editedTask.getName())) {
                        ArrayList<Object> myChanges = new ArrayList<>();
                        myChanges.add(editedTask.getTaskID());
                        myChanges.add("name");
                        myChanges.add(textFieldName.getText());
                        OperatorMainController.myController.editTask(myChanges.toArray());
                    }
                    if (!textAreaDescription.getText().equals(editedTask.getDescription())) {
                        ArrayList<Object> myChanges = new ArrayList<>();
                        myChanges.add(editedTask.getTaskID());
                        myChanges.add("description");
                        myChanges.add(textAreaDescription.getText());
                        OperatorMainController.myController.editTask(myChanges.toArray());
                    }
                    if (!textFieldLocation.getText().equals(editedTask.getLocation())) {
                        ArrayList<Object> myChanges = new ArrayList<>();
                        myChanges.add(editedTask.getTaskID());
                        myChanges.add("location");
                        myChanges.add(textFieldLocation.getText());
                        OperatorMainController.myController.editTask(myChanges.toArray());
                    }
                    System.out.println(lvAssigned.getItems().size());
                    if (this.lvAssigned.getItems().size() != editedTask.getUnits().size()) {
                        Object[] o = new Object[lvAssigned.getItems().size() + 1];
                        o[0] = selectedTask.getTaskID();
                        for (int i = 0; i < this.lvAssigned.getItems().size(); i++) {
                            o[1 + i] = lvAssigned.getItems().get(i).getUnitID();
                        }
                        OperatorMainController.myController.assignTask(o);
                    }
                }
            } else {
                int id = -1;
                String state = "";
                for (Task t : OperatorMainController.active_Tasks) {
                    if (t.getTaskID() == Long.parseLong(textFieldID.getText())) {
                        id = OperatorMainController.active_Tasks.indexOf(t);
                        state = "true";
                    }
                }
                for (Task t : OperatorMainController.inactive_Task) {
                    if (t.getTaskID() == Long.parseLong(textFieldID.getText())) {
                        id = OperatorMainController.inactive_Task.indexOf(t);
                        state = "false";
                    }
                }

                Task t = new Task(101, textFieldName.getText(), comboboxUrgency.getSelectionModel().getSelectedItem(), comboboxStatus.getSelectionModel().getSelectedItem(), textFieldLocation.getText(), textAreaDescription.getText());

                if (state.equals("true")) {
                    OperatorMainController.active_Tasks.remove(id);
                    OperatorMainController.active_Tasks.add(t);
                } else {
                    OperatorMainController.inactive_Task.remove(id);
                    OperatorMainController.inactive_Task.add(t);
                }
            }

        } else {
            alert.close();
        }
        Stage stage = (Stage) textAreaDescription.getScene().getWindow();
        stage.close();
    }

    /**
     * Delete task.
     *
     * @param event
     */
    private void buttonDelete(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Save changes to " + selectedTask.getName() + "?");

        if (!Simulation) {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (selectedTask.getStatus() == "Active") {
                    OperatorMainController.myController.removeActiveTask((int) selectedTask.getTaskID());
                } else {
                    OperatorMainController.myController.removeInactiveTask((int) selectedTask.getTaskID());
                }
            } else {
                alert.close();
            }
        } else {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String state = "";
                int index = -1;
                for (Task t : OperatorMainController.active_Tasks) {
                    if (t.getTaskID() == selectedTask.getTaskID()) {
                        index = OperatorMainController.active_Tasks.indexOf(t);
                        state = "active";
                    }
                }
                OperatorMainController.active_Tasks.remove(index);

                for (Task t : OperatorMainController.inactive_Task) {
                    if (t.getTaskID() == selectedTask.getTaskID()) {
                        index = OperatorMainController.inactive_Task.indexOf(t);
                        state = "inactive";
                    }
                }

                if (state.equals("active")) {
                    OperatorMainController.active_Tasks.remove(index);
                } else {
                    OperatorMainController.inactive_Task.remove(index);
                }
                Stage stage = (Stage) textAreaDescription.getScene().getWindow();
                stage.close();

            }
        }
    }

    /**
     * Close window
     *
     * @param event
     */
    @FXML
    private void buttonCancel(MouseEvent event
    ) {
        try {
            OperatorMainController.myController.cancelTask(selectedTask.getTaskID());
        } catch (Exception ex) {
            System.out.println("Cancel Error");
        }
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Fill the page with information from selected task.
     */
    private void fillPage() {

        try {
            if (!Simulation) {
                lvAssigned.getItems().addAll(FXCollections.observableArrayList(selectedTask.getUnits()));
                lvAvailable.getItems().addAll(OperatorMainController.myController.getInactiveUnits());
            } else {
                ActiveUnits.addAll(OperatorMainController.active_Units);
                InactiveUnits.addAll(OperatorMainController.inactive_Units);
            }
            if (selectedTask != null) {
                textFieldName.setText(selectedTask.getName());
                textAreaDescription.setText(selectedTask.getDescription());
                textFieldID.setText("" + selectedTask.getTaskID());
                textFieldLocation.setText(selectedTask.getLocation());
                comboboxUrgency.setPromptText(selectedTask.getUrgency());
                comboboxStatus.setPromptText(selectedTask.getStatus());
            }
            ArrayList<String> choices = new ArrayList();
            ArrayList<String> status = new ArrayList();
            choices.add("Low");
            choices.add("High");
            choices.add("Medium");
            status.add("ongoing");
            status.add("open");
            comboboxStatus.setItems(FXCollections.observableArrayList());
            comboboxUrgency.setItems(FXCollections.observableArrayList(choices));
        } catch (IOException ex) {
            Logger.getLogger(TaskInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
