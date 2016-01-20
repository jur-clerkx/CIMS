/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import Field_Operations.Task;
import Network.User;
import domain.ConnectionRunnable;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jur
 */
public class FXMLMainController implements Initializable, Observer {

    //Active task FXML
    @FXML
    private TextField textFieldTaskID;
    @FXML
    private TextField textFieldTaskName;
    @FXML
    private TextField textFieldTaskUrgency;
    @FXML
    private TextField textFieldTaskStatus;
    @FXML
    private TextField textFieldTaskLocation;
    @FXML
    private TextArea textAreaTaskDescription;
    @FXML
    private ListView listViewTaskUnits;
    //Personal info FXML
    @FXML
    private Tab tabQueue;
    @FXML
    private Tab tabActive;
    @FXML
    private TabPane tabPaneMain;
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldDate;
    @FXML
    private TextField textFieldGender;
    @FXML
    private TextField textFieldRank;
    @FXML
    private TextField textFieldSector;
    //Task list    
    @FXML
    private TableView tableViewTasks;
    @FXML
    private TableColumn<Task, Integer> tableColumnTaskID;
    @FXML
    private TableColumn<Task, String> tableColumnTaskName;
    @FXML
    private TableColumn<Task, String> tableColumnUrgency;
    @FXML
    private Button buttonAcceptTask;
    @FXML
    private TextArea textAreaReasonDeny;
    //Status update time
    @FXML
    private Label labelLastUpdate;

    @FXML
    private Button buttonSendFeedback;
    @FXML
    private Button buttonCancelled;
    @FXML
    private Button buttonInProgress;
    @FXML
    private Button buttonCompleted;
    @FXML
    private Button buttonShowInformation;

    int counter = 0;
    ArrayList<Task> tasks;
    Task currentTask;
    User user;
    LocalTime lastUpdateTime;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add controller as listener to connection
        CIMSFieldOperationsUnitApp.con.addObserver(this);
        updatePanes(null);

        currentTask = null;
    }

    @Override
    public void update(Observable o, Object o1) {
        ConnectionRunnable con = (ConnectionRunnable) o;
        //Check if connection is lost
        if (con.getStatus() == 2) {

        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    closeApp();
                }

            });
        }
    }

    /**
     * Closes the application
     */
    private void closeApp() {
        //Close current window
        Stage currentstage = (Stage) tabQueue.getContent().getScene().getWindow();
        currentstage.close();
    }

    /**
     * Updates all the panes
     */
    public void updatePanes(Event ae) {
        //Update tasks
        tasks = CIMSFieldOperationsUnitApp.con.getTaskList();
        user = CIMSFieldOperationsUnitApp.con.getUser();
        //Update current task
        if (currentTask != null) {
            if (currentTask.getStatus() == "Completed" || currentTask.getStatus() == "Cancelled") {
                currentTask = null;
                buttonSendFeedback.setDisable(true);
                buttonCancelled.setDisable(true);
                buttonInProgress.setDisable(true);
                buttonCompleted.setDisable(true);
                buttonShowInformation.setDisable(true);
            }

            buttonSendFeedback.setDisable(false);
            buttonCancelled.setDisable(false);
            buttonInProgress.setDisable(false);
            buttonCompleted.setDisable(false);
            buttonShowInformation.setDisable(false);
        } else {
            buttonSendFeedback.setDisable(true);
            buttonCancelled.setDisable(true);
            buttonInProgress.setDisable(true);
            buttonCompleted.setDisable(true);
            buttonShowInformation.setDisable(true);
        }

        //Fill current task
        if (currentTask != null) {
            textFieldTaskID.setText(currentTask.getTaskID() + "");
            textFieldTaskName.setText(currentTask.getName());
            textFieldTaskStatus.setText(currentTask.getStatus());
            textFieldTaskLocation.setText(currentTask.getLocation());
            textFieldTaskUrgency.setText(currentTask.getUrgency());
            textAreaTaskDescription.setText(currentTask.getDescription());
            listViewTaskUnits.getItems().clear();
            listViewTaskUnits.getItems().add(currentTask.getUnits());
        } else {
            textFieldTaskID.setText("No Task!");
            textFieldTaskName.setText("No Task!");
            textFieldTaskStatus.setText("No Task!");
            textFieldTaskLocation.setText("No Task!");
            textFieldTaskUrgency.setText("No Task!");
            textAreaTaskDescription.setText("No Task!");
            listViewTaskUnits.getItems().clear();
        }

        //Fill current user
        textFieldID.setText("" + user.getUser_ID());
        textFieldFirstName.setText(user.getFirstname());
        textFieldLastName.setText(user.getLastname());
        textFieldDate.setText("none");
        textFieldGender.setText(user.getGender());
        textFieldRank.setText(user.getRank());
        textFieldSector.setText(user.getSector());

        //Fill tableview
        if (tasks != null) {
            tableColumnTaskID.setCellValueFactory(new PropertyValueFactory<Task, Integer>("taskID"));
            tableColumnTaskName.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
            tableColumnUrgency.setCellValueFactory(new PropertyValueFactory<Task, String>("urgency"));
            tableViewTasks.getItems().setAll(tasks);
            tabQueue.setText("Task Queue (" + tasks.size() + ")");
            System.out.println("Update");
        } else if (CIMSFieldOperationsUnitApp.con.getStatus() == 2) {
            CIMSFieldOperationsUnitApp.con.quitConnection();
            CIMSFieldOperationsUnitApp.con = null;
            //Go back to login window
            try {
                //Open new window
                Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

                //Close current window
                Stage currentstage = (Stage) listViewTaskUnits.getScene().getWindow();
                currentstage.close();
            } catch (IOException ex) {
                System.out.println("Switchin windows failed!");
            }
        }
        //Enable/disable button to accept tasks
        if (currentTask == null) {
            buttonAcceptTask.setDisable(false);
        } else {
            buttonAcceptTask.setDisable(true);
        }

        //Update last update time
        lastUpdateTime = LocalTime.now();
        labelLastUpdate.setText(String.format("%02d", lastUpdateTime.getHour()) + ":" + String.format("%02d", lastUpdateTime.getMinute()) + ":" + String.format("%02d", lastUpdateTime.getSecond()));
    }

    /**
     * Checks what the new status is and sends it to the server
     *
     * @param ae
     */
    public void handleUpdateStatus(ActionEvent ae) {
        if (currentTask != null) {
            CIMSFieldOperationsUnitApp.con.updateTaskStatus((int) currentTask.getTaskID(), ((Button) ae.getSource()).getText());
            currentTask.setStatus(((Button) ae.getSource()).getText());
            if (currentTask.getStatus().equals("Cancelled") || currentTask.getStatus().equals("Completed")) {
                currentTask = null;
            }
        }
        updatePanes(null);
    }

    /**
     * Sets the selected task as active one
     *
     * @param ae
     */
    public void handleSetActive(ActionEvent ae) {
        currentTask = (Task) tableViewTasks.getSelectionModel().getSelectedItem();
        tabPaneMain.getSelectionModel().select(tabActive);
    }

    /**
     * Accepts the selected task and sets it as the active one Only works when
     * no other task is currently active
     *
     * @param ae
     */
    public void handleAcceptTask(ActionEvent ae) {
        Task selectedTask = (Task) tableViewTasks.getSelectionModel().getSelectedItem();
        if (selectedTask != null && currentTask == null) {
            CIMSFieldOperationsUnitApp.con.acceptDenyTask((int) selectedTask.getTaskID(), true, "Accepted");
            currentTask = selectedTask;
            tabPaneMain.getSelectionModel().select(tabActive);
        }
    }

    /**
     * Denies te selected task
     *
     * @param ae
     */
    public void handleDenyTask(ActionEvent ae) {
        Task selectedTask = (Task) tableViewTasks.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            CIMSFieldOperationsUnitApp.con.acceptDenyTask((int) selectedTask.getTaskID(), false, textAreaReasonDeny.getText());
            updatePanes(null);
        }
    }

    /**
     * Opens a new window that shows the unit info of the current user
     *
     * @param ae
     */
    public void handleShowUnitInfo(ActionEvent ae) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLUnitOverview.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Opens a new window to show the selected task info
     *
     * @param ae
     */
    public void handleShowTaskInfo(ActionEvent ae) {
        //Get the selected task
        CIMSFieldOperationsUnitApp.taskInfo = (Task) tableViewTasks.getSelectionModel().getSelectedItem();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLShowTaskInfo.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Opens a new window to give the user the ability to send feedback to the
     * operator
     *
     * @param ae
     */
    public void handleSendFeedback(ActionEvent ae) {
        CIMSFieldOperationsUnitApp.currentTask = currentTask;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLSendFeedback.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            //Hide current window
            Stage currentstage = (Stage) textAreaReasonDeny.getScene().getWindow();
            currentstage.hide();
            //Wait till popup window 
            stage.showAndWait();
            currentstage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Opens a new window that shows the roadmap to the user
     *
     * @param ae
     */
    public void handleShowRoadmap(ActionEvent ae) {
        CIMSFieldOperationsUnitApp.currentTask = currentTask;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLShowRoadmap.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            //Hide current window
            Stage currentstage = (Stage) textAreaReasonDeny.getScene().getWindow();
            currentstage.hide();
            //Wait till popup window 
            stage.showAndWait();
            updatePanes(null);
            currentstage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Opens a new window that shows the information to the user
     *
     * @param ae
     */
    public void handleShowInformation(ActionEvent ae) {
        CIMSFieldOperationsUnitApp.currentTask = currentTask;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLShowInfo.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            //Hide current window
            Stage currentstage = (Stage) textAreaReasonDeny.getScene().getWindow();
            currentstage.hide();
            //Wait till popup window 
            stage.showAndWait();
            updatePanes(null);
            currentstage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FXMLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
