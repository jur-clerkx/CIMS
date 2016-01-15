/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;


import Field_Operations.Domain.Material;
import Field_Operations.Domain.Task;
import Field_Operations.Domain.Unit;
import Field_Operations.Domain.Vehicle;
import Global.Domain.PrivateUser;
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
import javafx.scene.control.ToggleGroup;
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

    private boolean Simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Simulation = OperatorMainController.is_Simulation;
        if (!Simulation) {
            ToggleGroup group = new ToggleGroup();
            radiobuttonSmall.setToggleGroup(group);
            radiobuttonMedium.setToggleGroup(group);
            radiobuttonLarge.setToggleGroup(group);

            int ID = OperatorMainController.myController.selectedUnitID;
            mySelectedUnit = null;
            try {
                mySelectedUnit = (Unit) OperatorMainController.myController.getUnitInfo(ID);
            } catch (IOException ex) {
                Logger.getLogger(UnitInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (mySelectedUnit != null) {
                fillPage();
            }
        } else {
            ToggleGroup group = new ToggleGroup();
            radiobuttonSmall.setToggleGroup(group);
            radiobuttonMedium.setToggleGroup(group);
            radiobuttonLarge.setToggleGroup(group);

            int ID = OperatorMainController.selectedUnitID;
            mySelectedUnit = null;
            for (Unit i : OperatorMainController.inactive_Units) {
                if (i.getId() == ID) {
                    mySelectedUnit = i;
                }
            }

            for (Unit i : OperatorMainController.active_Units) {
                if (i.getId() == ID) {
                    mySelectedUnit = i;
                }
            }
            if (mySelectedUnit != null) {
                System.out.println(mySelectedUnit.getTasks());
                fillPage();
            }
        }

    }

    @FXML
    private void saveClick(MouseEvent event) {
        if (!Simulation) {
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
                alert.showAndWait();
            }
        } else {
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

            Unit myunit = new Unit(textfieldName.getText(), textfieldLocation.getText());
            
            
            
            for(int i = 0; i < NRofAmbulancePeople; i++)
            {
                myunit.addUser(new PrivateUser("test","test,","test","test","Medical","test",0,"test"));
            }
            
            for(int i = 0; i < NrOFPolicemen; i++)
            {
                myunit.addUser(new PrivateUser("test","test,","test","test","Police","test",0,"test"));
            }
            
            for(int i = 0; i < NRofFireFIghters; i++)
            {
                myunit.addUser(new PrivateUser("test","test,","test","test","Fire","test",0,"test"));
            }
            
            String selectedUnit = "inactive";
            Unit uss = null;
            for (Unit us : OperatorMainController.active_Units) {
                if (myunit.getId() == us.getId()) {
                    selectedUnit = "Active";
                    uss = us;
                }
            }

            for (Unit us : OperatorMainController.inactive_Units) {
                if (myunit.getId() == us.getId()) {
                    selectedUnit = "Inactive";
                    uss = us;
                }
            }
            if (uss != null) {
                myunit.setTask(uss.getTasks());
            }
            if (selectedUnit.equals("Active")) {
                int i = OperatorMainController.active_Units.indexOf(uss);
                OperatorMainController.active_Units.remove(i);
                OperatorMainController.active_Units.add(myunit);
            } else {
                int i = OperatorMainController.inactive_Units.indexOf(uss);
                OperatorMainController.inactive_Units.remove(i);
                OperatorMainController.inactive_Units.add(myunit);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);

            alert.setContentText("Unit succesfully created.");
            alert.showAndWait();
            Stage stage = (Stage) buttonCancel.getScene().getWindow();
            stage.close();

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
        if (mySelectedUnit.getMembers().size() <= 5) {
            radiobuttonSmall.setSelected(true);
        } else if (mySelectedUnit.getMembers().size() > 6 && mySelectedUnit.getMembers().size() < 10) {
            radiobuttonMedium.setSelected(true);
        } else {
            radiobuttonLarge.setSelected(true);
        }
        if (mySelectedUnit.getTasks() != null) {
            Task task = (Task) mySelectedUnit.getTasks();
            textfieldTaskID.setText(Integer.toString((int)task.getId()));
            textfieldTaskname.setText(task.getName());
        }

        for (Material m : mySelectedUnit.getMaterials()) {
            if (m.getType() == 1) {
                radiobuttonGas.setSelected(true);
            }
            if (m.getType() == 2) {
                radiobuttonFuel.setSelected(true);
            }
            if (m.getType() == 3) {
                radiobuttonExplosion.setSelected(true);
            }
            if (m.getType() == 4) {
                radiobuttonEarthquake.setSelected(true);
            }
            if (m.getType() == 5) {
                radiobuttonTerrorist.setSelected(true);
            }
        }

        for (PrivateUser u : mySelectedUnit.getMembers()) {

            if (u.getSector().contains("Police")) {
                policeUsers++;
                radiobuttonPolice.setSelected(true);
            } else if (u.getSector().contains("Medical")) {
                ambulanceUsers++;
                radiobuttonAmbulance.setSelected(true);
            } else if (u.getSector().contains("Fire")) {
                firefighters++;
                radiobuttonFireFighter.setSelected(true);
            }
        }

        for (Vehicle v : mySelectedUnit.getVehicles()) {
            if (v.getType()== 1) {
                firetrucks++;
            } else if (v.getType() == 2) {
                policeCars++;
            } else if (v.getType() == 3) {
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
