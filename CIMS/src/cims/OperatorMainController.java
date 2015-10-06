/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class OperatorMainController implements Initializable {

    static ConnectionController myController;
    @FXML
    private AnchorPane MainField;
    @FXML
    private Hyperlink HyperLinkInactiveTask;
    @FXML
    private Hyperlink HyperLinkActiveTask;
    @FXML
    private Hyperlink HyperLinkInactiveUnit;
    @FXML
    private Hyperlink HyperLinkActiveUnit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            myController = new ConnectionController();
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
            }

            if (node != null) {
                MainField.getChildren().setAll(node);
            }
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
