/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Task;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class TasksController implements Initializable {

    @FXML
    private TableView<Task> ATaskTable;
    @FXML
    private TableView<Task> UTaskTable;
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonDelete;
    @FXML
    private AnchorPane MainField;
    @FXML
    private TableColumn<Task, Number> UTaskID;
    @FXML
    private TableColumn<Task, Number> ATaskID;
    @FXML
    private TableColumn<Task, String> UTaskName;
    @FXML
    private TableColumn<Task, String> ATaskName;
    @FXML
    private TableColumn<Task, String> ATaskStatus;
    @FXML
    private TableColumn<Task, String> UTaskUrgency;
    @FXML
    private TableColumn<Task, String> ATaskUnit;

    ObservableList<Task> ActiveTasks;
    ObservableList<Task> InactiveTasks;

    private boolean Simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Simulation = OperatorMainController.is_Simulation;
        if (!Simulation) {
            if (OperatorMainController.myController.user != null) {
                try {
                    if (OperatorMainController.myController.getInactiveTasks() != null) {
                        InactiveTasks = FXCollections.observableArrayList(OperatorMainController.myController.getInactiveTasks());
                    }
                    if (OperatorMainController.myController.getActiveTasks() != null) {
                        ActiveTasks = FXCollections.observableArrayList(OperatorMainController.myController.getActiveTasks());
                    }

                    ATaskID.setCellValueFactory(new PropertyValueFactory<>("taskID"));
                    ATaskName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    ATaskStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
                    ATaskUnit.setCellValueFactory(new PropertyValueFactory<>("units"));

                    UTaskID.setCellValueFactory(new PropertyValueFactory<>("taskID"));
                    UTaskName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    UTaskUrgency.setCellValueFactory(new PropertyValueFactory<>("urgency"));

                    ATaskTable.setRowFactory(tv -> {
                        TableRow<Task> row = new TableRow<>();
                        row.setOnMouseClicked(event -> {
                            if (event.getClickCount() == 2 && (!row.isEmpty())) {

                                Task myTask = row.getItem();
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaskInfo.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    stage.initStyle(StageStyle.DECORATED);
                                    OperatorMainController.myController.selectedTaskID = myTask.getTaskID();
                                    stage.setTitle("Task" + myTask.getTaskID());
                                    stage.setScene(new Scene(root1));
                                    stage.show();
                                } catch (Exception x) {
                                    System.out.println(x.getMessage());
                                }
                            }
                        });
                        return row;
                    });

                    UTaskTable.setRowFactory(tv -> {
                        TableRow<Task> row = new TableRow<>();
                        row.setOnMouseClicked(event -> {
                            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                                Task myTask = row.getItem();
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaskInfo.fxml"));
                                    Parent root1 = (Parent) fxmlLoader.load();
                                    Stage stage = new Stage();
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    stage.initStyle(StageStyle.DECORATED);
                                    stage.setTitle("Unit" + myTask.getTaskID());
                                    stage.setScene(new Scene(root1));
                                    stage.show();
                                } catch (Exception x) {
                                    System.out.println(x.getMessage());
                                }
                            }
                        });
                        return row;
                    });

                    UTaskTable.setItems(InactiveTasks);
                    ATaskTable.setItems(ActiveTasks);
                } catch (IOException ex) {
                    Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            InactiveTasks = FXCollections.observableArrayList(OperatorMainController.inactive_Task);

            ActiveTasks = FXCollections.observableArrayList(OperatorMainController.active_Tasks);

            ATaskID.setCellValueFactory(new PropertyValueFactory<>("taskID"));
            ATaskName.setCellValueFactory(new PropertyValueFactory<>("name"));
            ATaskStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            ATaskUnit.setCellValueFactory(new PropertyValueFactory<>("units"));

            UTaskID.setCellValueFactory(new PropertyValueFactory<>("taskID"));
            UTaskName.setCellValueFactory(new PropertyValueFactory<>("name"));
            UTaskUrgency.setCellValueFactory(new PropertyValueFactory<>("urgency"));

            ATaskTable.setRowFactory(tv -> {
                TableRow<Task> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {

                        Task myTask = row.getItem();
                        try {
                             OperatorMainController.selectedTaskID = myTask.getTaskID();
                            System.out.println("HIER NICK: " + myTask.getTaskID());
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaskInfo.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.DECORATED);
                           
                            System.out.println("HIER NICK: " + myTask.getTaskID());
                            stage.setTitle("Task" + myTask.getTaskID());
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch (Exception x) {
                            System.out.println(x.getMessage());
                        }
                    }
                });
                return row;
            });

            UTaskTable.setRowFactory(tv -> {
                TableRow<Task> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Task myTask = row.getItem();
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaskInfo.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.DECORATED);
                            OperatorMainController.selectedTaskID = myTask.getTaskID();
                            stage.setTitle("Unit" + myTask.getTaskID());
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch (Exception x) {
                            System.out.println(x.getMessage());
                        }
                    }
                });
                return row;
            });

            UTaskTable.setItems(InactiveTasks);
            ATaskTable.setItems(ActiveTasks);

        }
    }

    @FXML
    private void newClick(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateTask.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Create Task");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception x) {
            System.out.println(x.getMessage());
        }

    }

    @FXML
    private void deleteClick(MouseEvent event) {
        Task selectedTask = (Task) ATaskTable.getSelectionModel().getSelectedItem();
        if (!Simulation) {
            try {
                if (selectedTask.isAccepted()) {
                    OperatorMainController.myController.removeActiveTask(selectedTask.getTaskID());
                    ActiveTasks.remove(selectedTask);
                } else {
                    OperatorMainController.myController.removeInactiveTask(selectedTask.getTaskID());
                    InactiveTasks.remove(selectedTask);

                }
            } catch (Exception ex) {
                Logger.getLogger(InactiveUnitsController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (!selectedTask.isAccepted()) {
                
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
    }

}
