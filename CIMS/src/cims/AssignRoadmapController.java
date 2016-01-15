/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;


import Field_Operations.Domain.Roadmap;
import Field_Operations.Domain.Task;
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
import javafx.scene.input.MouseEvent;
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
    @FXML
    private Button btnCancel;

    private ObservableList unitList;
    private ObservableList roadmapList;
    private boolean Simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Simulation = OperatorMainController.is_Simulation;
        if (!Simulation) {
            unitList = FXCollections.observableArrayList();
            roadmapList = FXCollections.observableArrayList();
            try {
                if (OperatorMainController.myController.user != null) {
                    ArrayList<Task> myTasks = OperatorMainController.myController.getActiveTasks();
                    myTasks.addAll(OperatorMainController.myController.getInactiveTasks());
                    ArrayList<Roadmap> myroadmaps = OperatorMainController.myController.getRoadmaps();
                    if (myTasks != null) {
                        unitList.addAll(myTasks);
                    }
                    if (myroadmaps != null) {
                        roadmapList.addAll(myroadmaps);
                    }
                    cboxUnits.getItems().addAll(unitList);
                    cboxRoadmaps.getItems().addAll(roadmapList);
                }
            } catch (IOException ex) {
                Logger.getLogger(AssignRoadmapController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            unitList = FXCollections.observableArrayList();
            roadmapList = FXCollections.observableArrayList();
            ArrayList<Task> myTasks = OperatorMainController.active_Tasks;
            myTasks.addAll(OperatorMainController.inactive_Task);
            ArrayList<Roadmap> myroadmaps = OperatorMainController.roadmaps;
            if (myTasks != null) {
                unitList.addAll(myTasks);
            }
            if (myroadmaps != null) {
                roadmapList.addAll(myroadmaps);
            }
            cboxUnits.getItems().addAll(unitList);
            cboxRoadmaps.getItems().addAll(roadmapList);

        }
    }

    @FXML
    private void btnAssign(MouseEvent event) {
        if (!Simulation) {
            try {
                if (OperatorMainController.myController.assignRoadmaps((int)cboxUnits.getSelectionModel().getSelectedItem().getId(),(int) cboxRoadmaps.getSelectionModel().getSelectedItem().getId())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Successfull");
                    alert.setContentText("Roadmap succesfully assigned");
                    alert.showAndWait();
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

            OperatorMainController.roadmaps.add(new Roadmap( "UserCreated", "UserCreated"));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Successfull");
            alert.setContentText("Roadmap succesfully assigned");
            alert.showAndWait();

        }
    }

}
