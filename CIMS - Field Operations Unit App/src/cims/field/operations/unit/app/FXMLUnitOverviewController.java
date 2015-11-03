/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import Field_Operations.Unit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jur
 */
public class FXMLUnitOverviewController implements Initializable {
    
    @FXML
    private TextField textFieldUnitID;
    @FXML
    private TextField textFieldUnitName;
    @FXML
    private TextArea textAreaUnitDescription;
    @FXML
    private TextField textFieldUnitShift;
    @FXML
    private ListView listViewUnitUsers;
    @FXML
    private ListView listViewUnitMaterials;
    @FXML
    private ListView listViewUnitVehicles;

    private Unit unitInfo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Get the unit info
        unitInfo = CIMSFieldOperationsUnitApp.con.getUnit();
        if(unitInfo == null) {
            Stage stage = (Stage)textFieldUnitID.getScene().getWindow();
            stage.close();
        } else {
            textFieldUnitID.setText("" + unitInfo.getUnitID());
            textFieldUnitName.setText(unitInfo.getName());
            textAreaUnitDescription.setText(unitInfo.getDescription());
            textFieldUnitShift.setText(unitInfo.getShift());
            listViewUnitUsers.setItems((ObservableList) unitInfo.getMembers());
            listViewUnitMaterials.setItems((ObservableList) unitInfo.getMaterials());
            listViewUnitVehicles.setItems((ObservableList) unitInfo.getVehicles());
        }
    }    
    
}
