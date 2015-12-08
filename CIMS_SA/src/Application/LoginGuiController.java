/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Connection.ConnectionRunnable;
import Network.PublicUser;
import Network.User;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class LoginGuiController implements Initializable, Observer {

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
    public static ConnectionRunnable myController;
    public static int SelectedInfoID = 0;
    private boolean[] result;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void Login(MouseEvent event) {
        myController = new ConnectionRunnable(txtUserIDLogin.getText(), txtPasswordLogin.getText());
        Thread t = new Thread(myController);
        t.setDaemon(true);
        t.start();
        if (myController != null) {
            CIMS_SA.con = myController;
            CIMS_SA.con.addObserver(this);
        }
        if (CIMS_SA.con.getUser() != null) {
            if (CIMS_SA.con.getUser() instanceof PublicUser) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("MainUser.fxml"));
                    Scene scene = new Scene(root);
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("User Information");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    primaryStage.resizableProperty().set(false);
                    CIMS_SA.con.deleteObserver(this);
                    CIMS_SA.primaryStage.close();
                } catch (IOException ex) {
                    System.out.println("Error when opening UserGui");
                    Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (CIMS_SA.con.getUser() instanceof PublicUser) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("MainOperator.fxml"));
                    Scene scene = new Scene(root);
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("User Information");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    primaryStage.resizableProperty().set(false);
                    CIMS_SA.con.deleteObserver(this);
                    CIMS_SA.primaryStage.close();
                } catch (IOException ex) {
                    System.out.println("Error when opening UserGui");
                }
            }

        }

    }

    @FXML
    private void CreateUser(MouseEvent event) {
    }

    @Override
    public void update(Observable o, Object o1
    ) {
        //Check if login is succesfull
        ConnectionRunnable con = (ConnectionRunnable) o;
        //Remove as listener from connection
        con.deleteObserver(this);
        //Check if login succeeded
        if (con.getStatus() == 1) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    CIMS_SA.con = con;

                    if (CIMS_SA.con.getUser() != null) {

                        if (CIMS_SA.con.getUser() instanceof User) {
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
                                 Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (CIMS_SA.con.getUser() instanceof PublicUser) {
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("MainUser.fxml"));
                                Scene scene = new Scene(root);
                                Stage primaryStage = new Stage();
                                primaryStage.setTitle("User Information");
                                primaryStage.setScene(scene);
                                primaryStage.show();
                                primaryStage.resizableProperty().set(false);;
                                CIMS_SA.primaryStage.close();
                            } catch (IOException ex) {
                                System.out.println("Error when opening UserGui");
                            }
                        }

                    }

                }
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Username or password error.");
                    alert.showAndWait();
                }
            });
        }
    }
}
