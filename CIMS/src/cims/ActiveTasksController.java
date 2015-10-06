/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import cims.Field_Operations.Task;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
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
    
    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tasks.add(new Task(1, "Task 1", "High", "Active", "Eindhoven", "Fontys"));
        tasks.add(new Task(3, "Task 3", "Low", "Inactive", "Eindhoven", "TU"));
        
        tableId.setCellValueFactory(new PropertyValueFactory<Task, Number>("taskId"));
        tableTaskName.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<Task, String>("status"));
        //tableTaskUnit.setCellValueFactory(new PropertyValueFactory<Task, String>("unit"));
        
        tableviewActiveTask.setItems(tasks);
        //tableviewActiveTask.getColumns().addAll(tableId, tableTaskName, tableStatus, tableTaskUnit);
    }

    @FXML
    private void deleteButtonClick(MouseEvent event) {
        // TODO: No confirmation action yet
        int ix = tableviewActiveTask.getSelectionModel().getSelectedIndex();
        Task task = (Task)tableviewActiveTask.getSelectionModel().getSelectedItem();
        tasks.remove(task);
    }

    @FXML
    private void newButtonClick(MouseEvent event) {
    Node node = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateTask.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Create Task");
            stage.setScene(new Scene(root1));  
            stage.show();
          }
          catch(Exception x) {
              System.out.println(x.getMessage());
          }
    }
}
