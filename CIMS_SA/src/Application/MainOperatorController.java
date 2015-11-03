/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class MainOperatorController implements Initializable {
    @FXML
    private Button btnHome;
    @FXML
    private Button btnAddInformation;
    @FXML
    private Button btnEditInformation;
    @FXML
    private Button btnSendInfo;
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
        // TODO
    }    

    @FXML
    private void btnHome(MouseEvent event) {
    }

    @FXML
    private void addInformation(MouseEvent event) {
    }

    @FXML
    private void btnEditInformation(MouseEvent event) {
    }

    @FXML
    private void btnSendInfo(MouseEvent event) {
    }

    @FXML
    private void btnSearch(MouseEvent event) {
    }
    
}
