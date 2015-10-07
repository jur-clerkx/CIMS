/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import Network.User;
import domain.ConnectionRunnable;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jur
 */
public class FXMLMainController implements Initializable, Observer {

    @FXML
    private Tab tabQueue;
    @FXML
    private TextField textFieldID;
    @FXML
    private TextField textFieldFirstName;
    @FXML
    private TextField textFieldLastName;
    @FXML
    private TextField textFieldDate;
    @FXML
    private TextField textFieldGender;
    @FXML
    private TextField textFieldRank;
    @FXML
    private TextField textFieldSector;

    int counter = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add controller as listener to connection
        CIMSFieldOperationsUnitApp.con.addObserver(this);
        
        //Put info in fields
        User user = CIMSFieldOperationsUnitApp.con.getUser();
        textFieldID.setText("" + user.getUser_ID());
        textFieldFirstName.setText(user.getFirstname());
        textFieldLastName.setText(user.getLastname());
        textFieldDate.setText("none");
        textFieldGender.setText(user.getGender());
        textFieldRank.setText(user.getRank());
        textFieldSector.setText(user.getSector());
    }

    @FXML
    private void handleAddButtonAction(ActionEvent ae) {
        counter++;
        tabQueue.setText("Task Queue (" + counter + ")");
    }

    @Override
    public void update(Observable o, Object o1) {
        ConnectionRunnable con = (ConnectionRunnable) o;
        //Check if connection is lost
        if (con.getStatus() == 2) {

        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    closeApp();
                }
                
            });
        }
    }

    /**
     * Closes the application
     */
    private void closeApp() {
        //Close current window
        Stage currentstage = (Stage) tabQueue.getContent().getScene().getWindow();
        currentstage.close();
    }
}
