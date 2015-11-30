/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import static Application.MainOperatorController.SelectedInformationID;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class MainUserController implements Initializable {

    @FXML
    private Button btnHome;
    @FXML
    private Button btnAddInformation;
    @FXML
    private Button btnEditInformation;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private AnchorPane AnchorMain;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("HomeSub.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnHome(MouseEvent event) {
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("HomeSub.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addInformation(MouseEvent event) {
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("CreateInformation.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnEditInformation(MouseEvent event) {
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("EditInformation.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSearch(MouseEvent event) {

        SelectedInformationID = Integer.parseInt(txtSearch.getText());

        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("EditInformation.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
