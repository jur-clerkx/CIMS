/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Connection.ConnectionRunnable;
import Situational_Awareness.Information;
import Field_Operations.Task;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class CreateInformationController implements Initializable, Observer {

    @FXML
    private ImageView imageView;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCLear;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtLocation;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtNRofVictims;
    @FXML
    private RadioButton radioYes;
    @FXML
    private RadioButton radioNo;
    @FXML
    private TextField txtArea;
    @FXML
    private RadioButton radioSmall;
    @FXML
    private RadioButton radioMedium;
    @FXML
    private RadioButton radioLarge;
    @FXML
    private TextField txtURL;
    @FXML
    private AnchorPane thisAnchor;
    @FXML
    private ToggleButton toggleButton;

    private boolean simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        simulation = LoginGuiController.simulation;
        imageView.setImage(new Image("Application/untitled.png"));
    }

    @FXML
    private void RegisterInformation(MouseEvent event) {
        String name = txtName.getText() + " " + txtLastname.getText();

        boolean toggle;
        int danger = 0;
        int toxic = 0;
        if (radioSmall.isSelected()) {
            danger = 0;
        } else if (radioMedium.isSelected()) {
            danger = 1;
        } else if (radioLarge.isSelected()) {
            danger = 2;
        }

        if (radioYes.isSelected()) {
            toxic = 1;
        } else if (radioNo.isSelected()) {
            toxic = 0;
        }

        if (toggleButton.isSelected()) {
            toggle = true;
        } else {
            toggle = false;
        }
        if (!simulation) {
            if (CIMS_SA.con.createInformation(name, txtDescription.getText(), txtLocation.getText(), Integer.parseInt(txtNRofVictims.getText()), toxic, danger, Integer.parseInt(txtArea.getText()), txtURL.getText(), toggle)
                    == true) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successfull");
                alert.setContentText("Information succesfully created");
                alert.showAndWait();
            }
        } else {
            Task task = new Task(1, "Task1", "High", "Open", "Eindhoven", "Fuuuuuuck");
            Network.PublicUser user = new Network.PublicUser(1, "Bas", "Koch", "12345467");
            Information simulationInformation = new Information(LoginGuiController.information.size() + 1, task, name, txtDescription.getText(), txtLocation.getText(), Integer.parseInt(txtNRofVictims.getText()), toxic, danger, Integer.parseInt(txtArea.getText()), txtURL.getText(), user, false);
            LoginGuiController.information.add(simulationInformation);
        }

    }

    @FXML
    private void Cancel(MouseEvent event) {
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("HomeSub.fxml"));
            thisAnchor.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ClearInformation(MouseEvent event) {

        txtArea.clear();
        txtDescription.clear();
        txtLastname.clear();
        txtLocation.clear();
        txtNRofVictims.clear();
        txtName.clear();
        txtURL.clear();

    }

    @FXML
    private void MouseExit(MouseEvent event) {
        if (!txtURL.getText().isEmpty()) {
            Image img = new Image(txtURL.getText());
            while (img.getProgress() < 1) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CreateInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (img.getHeight() == 0) {
                img = new Image("Application/untitled.png");
            }
            imageView.setImage(img);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //Check if login is succesfull
        ConnectionRunnable con = (ConnectionRunnable) o;
        //Remove as listener from connection
        con.deleteObserver(this);
        //Check if login succeeded
        if (con.getStatus() == 1) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        CIMS_SA.con = con;

                        Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();

                        //Close current window
                        Stage currentstage = (Stage) txtArea.getScene().getWindow();
                        currentstage.close();

                    } catch (IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Login failed.");
                        alert.showAndWait();
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
