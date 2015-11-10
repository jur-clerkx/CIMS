/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Connection.ConnectionController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class LoginGuiController implements Initializable {

    @FXML
    private TextField txtUserIDLogin;
    @FXML
    private TextField txtPasswordLogin;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtVoornaam;
    @FXML
    private TextField txtAchternaam;
    @FXML
    private TextField txtBSNNummer;
    @FXML
    private TextField txtPasswordCreate;
    @FXML
    private Button btnCreate;
    public static ConnectionController myController;
    public static int SelectedInfoID = 0;
    private boolean[] result;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            myController = new ConnectionController();
        } catch (IOException ex) {
            Logger.getLogger(LoginGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Login(MouseEvent event) {
        try {
            if (txtUserIDLogin.getText().equals("test") && txtPasswordLogin.getText().equals("test")) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("MainOperator.fxml"));
                    Scene scene = new Scene(root);
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("User Information");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    primaryStage.resizableProperty().set(false);
                    CIMS_SA.primaryStage.close();
                } catch (IOException ex) {
                    System.out.println("Error when opening UserGui");
                }
            } else {
                result = myController.Login(txtUserIDLogin.getText(), txtPasswordLogin.getText());

                if (result[0] == true && result[1] == false) {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("MainUser.fxml"));
                        Scene scene = new Scene(root);
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("User Information");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        primaryStage.resizableProperty().set(false);
                        CIMS_SA.primaryStage.close();
                    } catch (IOException ex) {
                        System.out.println("Error when opening UserGui");
                    }
                } else if (result[0] == true && result[1] == true) {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("MainOperator.fxml"));
                        Scene scene = new Scene(root);
                        Stage primaryStage = new Stage();
                        primaryStage.setTitle("User Information");
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        primaryStage.resizableProperty().set(false);
                        CIMS_SA.primaryStage.close();
                    } catch (IOException ex) {
                        System.out.println("Error when opening UserGui");
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error occured with login");
        }

    }

    @FXML
    private void CreateUser(MouseEvent event) {
    }

}
