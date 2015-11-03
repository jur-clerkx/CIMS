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
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class HomeSubController implements Initializable {
    @FXML
    private ListView<?> listAvailableInformation;
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnOpenInfo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnRefresh(MouseEvent event) {
    }

    @FXML
    private void btnOpenInfo(MouseEvent event) {
    }
    
}
