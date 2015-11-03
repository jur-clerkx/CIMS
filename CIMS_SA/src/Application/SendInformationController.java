/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Connection.ConnectionController;
import Situational_Awareness.Information;
import Situational_Awareness.PublicUser;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class SendInformationController implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private Button btnSendInfo;
    @FXML
    private Button btnCancel;
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
    private ComboBox<PublicUser> comboUser;
    @FXML
    private ComboBox<Information> comboInformation;

    private ObservableList<Information> obsInformationList;
    private ObservableList<PublicUser> obsUserList;
    
    private Information selectedInformation;
    private PublicUser selectedUser;
    private ConnectionController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            myController = new ConnectionController();
            obsInformationList.addAll(myController.getAllInformation());
            obsUserList.addAll(myController.getUsers());
            
            comboUser.setItems(obsUserList);
            comboInformation.setItems(obsInformationList);
            
            
            
            comboInformation.setOnAction((event) -> {
                selectedInformation = comboInformation.getSelectionModel().getSelectedItem();
            });
            comboUser.setOnAction((event) -> {
                selectedUser = comboUser.getSelectionModel().getSelectedItem();
            });
            
            txtName.setText(selectedUser.getFirstname() + " " + selectedUser.getLastname());
            txtLocation.setText(selectedInformation.getLocation());
            txtDescription.setText(selectedInformation.getDescription());
            txtNRofVictims.setText("" + selectedInformation.getCasualities());
            if(selectedInformation.getToxic() == false) {
                radioNo.setSelected(true);
                radioYes.setSelected(false);
            } else {
                radioNo.setSelected(false);
                radioYes.setSelected(true);
            }
            txtArea.setText("" + selectedInformation.getImpact());
            if(selectedInformation.getDanger() == 3) {
                radioSmall.setSelected(false);
                radioMedium.setSelected(false);
                radioLarge.setSelected(true);
            } else if (selectedInformation.getDanger() == 2) {
                radioSmall.setSelected(false);
                radioMedium.setSelected(true);
                radioLarge.setSelected(false);
            } else {
                radioSmall.setSelected(true);
                radioMedium.setSelected(false);
                radioLarge.setSelected(false);
            }
            // TODO: Add image
            
        } catch (IOException ex) {
            Logger.getLogger(LoginGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void RegisterInformation(MouseEvent event) {
        try {
            if(myController.sendInfo(selectedUser, selectedInformation) == true) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information sent");
                alert.setContentText("Information was sent to user.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Information couldn't be sent to user.");
                alert.showAndWait();
            }
        } catch (IOException ex) {
            Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Cancel(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();

        stage.close();
    }
    
}
