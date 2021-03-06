/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Domain.Roadmap;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class RoadmapCreateController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtDescription;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCreate;

    private boolean Simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Simulation = OperatorMainController.is_Simulation;
    }

    @FXML
    private void create(ActionEvent event) {
        if (!Simulation) {
            try {
                if (OperatorMainController.myController.createRoadmap(txtName.getText(), txtDescription.getText())) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Roadmap succesfully created.");
                    alert.showAndWait();
                }

            } catch (IOException ex) {
                Logger.getLogger(RoadmapCreateController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RoadmapCreateController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            OperatorMainController.roadmaps.add(new Roadmap( txtName.getText(), txtDescription.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Roadmap succesfully created.");
            alert.showAndWait();
        }
    }

}
