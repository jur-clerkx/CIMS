/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Domain.Roadmap;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class viewRoadMapController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtDescription;

    private Roadmap selectedRoadmap = null;

    private boolean Simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Simulation = OperatorMainController.is_Simulation;
        selectedRoadmap = OperatorMainController.selectedRoadmap;

        if (selectedRoadmap != null) {
            txtName.setText(selectedRoadmap.getName());
            txtDescription.setText(selectedRoadmap.getDescription());
        }
    }

}
