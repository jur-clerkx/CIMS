/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import domain.ConnectionRunnable;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Jur
 */
public class FXMLLoginController implements Initializable, Observer {

    @FXML
    private Label labelError;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private Button buttonLogin;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        ConnectionRunnable con = new ConnectionRunnable(textFieldUsername.getText(), passwordFieldPassword.getText());
        con.addObserver(this);
        Thread t = new Thread(con);
        t.setDaemon(true);
        t.start();
        buttonLogin.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Load in default username
        textFieldUsername.setText(CIMSFieldOperationsUnitApp.props.getUser());
    }

    @FXML
    public void handleServerIPMenuAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLSettingsServerIP.fxml"));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            //Close current window
            Stage currentstage = (Stage) textFieldUsername.getScene().getWindow();
            currentstage.close();
        } catch (IOException ex) {
            labelError.setVisible(true);
            labelError.setText("Switching window failed!");
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        //Check if login is succesfull
        ConnectionRunnable con = (ConnectionRunnable) o;
        //Remove as listener from connection
        con.deleteObserver(this);
        //Check if login succeeded
        if (con.getStatus() == 1) {
            CIMSFieldOperationsUnitApp.props.setUser(textFieldUsername.getText());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        CIMSFieldOperationsUnitApp.con = con;
                        
                        Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();

                        //Close current window
                        Stage currentstage = (Stage) textFieldUsername.getScene().getWindow();
                        currentstage.close();

                    } catch (IOException ex) {
                        labelError.setVisible(true);
                        labelError.setText("Logging in failed!");
                        buttonLogin.setDisable(false);
                    }
                }
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    labelError.setVisible(true);
                    labelError.setText("Wrong username and/or password!");
                    passwordFieldPassword.setText("");
                    buttonLogin.setDisable(false);
                }
            });
        }
    }

}
