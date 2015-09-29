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
    private TextField textID;
    @FXML
    private TextField textName;
    @FXML
    private ListView<?> AUnitListView;
    @FXML
    private TextField textLocation;
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonDelete;
    @FXML
    private ComboBox<?> comboBoxUrgency;
    @FXML
    private ComboBox<?> comboBoxStatus;
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
    private void okClicked(MouseEvent event) {
    }

    @FXML
    private void cancelClick(MouseEvent event) {
    }

    @FXML
    private void deleteClick(MouseEvent event) {
    }
    
}
