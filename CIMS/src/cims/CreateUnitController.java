/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Unit;
import Field_Operations.Vehicle;
import Network.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
    private boolean Simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Simulation = OperatorMainController.is_Simulation;
    }

    @FXML
    private void buttonCreate(MouseEvent event) {
        if (!Simulation) {
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

            try {
                succes = OperatorMainController.myController.CreateUnit(textFieldName.getText(), "", size, getSelectedSpecials(), NrOfPoliceCars, NrOfFireTrucks, NrOfAmbulances, NrOFPolicemen, NRofFireFIghters, NRofAmbulancePeople);
            } catch (Exception ex) {

            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (succes) {
                alert.setContentText("Unit succesfully created.");
                alert.showAndWait();
                Stage stage = (Stage) buttonCancel.getScene().getWindow();
                stage.close();
            } else {
                alert.setContentText("An error has occured");
                alert.showAndWait();
            }
        } else {
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

            try {
                int i = 0;
                Unit u = new Unit(1,textFieldName.getText(), "","");
                while (this.NRofAmbulancePeople != i) {
                    u.addUser(new User(101,"dave", "test", "male", "Boss", "Medical", "1-1-2001", 1));
                    i++;
                }
                i = 0;
                while (this.NRofFireFIghters != i) {
                    u.addUser(new User(102,"dave", "test", "male", "Boss", "Fire", "1-1-2001", 1));
                    i++;
                }
                i = 0;
                while (this.NrOfPoliceCars != i) {
                    User t = new User(102,"dave", "test", "male", "Boss", "Fire", "1-1-2001", 1);
                    u.addVehicle(new Vehicle(102,"test","test","test",t,1) );
                    i++;
                }
                i = 0;
                
                while (this.NrOfFireTrucks != i) {
                    User t = new User(102,"dave", "test", "male", "Boss", "Fire", "1-1-2001", 1);
                    u.addVehicle(new Vehicle(102,"test","test","test",t,1) );
                    i++;
                }
                i = 0;
                while (this.NrOfAmbulances != i) {
                    User t = new User(102,"dave", "test", "male", "Boss", "Fire", "1-1-2001", 1);
                     u.addVehicle(new Vehicle(102,"test","test","test",t,1) );
                    i++;
                }
                i = 0;
                while (this.NrOFPolicemen != i) {
                    u.addUser(new User(102,"dave", "test", "male", "Boss", "Fire", "1-1-2001", 1));
                    i++;
                }
                i = 0;
                succes = OperatorMainController.inactive_Units.add(u);
            } catch (Exception ex) {

            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (succes) {
                alert.setContentText("Unit succesfully created.");
                alert.showAndWait();
                Stage stage = (Stage) buttonCancel.getScene().getWindow();
                stage.close();
            } else {
                alert.setContentText("An error has occured");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void buttonCancel(MouseEvent event
    ) {
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
        if (radioButtonAmbulance.isSelected()) {
            selected += "7";
        }
        if (radioButtonPolice.isSelected()) {
            selected += "8";
        }
        return selected;
    }

    private void convertToInt() {
        if (textFieldNRPolice.getText().equals("")) {
            NrOfPoliceCars = 0;
        } else {
            NrOfPoliceCars = Integer.parseInt(textFieldNRPolice.getText());
        }
        if (textFieldNRAmbulance.getText().equals("")) {
            NrOfAmbulances = 0;
        } else {
            NrOfAmbulances = Integer.parseInt(textFieldNRAmbulance.getText());
        }
        if (textFieldNRFire.getText().equals("")) {
            NrOfFireTrucks = 0;
        } else {
            NrOfFireTrucks = Integer.parseInt(textFieldNRFire.getText());
        }
        if (textFieldPPCPolice.getText().equals("")) {
            NrOFPolicemen = 0;
        } else {
            NrOFPolicemen = Integer.parseInt(textFieldPPCPolice.getText());
        }
        if (textFieldPPCFire.getText().equals("")) {
            NRofFireFIghters = 0;
        } else {
            NRofFireFIghters = Integer.parseInt(textFieldPPCFire.getText());
        }
        if (textFieldPPCAmbu.getText().equals("")) {
            NRofAmbulancePeople = 0;
        } else {
            NRofAmbulancePeople = Integer.parseInt(textFieldPPCAmbu.getText());
        }

    }

    @FXML
    private void radioPolice(ActionEvent event) {
        if (radioButtonSmall.isSelected()) {
            textFieldPPCPolice.setText(Integer.toString(1));
            textFieldNRPolice.setText((Integer.toString(1)));
        }
        if (radioButtonMedium.isSelected()) {
            textFieldPPCPolice.setText(Integer.toString(5));
            textFieldNRPolice.setText((Integer.toString(3)));
        }
        if (radioButtonLarge.isSelected()) {
            textFieldPPCPolice.setText(Integer.toString(10));
            textFieldNRPolice.setText((Integer.toString(5)));
        }
    }

    @FXML
    private void radioFire(ActionEvent event) {
        if (radioButtonSmall.isSelected()) {
            textFieldPPCFire.setText(Integer.toString(1));
            textFieldNRFire.setText((Integer.toString(1)));
        }
        if (radioButtonMedium.isSelected()) {
            textFieldPPCFire.setText(Integer.toString(5));
            textFieldNRFire.setText((Integer.toString(3)));
        }
        if (radioButtonLarge.isSelected()) {
            textFieldPPCFire.setText(Integer.toString(10));
            textFieldNRFire.setText((Integer.toString(5)));
        }
    }

    @FXML
    private void radioAmbu(ActionEvent event) {
        if (radioButtonSmall.isSelected()) {
            textFieldPPCAmbu.setText(Integer.toString(1));
            textFieldNRAmbulance.setText((Integer.toString(1)));
        }
        if (radioButtonMedium.isSelected()) {
            textFieldPPCAmbu.setText(Integer.toString(5));
            textFieldNRAmbulance.setText((Integer.toString(3)));
        }
        if (radioButtonLarge.isSelected()) {
            textFieldPPCAmbu.setText(Integer.toString(10));
            textFieldNRAmbulance.setText((Integer.toString(5)));
        }
    }
}
