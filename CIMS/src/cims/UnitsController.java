/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Field_Operations.Task;
import Field_Operations.Unit;
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
import javafx.scene.control.Alert;
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
        try {
//            InactiveUnits = FXCollections.observableArrayList();
//            InactiveUnits.add(new Unit(1, "test", "test", "test"));
//            ActiveUnits = FXCollections.observableArrayList();
//            ActiveUnits.add(new Unit(1, "test", "test", "test"));
            if (OperatorMainController.myController.user != null) {
                InactiveUnits =  FXCollections.observableArrayList(OperatorMainController.myController.getInactiveUnits());
                ActiveUnits = FXCollections.observableArrayList(OperatorMainController.myController.getActiveUnits());
            }
            AUnitTable.setRowFactory(tv -> {
                TableRow<Unit> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {

                        Unit myUnit = row.getItem();
                        try {
                            ConnectionController.selectedUnitID = myUnit.getUnitID();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UnitInfo.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.DECORATED);
                            stage.setTitle("Unit");
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch (Exception x) {
                            System.out.println("Error" + x.toString() + x.getMessage());
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
                            ConnectionController.selectedUnitID = myUnit.getUnitID();
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

            AUnitID.setCellValueFactory(new PropertyValueFactory<Unit, Number>("unitID"));
            AUnitName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
            AUnitStatus.setCellValueFactory(new PropertyValueFactory<Unit, String>("description"));
            IUnitID.setCellValueFactory(new PropertyValueFactory<Unit, Number>("unitID"));
            IUnitName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
            IUnitStatus.setCellValueFactory(new PropertyValueFactory<Unit, String>("description"));
            IUnitTable.setItems(InactiveUnits);
            AUnitTable.setItems(ActiveUnits);
        } catch (IOException ex) {
            Logger.getLogger(UnitsController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("An error has occured.");
            alert.showAndWait();
        }
    }

    @FXML
    private void disbandClick(MouseEvent event) throws ClassNotFoundException {
        Unit selectedUnit = (Unit) IUnitTable.getSelectionModel().getSelectedItem();

        try {
            if (selectedUnit != null) {
                OperatorMainController.myController.DisbandUnit(selectedUnit.getUnitID());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Please select a Unit");
                alert.showAndWait();
            }
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("An error has occured.");
            alert.showAndWait();
        }
    }

}
