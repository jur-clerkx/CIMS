/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Connection.ConnectionRunnable;
import Field_Operations.Task;
import Network.PublicUser;
import Network.User;
import Situational_Awareness.Information;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    
    public static ArrayList<Information> information;
    public static boolean simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      information = new ArrayList<>();
    }

    @FXML
    private void Login(MouseEvent event) {
        if(txtUserIDLogin.getText().equals("Simulation") && txtPasswordLogin.getText().equals("Simulation")) {
            simulation = true;
            information = fillInformation();
        }
        if (!simulation) {
            myController = new ConnectionRunnable(txtUserIDLogin.getText(), txtPasswordLogin.getText());
            CIMS_SA.con = myController;
            CIMS_SA.con.addObserver(this);
            Thread t = new Thread(myController);
            t.setDaemon(true);
            t.start();
            
        } 
        if(CIMS_SA.con != null || simulation == true) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("MainOperator.fxml"));
                Scene scene = new Scene(root);
                Stage primaryStage = new Stage();
                primaryStage.setTitle("User Information");
                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.resizableProperty().set(false);
                //CIMS_SA.con.deleteObserver(this);
                CIMS_SA.primaryStage.close();
            } catch (IOException ex) {
                System.out.println("Error when opening UserGui");
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
            Platform.runLater(() -> {
                CIMS_SA.con = con;
                
                if (CIMS_SA.con.getUser() != null) {
                    if (CIMS_SA.con.getUser() instanceof User) {
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
                    } else if (CIMS_SA.con.getUser() instanceof PublicUser) {
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
                            Logger.getLogger(LoginGuiController.class.getName()).log(Level.SEVERE, null, ex);
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
                                CIMS_SA.primaryStage.close();
                            } catch (IOException ex) {
                                System.out.println("Error when opening UserGui");
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
    private ArrayList<Information> fillInformation() {
        ArrayList<Information> dummyInfoList = new ArrayList<Information>();
        Task task = new Task(1, "Task1", "High", "Open", "Eindhoven", "Fuuuuuuck");
        PublicUser user = new PublicUser(1, "Bas", "Koch", "12345467");
        Information simulationInformation = new Information(1, task, "Info1", "Fontys", task.getLocation(), 0, 0, 0, 0, "", user, false);
        //new Information((long)LoginGuiController.information.size() + 1, task, "Info1", "Fontys", task.getLocation(), 0, 0, 0, 5, null, user, false);

        
        dummyInfoList.add(simulationInformation);
        
        task = new Task(2, "Task2", "High", "Open", "Eindhoven", "Fuuuuuuck");
        simulationInformation = new Information(1, task, "Info2", "Fontys", task.getLocation(), 1, 1, 1, 50, "", user, false);
        dummyInfoList.add(simulationInformation);
        
        task = new Task(3, "Task3", "High", "Open", "Eindhoven", "Fuuuuuuck");
        simulationInformation = new Information(2, task, "Info3", "Fontys", task.getLocation(), 2, 2, 2, 100, null, user, false);
        dummyInfoList.add(simulationInformation);
        return dummyInfoList;
    }
}
