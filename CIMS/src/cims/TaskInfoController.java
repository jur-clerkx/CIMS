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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class TaskInfoController implements Initializable {
    @FXML
    private GridPane TaskInfo;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldLocation;
    @FXML
    private Button buttonOK;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonDelete;
    @FXML
    private ComboBox<?> comboboxUrgency;
    @FXML
    private ComboBox<?> comboboxStatus;
    @FXML
    private TextArea textAreaDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buttonAdd(MouseEvent event) {
    }

    @FXML
    private void buttonRemove(MouseEvent event) {
    }

    @FXML
    private void buttonOK(MouseEvent event) {
    }

    @FXML
    private void buttonCancel(MouseEvent event) {
    }

    
}
