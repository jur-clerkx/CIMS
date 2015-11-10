/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Connection.ConnectionController;
import Situational_Awareness.Information;
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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class HomeSubController implements Initializable {

    @FXML
    private ListView<Information> listAvailableInformation;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnOpenInfo;
    @FXML
    private AnchorPane thisAnchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (ConnectionController.user != null) {
                ObservableList<Information> myObservableList = FXCollections.observableArrayList(LoginGuiController.myController.getAllInformation());
                listAvailableInformation.setItems(myObservableList);
            } else {
                ObservableList<Information> failedTest = FXCollections.observableArrayList();
                failedTest.add(new Information(1, 1, "Connection Failed", "Server", 1, false, 0, 1));
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeSubController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnRefresh(MouseEvent event) {
        listAvailableInformation.refresh();
    }

    @FXML
    private void btnOpenInfo(MouseEvent event) {
        try {
            Information info = (Information) listAvailableInformation.getSelectionModel().getSelectedItem();

            LoginGuiController.SelectedInfoID = info.getID();
            Node node = (Node) FXMLLoader.load(getClass().getResource("EditInformation.fxml"));
            thisAnchor.getChildren().setAll(node);

        } catch (IOException ex) {
            Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
