/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

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
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class UnassignedTasksController implements Initializable {
    @FXML
    private TableView<?> UTaskTable;
    @FXML
    private Button buttonNew;
    @FXML
    private AnchorPane MainField;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonNewClick(MouseEvent event) 
    {
              Node node = null;
        try {
            node = (Node) FXMLLoader.load(getClass().getResource("CreateTask.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(ActiveTasksController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (node != null) {
            MainField.getChildren().setAll(node);
        }
    }

    @FXML
    private void cancelClick(MouseEvent event) {
    }
    
}
