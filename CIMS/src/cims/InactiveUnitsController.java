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
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class InactiveUnitsController implements Initializable {
    @FXML
    private TableView<Unit> IUnitTable;
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonDisband;
    @FXML
    private AnchorPane MainField;
    
    ObservableList<Unit> InactiveUnits;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //InactiveUnits = (ObservableList<Unit>) OperatorMainController.myController.getInactiveUnits();
        InactiveUnits = FXCollections.observableArrayList();
        Unit test = new Unit("test","test");
        IUnitTable.setItems(InactiveUnits);
    }    

    @FXML
    private void newClick(MouseEvent event) {   Node node = null;
      try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateUnit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Create Unit");
            stage.setScene(new Scene(root1));  
            stage.show();
          }
          catch(Exception x) {
              System.out.println(x.getMessage());
          }
    }

    @FXML
    private void disbandClick  (MouseEvent event) {
       Unit selectedUnit = (Unit)IUnitTable.getSelectionModel().getSelectedItem();
    
        try {
            OperatorMainController.myController.DisbandUnit(selectedUnit.getUnitID());
        } catch (IOException ex) {
            Logger.getLogger(InactiveUnitsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
