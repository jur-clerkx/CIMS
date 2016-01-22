/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

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
 * @author rick
 */
public class ActiveUnitsController implements Initializable {

    @FXML
    private TableView tableviewActiveUnits;
    @FXML
    private AnchorPane MainField;
    @FXML
    private TableColumn<Unit, Number> tableUnitID;
    @FXML
    private TableColumn<Unit, String> tableUnitName;

    ObservableList<Unit> activeUnits;

    boolean Simulation;
    @FXML
    private Button btnRefresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Simulation = OperatorMainController.is_Simulation;
        if (!Simulation) {
            try {
                if (OperatorMainController.myController.user != null) {
                    activeUnits = FXCollections.observableArrayList(OperatorMainController.myController.getActiveUnits());
                }
            } catch (Exception ex) {
                activeUnits = FXCollections.observableArrayList();
            }
            //activeUnits = FXCollections.observableArrayList();
            //activeUnits.add(new Unit(1, "test", "test", "test"));
            tableUnitID.setCellValueFactory(new PropertyValueFactory<Unit, Number>("unitID"));
            tableUnitName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
            tableviewActiveUnits.setItems(activeUnits);

            tableviewActiveUnits.setRowFactory(tv -> {
                TableRow<Unit> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {

                        Unit myUnit = row.getItem();
                        try {
                            OperatorMainController.myController.selectedUnitID = (int) myUnit.getUnitID();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UnitInfo.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.DECORATED);
                            stage.setTitle("Unit");
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch (Exception x) {
                            System.out.println("Error" + x.getMessage());

                        }
                    }
                });
                return row;
            });
        } else {
            activeUnits = FXCollections.observableArrayList();
            activeUnits.addAll(OperatorMainController.active_Units);
            tableUnitID.setCellValueFactory(new PropertyValueFactory<Unit, Number>("unitID"));
            tableUnitName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
            tableviewActiveUnits.setItems(activeUnits);

            tableviewActiveUnits.setRowFactory(tv -> {
                TableRow<Unit> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {

                        Unit myUnit = row.getItem();
                        try {
                            OperatorMainController.selectedUnitID = (int) myUnit.getUnitID();
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UnitInfo.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.DECORATED);
                            stage.setTitle("Unit");
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch (Exception x) {
                            System.out.println("Error" + x.getMessage());

                        }
                    }
                });
                return row;
            });
        }
    }

    private void newButtonClick(MouseEvent event) {
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

    private void disbandButtonClick(MouseEvent event) throws ClassNotFoundException {
        Unit selectedUnit = (Unit) tableviewActiveUnits.getSelectionModel().getSelectedItem();

        if (!Simulation) {
            try {
                if (selectedUnit != null) {
                    OperatorMainController.myController.DisbandUnit((int) selectedUnit.getUnitID());
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
        } else {
            this.activeUnits.remove(selectedUnit);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Unit has been disbanned");
            alert.showAndWait();
        }
    }

    @FXML
    private void Refresh(MouseEvent event) {
        tableviewActiveUnits.getItems().clear();
        if (!Simulation) {
            try {
                if (OperatorMainController.myController.user != null) {
                    activeUnits = FXCollections.observableArrayList(OperatorMainController.myController.getActiveUnits());
                }
            } catch (Exception ex) {
                activeUnits = FXCollections.observableArrayList();
            }
        } else {
            activeUnits = FXCollections.observableArrayList();
            activeUnits.addAll(OperatorMainController.active_Units);
        }
        tableviewActiveUnits.setItems(activeUnits);
    }
}
