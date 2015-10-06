/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import cims.Field_Operations.Unit;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.ButtonGroup;

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

    private int NrOfPoliceCars;
    private int NrOfAmbulances;
    private int NrOfFireTrucks;
    private int NrOFPolicemen;
    private int NRofAmbulancePeople;
    private int NRofFireFIghters;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void buttonCreate(MouseEvent event) {
        boolean succes = false;
        int size = 0;
        if (radioButtonSmall.isSelected()) {
            size = 1;
        } else if (radioButtonMedium.isSelected()) {
            size = 2;
        } else if (radioButtonLarge.isSelected()) {
            size = 3;
        }
        convertToInt();

        succes = OperatorMainController.myController.CreateUnit(textFieldName.getText(), textFieldLocation.getText(), size, getSelectedSpecials(),NrOfPoliceCars,NrOfFireTrucks,NrOfAmbulances,NrOFPolicemen,NRofFireFIghters,NRofAmbulancePeople);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        if (succes) {
            alert.setContentText("Unit succesfully created.");
        } else {
            alert.setContentText("An error has occured");
        }

        alert.showAndWait();
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void buttonCancel(MouseEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();

        stage.close();
    }

    private String getSelectedSpecials() {
        String selected = "F";
        if (radioButtonFire.isSelected()) {
            selected += "1";
        }
        if (radioButtonFuel.isSelected()) {
            selected += "2";
        }
        if (radioButtonExplosion.isSelected()) {
            selected += "3";
        }
        if (radioButtonGas.isSelected()) {
            selected += "4";
        }
        if (radioButtonQuake.isSelected()) {
            selected += "5";
        }
        if (radioButtonTerror.isSelected()) {
            selected += "6";
        }
        return selected;
    }

    private void convertToInt() {
        if (textFieldNRPolice.getText().equals("")) {
            NrOfPoliceCars = -1;
        } else {
            NrOfPoliceCars = Integer.parseInt(textFieldNRPolice.getText());
        }
        if (textFieldNRAmbulance.getText().equals("")) {
            NrOfAmbulances = -1;
        } else {
            NrOfAmbulances = Integer.parseInt(textFieldNRAmbulance.getText());
        }
        if (textFieldNRFire.getText().equals("")) {
            NrOfFireTrucks = -1;
        } else {
            NrOfFireTrucks = Integer.parseInt(textFieldNRFire.getText());
        }
        if (textFieldPPCPolice.getText().equals("")) {
            NrOFPolicemen = -1;
        } else {
            NrOFPolicemen = Integer.parseInt(textFieldPPCPolice.getText());
        }
        if (textFieldPPCFire.getText().equals("")) {
            NRofFireFIghters = -1;
        } else {
            NRofFireFIghters = Integer.parseInt(textFieldPPCFire.getText());
        }
        if (textFieldPPCAmbu.getText().equals("")) {
            NRofAmbulancePeople = -1;
        } else {
            NRofAmbulancePeople = Integer.parseInt(textFieldPPCAmbu.getText());
        }

    }
}
