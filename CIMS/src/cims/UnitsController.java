/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Task;
import cims.Field_Operations.Unit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class UnitsController implements Initializable {

    @FXML
    private TableView<Unit> AUnitTable;
    @FXML
    private TableView<Unit> IUnitTable;
    @FXML
    private AnchorPane MainField;
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonDisband;
    @FXML
    private TableColumn<Unit, Number> AUnitID;
    @FXML
    private TableColumn<Unit, String> AUnitName;
    @FXML
    private TableColumn<Unit, String> AUnitStatus;
    @FXML
    private TableColumn<Unit, Number> IUnitID;
    @FXML
    private TableColumn<Unit, String> IUnitName;
    @FXML
    private TableColumn<Unit, String> IUnitStatus;

    ObservableList<Unit> ActiveUnits;
    ObservableList<Unit> InactiveUnits;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //InactiveUnits = (ObservableList<Unit>) OperatorMainController.myController.getInactiveUnits();
        //ActieUnits = (ObservableList<Unit>) OperatorMainController.myController.getInactiveUnits();
        AUnitTable.setRowFactory(tv -> {
            TableRow<Unit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    
                    Unit myUnit = row.getItem();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UnitInfo.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.DECORATED);
                        stage.setTitle("Unit" + myUnit.getUnitID());
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch (Exception x) {
                        System.out.println(x.getMessage());
                    }
                }
                });
            return row;
        });
        
         IUnitTable.setRowFactory(tv -> {
            TableRow<Unit> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Unit myUnit = row.getItem();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UnitInfo.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.DECORATED);
                        stage.setTitle("Unit" + myUnit.getUnitID());
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } catch (Exception x) {
                        System.out.println(x.getMessage());
                    }
                }
                });
            return row;
        });
        InactiveUnits = FXCollections.observableArrayList();
        InactiveUnits.add(new Unit(1,"test","test","test"));
        ActiveUnits = FXCollections.observableArrayList();
        ActiveUnits.add(new Unit(1,"test","test","test"));
        AUnitID.setCellValueFactory(new PropertyValueFactory<Unit, Number>("unitID"));
        AUnitName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
        AUnitStatus.setCellValueFactory(new PropertyValueFactory<Unit, String>("description"));
        IUnitID.setCellValueFactory(new PropertyValueFactory<Unit, Number>("unitID"));
        IUnitName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
        IUnitStatus.setCellValueFactory(new PropertyValueFactory<Unit, String>("description"));
        IUnitTable.setItems(InactiveUnits);
        AUnitTable.setItems(ActiveUnits);
    }

    @FXML
    private void newClick(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateUnit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Create Unit");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception x) {
            System.out.println(x.getMessage());
        }
    }

    @FXML
    private void disbandClick(MouseEvent event) {
        Unit selectedUnit = (Unit) IUnitTable.getSelectionModel().getSelectedItem();

        try {
            OperatorMainController.myController.DisbandUnit(selectedUnit.getUnitID());
        } catch (IOException ex) {
            Logger.getLogger(InactiveUnitsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
