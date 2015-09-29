/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class ActiveTasksController implements Initializable {

    @FXML
    private TableView tableviewActiveTask;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonNew;
    @FXML
    private AnchorPane MainField;
    @FXML
    private TableColumn<?, ?> tableId;
    @FXML
    private TableColumn<?, ?> tableName;
    @FXML
    private TableColumn<?, ?> tableStatus;
    @FXML
    private TableColumn<?, ?> tableUnit;
    
    private int selectedID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }

    @FXML
    private void deleteButtonClick(MouseEvent event) {

    }

    @FXML
    private void newButtonClick(MouseEvent event) {
    Node node = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateTask.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Create Task");
            stage.setScene(new Scene(root1));  
            stage.show();
          }
          catch(Exception x) {
              System.out.println(x.getMessage());
          }
    }
}
