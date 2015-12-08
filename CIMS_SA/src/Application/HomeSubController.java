/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Network.PublicUser;
import Situational_Awareness.Information;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
    public ListView<Information> listAvailableInformation;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnOpenInfo;
    @FXML
    private AnchorPane thisAnchor;
    private ObservableList<Information> myObservableList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timer t;
        if (CIMS_SA.con.getUser() instanceof PublicUser) {
            
            if (CIMS_SA.con.getUser() != null) {
                try {
                    myObservableList = FXCollections.observableArrayList(CIMS_SA.con.getPublicInformation(CIMS_SA.con.getUser().getUser_ID()));
                    listAvailableInformation.setItems(myObservableList);
                    t = new Timer();
                    t.scheduleAtFixedRate(new TimerTask() {

                        @Override
                        public void run() {
                            try {
                                 myObservableList = FXCollections.observableArrayList(CIMS_SA.con.getPublicInformation(CIMS_SA.con.getUser().getUser_ID()));
                            } catch (IOException ex) {
                                Logger.getLogger(HomeSubController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }, 30, 30);
                } catch (IOException ex) {
                    Logger.getLogger(HomeSubController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            try {
                if (CIMS_SA.con.getUser() != null) {
                    myObservableList = FXCollections.observableArrayList(CIMS_SA.con.getAllInformation());
                    listAvailableInformation.setItems(myObservableList);
                     t = new Timer();
                    t.scheduleAtFixedRate(new TimerTask() {

                        @Override
                        public void run() {
                            try {
                                 myObservableList = FXCollections.observableArrayList(CIMS_SA.con.getPublicInformation(CIMS_SA.con.getUser().getUser_ID()));
                            } catch (IOException ex) {
                                Logger.getLogger(HomeSubController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }, 30, 30);
                }
            } catch (IOException ex) {
                Logger.getLogger(HomeSubController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void btnRefresh(MouseEvent event) {
        try {
            if (CIMS_SA.con.getUser() instanceof PublicUser) {
                ObservableList<Information> myObservableList = FXCollections.observableArrayList(CIMS_SA.con.getPublicInformation(CIMS_SA.con.getUser().getUser_ID()));
                listAvailableInformation.setItems(myObservableList);
            } else {

                ObservableList<Information> myObservableList = FXCollections.observableArrayList(CIMS_SA.con.getAllInformation());
                listAvailableInformation.setItems(myObservableList);
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeSubController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listAvailableInformation.refresh();
    }

    @FXML
    private void btnOpenInfo(MouseEvent event) {
        try {
            Information info = (Information) listAvailableInformation.getSelectionModel().getSelectedItem();

            if (info.getID() != -1) {
                LoginGuiController.SelectedInfoID = info.getID();
                Node node = (Node) FXMLLoader.load(getClass().getResource("EditInformation.fxml"));

                thisAnchor.getChildren().setAll(node);
            }
        } catch (IOException ex) {
            Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void DoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
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
}
