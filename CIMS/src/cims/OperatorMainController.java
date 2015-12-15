/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Connection.ConnectionRunnable;
import Field_Operations.Roadmap;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class OperatorMainController implements Initializable, Observer {

    static ConnectionRunnable myController;
    @FXML
    public AnchorPane MainField;
    @FXML
    private TitledPane TMenu;
    @FXML
    private Hyperlink HyperLinkITask;
    @FXML
    private Hyperlink HyperLinkATask;
    @FXML
    private TitledPane UMenu;
    @FXML
    private Hyperlink HyperLinkAUnit;
    @FXML
    private Hyperlink HyperLinkIUnit;
    @FXML
    private Hyperlink createRoadmap;
    @FXML
    private Hyperlink AssignRoadmap;

    public static Roadmap selectedRoadmap = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myController = new ConnectionRunnable("nickmullen", "0000");
        Thread t = new Thread(myController);
        t.setDaemon(true);
        t.start();
        if (myController != null) {
            myController.addObserver(this);
        }
        try {
            myController.RegisterObserver(this);
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void MenuClick(MouseEvent event) {
        try {
            String url = event.getSource().toString();
            Node node = null;
            if (url.contains("ATask")) {
                node = (Node) FXMLLoader.load(getClass().getResource("ActiveTasks.fxml"));
            } else if (url.contains("TMenu")) {
                node = (Node) FXMLLoader.load(getClass().getResource("Tasks.fxml"));
            } else if (url.contains("ITask")) {
                node = (Node) FXMLLoader.load(getClass().getResource("UnassignedTasks.fxml"));
            } else if (url.contains("AUnit")) {
                node = (Node) FXMLLoader.load(getClass().getResource("ActiveUnits.fxml"));
            } else if (url.contains("IUnit")) {
                node = (Node) FXMLLoader.load(getClass().getResource("InactiveUnits.fxml"));
            } else if (url.contains("UMenu")) {
                node = (Node) FXMLLoader.load(getClass().getResource("Units.fxml"));
            } else if (url.contains("create")) {
                node = (Node) FXMLLoader.load(getClass().getResource("RoadmapCreate.fxml"));
            } else if (url.contains("Assign")) {
                node = (Node) FXMLLoader.load(getClass().getResource("AssignRoadmap.fxml"));
            } else if (url.contains("Roadmap")) {
                node = (Node) FXMLLoader.load(getClass().getResource("AllRoadmaps.fxml"));
            }

            if (node != null) {
                MainField.getChildren().setAll(node);
            }
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof String) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update");
                alert.setContentText((String) arg);
                alert.showAndWait();
            });
        }

        //Check if login is succesfull
        ConnectionRunnable con = (ConnectionRunnable) o;
        //Remove as listener from connection
       
        //Check if login succeeded
        if (con.getStatus() != 1) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Username or password error.");
                alert.showAndWait(); 
                con.deleteObserver(this);
            });

        }
    }
}
