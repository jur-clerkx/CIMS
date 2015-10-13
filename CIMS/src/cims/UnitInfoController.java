/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Material;
import Field_Operations.Task;
import Field_Operations.Unit;
import Field_Operations.Vehicle;
import Network.User;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class UnitInfoController implements Initializable {

    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonCancel;
    @FXML
    private RadioButton radiobuttonSmall;
    @FXML
    private RadioButton radiobuttonMedium;
    @FXML
    private RadioButton radiobuttonLarge;
    @FXML
    private RadioButton radiobuttonPolice;
    @FXML
    private RadioButton radiobuttonFireFighter;
    @FXML
    private RadioButton radiobuttonAmbulance;
    @FXML
    private TextField textfieldName;
    @FXML
    private TextField textfieldLocation;
    @FXML
    private RadioButton radiobuttonGas;
    @FXML
    private RadioButton radiobuttonFuel;
    @FXML
    private RadioButton radiobuttonExplosion;
    @FXML
    private RadioButton radiobuttonTerrorist;
    @FXML
    private RadioButton radiobuttonEarthquake;
    @FXML
    private TextField textfieldPoliceCars;
    @FXML
    private TextField textfieldFiretrucks;
    @FXML
    private TextField textfieldAmbulances;
    @FXML
    private TextField textfieldPPCPolice;
    @FXML
    private TextField textfieldPPCFire;
    @FXML
    private TextField textfieldPPCAmbulance;
    @FXML
    private TextField textfieldTaskname;
    @FXML
    private TextField textfieldTaskID;

    private int NrOfPoliceCars;
    private int NrOfAmbulances;
    private int NrOfFireTrucks;
    private int NrOFPolicemen;
    private int NRofAmbulancePeople;
    private int NRofFireFIghters;
    private Unit mySelectedUnit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int ID = OperatorMainController.myController.selectedUnitID;
        mySelectedUnit = null;
        try {
            mySelectedUnit = ConnectionController.getUnitInfo(ID);
        } catch (IOException ex) {
            Logger.getLogger(UnitInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(mySelectedUnit != null)
        {
            fillPage();
        }

    }

    @FXML
    private void saveClick(MouseEvent event) {
        boolean succes = false;
        int size = 0;
        if (radiobuttonSmall.isSelected()) {
            size = 1;
        } else if (radiobuttonMedium.isSelected()) {
            size = 2;
        } else if (radiobuttonLarge.isSelected()) {
            size = 3;
        }
        convertToInt();
        try {
            succes = OperatorMainController.myController.editUnitInfo(textfieldName.getText(), textfieldLocation.getText(), size, getSelectedSpecials(), textfieldPPCPolice, NrOfFireTrucks, NrOfAmbulances, NrOFPolicemen, NRofFireFIghters, NRofAmbulancePeople);
        } catch (Exception ex) {
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if (succes) {
            alert.setContentText("Unit succesfully created.");
            alert.showAndWait();
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();
        } else {
            alert.setContentText("An error has occured");
        }

    }

    @FXML
    private void cancelClick(MouseEvent event) {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();

    }

 
    private String getSelectedSpecials() {
        String selected = "F";

        if (radiobuttonFuel.isSelected()) {
            selected += "2";
        }
        if (radiobuttonExplosion.isSelected()) {
            selected += "3";
        }
        if (radiobuttonGas.isSelected()) {
            selected += "4";
        }
        if (radiobuttonEarthquake.isSelected()) {
            selected += "5";
        }
        if (radiobuttonTerrorist.isSelected()) {
            selected += "6";
        }
        return selected;
    }

    private void convertToInt() {
        if (textfieldPoliceCars.getText().equals("")) {
            NrOfPoliceCars = -1;
        } else {
            NrOfPoliceCars = Integer.parseInt(textfieldPoliceCars.getText());
        }
        if (textfieldAmbulances.getText().equals("")) {
            NrOfAmbulances = -1;
        } else {
            NrOfAmbulances = Integer.parseInt(textfieldAmbulances.getText());
        }
        if (textfieldFiretrucks.getText().equals("")) {
            NrOfFireTrucks = -1;
        } else {
            NrOfFireTrucks = Integer.parseInt(textfieldFiretrucks.getText());
        }
        if (textfieldPPCPolice.getText().equals("")) {
            NrOFPolicemen = -1;
        } else {
            NrOFPolicemen = Integer.parseInt(textfieldPPCPolice.getText());
        }
        if (textfieldPPCFire.getText().equals("")) {
            NRofFireFIghters = -1;
        } else {
            NRofFireFIghters = Integer.parseInt(textfieldPPCFire.getText());
        }
        if (textfieldPPCAmbulance.getText().equals("")) {
            NRofAmbulancePeople = -1;
        } else {
            NRofAmbulancePeople = Integer.parseInt(textfieldPPCAmbulance.getText());
        }

    }

    private void fillPage() {

        int policeUsers = 0;
        int ambulanceUsers = 0;
        int firefighters = 0;
        int policeCars = 0;
        int firetrucks = 0;
        int ambulance = 0;
        
        if (!mySelectedUnit.getName().isEmpty()) {
            textfieldName.setText(mySelectedUnit.getName());
        }
        if (mySelectedUnit.getSize() <= 5) {
            radiobuttonSmall.arm();
        } else if (mySelectedUnit.getSize() > 6 && mySelectedUnit.getSize() < 10) {
            radiobuttonMedium.arm();
        } else {
            radiobuttonLarge.arm();
        }
        if (mySelectedUnit.getTask() != null) {
            Task task = (Task) mySelectedUnit.getTask();
            textfieldTaskID.setText(Integer.toString(task.getTaskID()));
            textfieldTaskname.setText(task.getName());
        }

        for (Material m : mySelectedUnit.getMaterials()) {
            if (m.getName().contains("Gas")) {
                radiobuttonGas.arm();
            }
            if (m.getName().contains("Fuel")) {
                radiobuttonFuel.arm();
            }
            if (m.getName().contains("Explosion")) {
                radiobuttonExplosion.arm();
            }
            if (m.getName().contains("Earth")) {
                radiobuttonEarthquake.arm();
            }
            if (m.getName().contains("Terror")) {
                radiobuttonTerrorist.arm();
            }
        }

        for (User u : mySelectedUnit.getMembers()) {

            if (u.getSector().contains("Police")) {
                policeUsers++;
            }
            else if(u.getSector().contains("Ambulance"))
            {
                ambulanceUsers++;
            }
            else if(u.getSector().contains("Fire"))
            {
                firefighters++;
            }
        }
        
        for(Vehicle v : mySelectedUnit.getVehicles())
        {
            if(v.getCarType().contains("Fire"))
            {
                firetrucks++;
            }
            else if(v.getCarType().contains("Police"))
            {
                policeCars++;
            }
            else if(v.getCarType().contains("Ambulance"))
            {
                ambulance++;
            }
        }
        
        textfieldPPCAmbulance.setText(Integer.toString(ambulanceUsers));
        textfieldPPCFire.setText(Integer.toString(firefighters));
        textfieldPPCPolice.setText(Integer.toString(policeUsers));
        textfieldPoliceCars.setText(Integer.toString(policeCars));
        textfieldFiretrucks.setText(Integer.toString(firetrucks));
        textfieldAmbulances.setText(Integer.toString(ambulance));
       
        
    }

}
