/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class InactiveUnitsController implements Initializable {
    @FXML
    private TableView<?> IUnitTable;
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonDisband;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void newClick(MouseEvent event) {
    }

    @FXML
    private void disbandClick  (MouseEvent event) {
    }
    
}
