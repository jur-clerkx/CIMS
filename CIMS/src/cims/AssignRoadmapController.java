/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Roadmap;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class AssignRoadmapController implements Initializable {

    @FXML
    private ComboBox<Unit> cboxUnits;
    @FXML
    private ComboBox<Roadmap> cboxRoadmaps;
    @FXML
    private Button btnAssign;
    @FXML
    private Button btnCancel;

    private ObservableList unitList;
    private ObservableList roadmapList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        unitList = FXCollections.observableArrayList();
        roadmapList = FXCollections.observableArrayList();
        try {
            if (OperatorMainController.myController.user != null) {
                ArrayList<Unit> myUnits = OperatorMainController.myController.getActiveUnits();
                ArrayList<Roadmap> myroadmaps = ConnectionController.getRoadmaps();
                if (myUnits != null && myroadmaps != null) {
                    unitList.addAll(myUnits);
                roadmapList.addAll(myroadmaps);
                }
                cboxUnits.getItems().addAll(unitList);
                cboxRoadmaps.getItems().addAll(roadmapList);
            }
        } catch (IOException ex) {
            Logger.getLogger(AssignRoadmapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnAssign(MouseEvent event) {
        try {
            if (ConnectionController.assignRoadmaps(cboxUnits.getSelectionModel().getSelectedItem().getUnitID(), cboxRoadmaps.getSelectionModel().getSelectedItem().getRoadmapId())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Successfull");
                alert.setContentText("Roadmap succesfully assigned");
                alert.showAndWait();
            }
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setContentText("Roadmap failed to be assigned");
            alert.showAndWait();
            Logger.getLogger(AssignRoadmapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
