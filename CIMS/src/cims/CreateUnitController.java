/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class CreateUnitController implements Initializable {
    @FXML
    private Button buttonCreate;
    @FXML
    private Button buttonCancel;
    @FXML
    private RadioButton radioButtonSmall;
    @FXML
    private RadioButton radioButtonMedium;
    @FXML
    private RadioButton radioButtonLarge;
    @FXML
    private RadioButton radioButtonPolice;
    @FXML
    private RadioButton radioButtonFire;
    @FXML
    private RadioButton radioButtonAmbulance;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldLocation;
    @FXML
    private RadioButton radioButtonGas;
    @FXML
    private RadioButton radioButtonFuel;
    @FXML
    private RadioButton radioButtonExplosion;
    @FXML
    private RadioButton radioButtonTerror;
    @FXML
    private RadioButton radioButtonQuake;
    @FXML
    private TextField textFieldNRPolice;
    @FXML
    private TextField textFieldNRFire;
    @FXML
    private TextField textFieldNRAmbulance;
    @FXML
    private TextField textFieldPPCPolice;
    @FXML
    private TextField textFieldPPCFire;
    @FXML
    private TextField textFieldPPCAmbu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonCreate(MouseEvent event) {
    }

    @FXML
    private void buttonCancel(MouseEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();

    stage.close();
    }
    
}
