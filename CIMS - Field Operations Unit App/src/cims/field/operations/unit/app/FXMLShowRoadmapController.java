/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import Field_Operations.Roadmap;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jur
 */
public class FXMLShowRoadmapController implements Initializable {

    @FXML
    private ListView listViewRoadmaps;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private Button buttonShowAllRoadmaps;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Fill the screen with current tasks roadmaps, if no task selected, fill with all roadmaps
        if (CIMSFieldOperationsUnitApp.currentTask != null) {
            ArrayList<Roadmap> roadmaps = CIMSFieldOperationsUnitApp.con.getRoadmapsByTask();
            if (roadmaps != null) {
                listViewRoadmaps.getItems().addAll(roadmaps);
            }
        } else {
            ArrayList<Roadmap> roadmaps = CIMSFieldOperationsUnitApp.con.getAllRoadmaps();
            if (roadmaps != null) {
                listViewRoadmaps.getItems().addAll(roadmaps);
            }
            buttonShowAllRoadmaps.setDisable(true);
        }

        textFieldName.setText("No roadmap selected");
        textAreaDescription.setText("No roadmap selected");
    }

    @FXML
    private void handleShowAllRoadmaps(ActionEvent event) {
        // Fill the screen with all the roadmaps of the system
        listViewRoadmaps.getItems().clear();
        listViewRoadmaps.getItems().addAll(CIMSFieldOperationsUnitApp.con.getAllRoadmaps());

        textFieldName.setText("No roadmap selected");
        textAreaDescription.setText("No roadmap selected");

        buttonShowAllRoadmaps.setDisable(true);
    }

    @FXML
    private void handleRoadmapSelectionChanged(Event event) {
        Roadmap rm = (Roadmap) listViewRoadmaps.getSelectionModel().getSelectedItem();
        if (rm != null) {
            textFieldName.setText(rm.getName());
            textAreaDescription.setText(rm.getDescription());
        } else {
            textFieldName.setText("No roadmap selected");
            textAreaDescription.setText("No roadmap selected");
        }
    }

}
