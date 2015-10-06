/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import cims.Field_Operations.Unit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class CreateTaskController implements Initializable {
    @FXML
    private GridPane TaskInfo;
    @FXML
    private TextField textfieldTaskID;
    @FXML
    private TextField textfieldTaskName;
    @FXML
    private TextField textfieldTaskLocation;
    @FXML 
    private ListView listviewAvailableUnits;
    @FXML 
    private ListView listviewAssignedUnits;
    @FXML 
    private Button buttonAssignUnit;
    @FXML 
    private Button buttonRevokeUnit;
    @FXML
    private ComboBox comboboxUrgency;
    @FXML
    private ComboBox comboboxStatus;
    @FXML
    private TextArea textareaDescription;
    @FXML 
    private Button buttonCreateUnit;
    @FXML 
    private Button buttonCancel;
    
    ObservableList<String> AvailableList = FXCollections.observableArrayList("Unit 1", "Unit 2", "Unit 3");        
    ObservableList<String> AssignedList = FXCollections.observableArrayList();    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboboxUrgency.getItems().addAll("High", "Medium", "Low");
        comboboxStatus.getItems().addAll("Open", "Closed");
        //TODO: populate available units from connection to observablelist
        listviewAvailableUnits.setItems(AvailableList);
    }    
    @FXML
    private void assignButtonClick(MouseEvent event) {
        int selectedItem = listviewAvailableUnits.getSelectionModel().getSelectedIndex();
        String Content = AvailableList.get(selectedItem);
        AvailableList.remove(selectedItem);
        AssignedList.add(Content);
        listviewAvailableUnits.setItems(AvailableList);
        listviewAssignedUnits.setItems(AssignedList);
        
    }
     @FXML
    private void revokeButtonClick(MouseEvent event) {
        int selectedItem = listviewAssignedUnits.getSelectionModel().getSelectedIndex();
        String Content = AssignedList.get(selectedItem);
        AssignedList.remove(selectedItem);
        AvailableList.add(Content);
        listviewAssignedUnits.setItems(AssignedList);
        listviewAvailableUnits.setItems(AvailableList);
    }
     @FXML
    private void createButtonClick(MouseEvent event) {
        int taskID = Integer.parseInt(textfieldTaskID.getText());
        String taskName = textfieldTaskName.getText();
        String taskLocation = textfieldTaskLocation.getText();
        String urgency = comboboxUrgency.getSelectionModel().getSelectedItem().toString();
        String status = comboboxStatus.getSelectionModel().getSelectedItem().toString();
        
        
        
        OperatorMainController.myController.createTask();
    }
     @FXML
    private void cancelButtonClick(MouseEvent event) {
        
    Stage stage = (Stage) buttonCancel.getScene().getWindow();

    stage.close();
    
    }
    
    
}
