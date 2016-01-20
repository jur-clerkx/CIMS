/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Roadmap;
import Field_Operations.Task;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class AssignRoadmapController implements Initializable {

    @FXML
    private ComboBox<Task> cboxUnits;
    @FXML
    private ComboBox<Roadmap> cboxRoadmaps;
    @FXML
    private Button btnAssign;

    private ObservableList taskList;
    private ObservableList roadmapList;
    private boolean Simulation;
    @FXML
    private AnchorPane Mainfield;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Simulation = OperatorMainController.is_Simulation;
        if (!Simulation) {
            taskList = FXCollections.observableArrayList();
            roadmapList = FXCollections.observableArrayList();
            try {
                if (OperatorMainController.myController.user != null) {
                    ArrayList<Task> myTasks = OperatorMainController.myController.getActiveTasks();
                    myTasks.addAll(OperatorMainController.myController.getInactiveTasks());
                    ArrayList<Roadmap> myroadmaps = OperatorMainController.myController.getRoadmaps();
                    if (myTasks != null) {
                        taskList.addAll(myTasks);
                    }
                    if (myroadmaps != null) {
                        roadmapList.addAll(myroadmaps);
                    }
                    cboxUnits.getItems().addAll(taskList);
                    for (Task t : myTasks) {
                        System.out.println(t.toString() + " is " + t.getTaskID());
                    }
                    cboxRoadmaps.getItems().addAll(roadmapList);
                }
            } catch (IOException ex) {
                Logger.getLogger(AssignRoadmapController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            taskList = FXCollections.observableArrayList();
            roadmapList = FXCollections.observableArrayList();
            ArrayList<Task> myTasks = OperatorMainController.active_Tasks;
            myTasks.addAll(OperatorMainController.inactive_Task);
            ArrayList<Roadmap> myroadmaps = OperatorMainController.roadmaps;
            if (myTasks != null) {
                taskList.addAll(myTasks);
            }
            if (myroadmaps != null) {
                roadmapList.addAll(myroadmaps);
            }
            cboxUnits.getItems().addAll(taskList);
            for (Task t : myTasks) {
                System.out.println(t.toString() + " is " + t.getTaskID());
            }
            cboxRoadmaps.getItems().addAll(roadmapList);

        }
    }

    @FXML
    private void btnAssign(MouseEvent event) {
        if (!Simulation) {
            try {
                Task t = cboxUnits.getSelectionModel().getSelectedItem();
                Roadmap r = cboxRoadmaps.getSelectionModel().getSelectedItem();
                if (OperatorMainController.myController.assignRoadmaps((int) cboxUnits.getSelectionModel().getSelectedItem().getTaskID(), (int) cboxRoadmaps.getSelectionModel().getSelectedItem().getRoadmapId())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Successfull");
                    alert.setContentText("Roadmap succesfully assigned");
                    alert.showAndWait();
                    try {
                        Node node;
                        node = (Node) FXMLLoader.load(getClass().getResource("AllRoadmaps.fxml"));
                        this.Mainfield.getChildren().setAll(node);
                    } catch (Exception ex) {
                        System.out.println("error change");
                    }
                }
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed");
                alert.setContentText("Roadmap failed to be assigned");
                alert.showAndWait();
                Logger
                        .getLogger(AssignRoadmapController.class
                                .getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            OperatorMainController.roadmaps.add(new Roadmap(12, "UserCreated", "UserCreated"));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successfull");
            alert.setContentText("Roadmap succesfully assigned");
            alert.showAndWait();
            try {
                Node node;
                node = (Node) FXMLLoader.load(getClass().getResource("AllRoadmaps.fxml"));
                this.Mainfield.getChildren().setAll(node);
            } catch (Exception ex) {
                System.out.println("error change");
            }

        }
    }

}
