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
public class InactiveUnitsController implements Initializable {

    @FXML
    private TableView<Unit> IUnitTable;
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonDisband;
    @FXML
    private AnchorPane MainField;
    @FXML
    private TableColumn<Unit, String> tableUnitName;
    @FXML
    private TableColumn<Unit, String> tableStatus;
    @FXML
    private TableColumn<Unit, Number> tableID;

    ObservableList<Unit> InactiveUnits;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (OperatorMainController.myController.user != null) {
                InactiveUnits = FXCollections.observableArrayList(OperatorMainController.myController.getInactiveUnits());
            }
        } catch (Exception ex) {
            InactiveUnits = FXCollections.observableArrayList();
        }

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
        tableID.setCellValueFactory(new PropertyValueFactory<Unit, Number>("unitID"));
        tableUnitName.setCellValueFactory(new PropertyValueFactory<Unit, String>("name"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<Unit, String>("description"));
        IUnitTable.setItems(InactiveUnits);
    }

    @FXML
    private void newClick(MouseEvent event) {
        Node node = null;
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
