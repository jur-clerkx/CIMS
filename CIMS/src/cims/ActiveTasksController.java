/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import static cims.ConnectionController.output;
import Field_Operations.Task;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class ActiveTasksController implements Initializable {

    @FXML
    private TableView<Task> tableviewActiveTask;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonNew;
    @FXML
    private AnchorPane MainField;
    @FXML
    private TableColumn<Task, Number> tableId;
    @FXML
    private TableColumn<Task, String> tableTaskName;
    @FXML
    private TableColumn<Task, String> tableStatus;
    @FXML
    private TableColumn<Task, String> tableTaskUnit;

    private int selectedID;

    private ObservableList<Task> tasks;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Database Data:
        try {
            if (OperatorMainController.myController.user != null) {
                tasks = FXCollections.observableArrayList(OperatorMainController.myController.getActiveTasks());
            }
        } catch (IOException ex) {
            tasks = FXCollections.observableArrayList();
            Logger.getLogger(ActiveTasksController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tasks = FXCollections.observableArrayList();
        tasks.add(new Task(1, "Task 1: Dummy", "High", "Active", "Eindhoven", "Fontys"));
        tasks.add(new Task(3, "Task 3: Dummy", "Low", "Inactive", "Eindhoven", "TU"));

        tableId.setCellValueFactory(new PropertyValueFactory<>("taskID"));
        tableTaskName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        //tableTaskUnit.setCellValueFactory(new PropertyValueFactory<Task, Number>("units")); - TODO Fill units Column

        tableviewActiveTask.setItems(tasks);

        tableviewActiveTask.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    Task myTask = row.getItem();
                    try {
                        ConnectionController.selectedTaskID = myTask.getTaskID();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaskInfo.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.DECORATED);
                        stage.setTitle("Task: " + myTask.getTaskID());
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch (Exception x) {
                        System.out.println("Error: " + x.getMessage());
                    }
                }
            });
            return row;
        });
    }

    @FXML
    private void deleteButtonClick(MouseEvent event) throws IOException {
        Task task = (Task) tableviewActiveTask.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Removing Task: " + task.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            tasks.remove(task);
            task.operateStatus("Cancelled");
            OperatorMainController.myController.removeActiveTask(task.getTaskID());

        } else {
            alert.close();
        }
    }

    @FXML
    private void newButtonClick(ActionEvent event) {
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
            System.out.println("Error: " + x.getMessage());
        }
    }
}
