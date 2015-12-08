/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Network.PublicUser;
import Situational_Awareness.Information;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    private ObservableList<Information> obsInformationList;
    private ObservableList<PublicUser> obsUserList;

    private Information selectedInformation;
    private PublicUser selectedUser;

    @FXML
    private AnchorPane thisAnchor;
    @FXML
    private ComboBox<Information> ComboInformation;
    private Information info;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

         ComboInformation.setOnAction((event) -> {

            if (CIMS_SA.con.getUser() != null) {

                info = ComboInformation.getValue();
                txtLastname.setText(info.getFirstName());
                txtDescription.setText(info.getDescription());
                txtLocation.setText(info.getLocation());
                txtNRofVictims.setText(Integer.toString(info.getCasualities()));
                txtArea.setText(Integer.toString(info.getImpact()));
                if (info.getDanger() == 0) {
                    radioSmall.setSelected(true);
                }
                if (info.getDanger() == 1) {
                    radioMedium.setSelected(true);
                }
                if (info.getDanger() == 1) {
                    radioLarge.setSelected(true);
                }
                if (info.getToxic() == 0) {
                    radioNo.setSelected(true);
                }
                if (info.getDanger() == 1) {
                    radioYes.setSelected(true);
                }
            }

        });
        ToggleGroup group1 = new ToggleGroup();
        ToggleGroup group2 = new ToggleGroup();
        radioLarge.setToggleGroup(group1);
        radioMedium.setToggleGroup(group1);
        radioSmall.setToggleGroup(group1);

        radioNo.setToggleGroup(group2);
        radioYes.setToggleGroup(group2);

        obsInformationList = FXCollections.observableArrayList();
        try {
            obsInformationList.addAll(CIMS_SA.con.getPublicInformation(CIMS_SA.con.getUser().getUser_ID()));
        } catch (Exception ex) {

            System.out.println("Error filling combobox");
        }
        ComboInformation.getItems().addAll(obsInformationList);
        
        ToggleGroup groupOne = new ToggleGroup();
        ToggleGroup groupTwo = new ToggleGroup();

        obsInformationList = FXCollections.observableArrayList();
        obsUserList = FXCollections.observableArrayList();
        radioNo.setToggleGroup(groupOne);
        radioYes.setToggleGroup(groupOne);
        radioSmall.setToggleGroup(groupTwo);
        radioMedium.setToggleGroup(groupTwo);
        radioLarge.setToggleGroup(groupTwo);

        try {
//           
//            if(CIMS_SA.con.getAllInformation() != null)
//            {
//            obsInformationList.addAll(CIMS_SA.con.getAllInformation());
//            }
            obsUserList.addAll(CIMS_SA.con.getUsers());
            comboUser.setItems(obsUserList);
//            ComboInformation.setItems(obsInformationList);
//
//            ComboInformation.setOnAction((event) -> {
//                selectedInformation = ComboInformation.getSelectionModel().getSelectedItem();
//                txtLocation.setText(selectedInformation.getLocation());
//                txtDescription.setText(selectedInformation.getDescription());
//                txtNRofVictims.setText("" + selectedInformation.getCasualities());
//                if (selectedInformation.getToxic() == 0) {
//                    radioNo.setSelected(true);
//                    radioYes.setSelected(false);
//                } else {
//                    radioNo.setSelected(false);
//                    radioYes.setSelected(true);
//                }
//                txtArea.setText("" + selectedInformation.getImpact());
//
//                if (selectedInformation.getDanger() == 3) {
//                    radioSmall.setSelected(false);
//                    radioMedium.setSelected(false);
//                    radioLarge.setSelected(true);
//                } else if (selectedInformation.getDanger() == 2) {
//                    radioSmall.setSelected(false);
//                    radioMedium.setSelected(true);
//                    radioLarge.setSelected(false);
//                } else {
//                    radioSmall.setSelected(true);
//                    radioMedium.setSelected(false);
//                    radioLarge.setSelected(false);
//                }
//                Image image;
//                if (selectedInformation.getImage() != null) {
//                    image = new Image(selectedInformation.getImage());
//                } else {
//                    image = new Image("Application/untitled.png");
//                }
//
//                imageView.setImage(image);
//            });
//            comboUser.setOnAction((event) -> {
//                selectedUser = comboUser.getSelectionModel().getSelectedItem();
//                txtName.setText("" + selectedUser.getUser_ID());
//                txtLastname.setText(selectedUser.getFirstname() + " " + selectedUser.getLastname());
//
//            });
//
        } catch (IOException ex) {
            Logger.getLogger(LoginGuiController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void SendInfo(MouseEvent event) {
        try {
            selectedUser = comboUser.getSelectionModel().getSelectedItem();
            selectedInformation = ComboInformation.getSelectionModel().getSelectedItem();
            if (selectedUser != null && selectedInformation != null) {
                if (CIMS_SA.con.sendInfo(selectedUser, selectedInformation) == true) {
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
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Information is null.");
                alert.showAndWait();
            }

        } catch (IOException ex) {
            Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
