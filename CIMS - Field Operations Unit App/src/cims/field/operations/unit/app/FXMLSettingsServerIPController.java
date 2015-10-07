/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jur
 */
public class FXMLSettingsServerIPController implements Initializable {

    @FXML
    private TextField textFieldServerIP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        textFieldServerIP.setText(CIMSFieldOperationsUnitApp.props.getServerURL());
    }

    @FXML
    public void handleButtonSave(ActionEvent event) {
        CIMSFieldOperationsUnitApp.props.setServerURL(textFieldServerIP.getText());
        //Close this window and go back to login
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            //Close current window
            Stage currentstage = (Stage) textFieldServerIP.getScene().getWindow();
            currentstage.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLSettingsServerIPController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleButtonCancel(ActionEvent event) {
        //Close this window and go back to login
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            //Close current window
            Stage currentstage = (Stage) textFieldServerIP.getScene().getWindow();
            currentstage.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLSettingsServerIPController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
