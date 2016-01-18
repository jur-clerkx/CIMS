/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Situational_Awareness.Domain.Information;
import Global.Domain.PublicUser;
import Global.Domain.User;
import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
    

    public boolean simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        simulation = LoginGuiController.simulation;
        selectedInformation = null;
        selectedUser = null;
        ComboInformation.setOnAction((event) -> {
            selectedInformation = ComboInformation.getSelectionModel().getSelectedItem();
            txtName.setText("" + selectedInformation.getId());
            //txtLastname.setText(selectedInformation.getName());
            txtDescription.setText(selectedInformation.getDescription());
            txtLocation.setText(selectedInformation.getLocation());
            txtNRofVictims.setText(Integer.toString(selectedInformation.getCasualities()));
            txtArea.setText(Integer.toString(selectedInformation.getImpact()));
            fillRadio();

        });
        comboUser.setOnAction((event) -> {
            selectedUser = comboUser.getSelectionModel().getSelectedItem();
            txtLastname.setText(selectedUser.getFirstname() + " " + selectedUser.getLastname());
        });

        ToggleGroup group1 = new ToggleGroup();
        ToggleGroup group2 = new ToggleGroup();
        radioLarge.setToggleGroup(group1);
        radioMedium.setToggleGroup(group1);
        radioSmall.setToggleGroup(group1);

        radioNo.setToggleGroup(group2);
        radioYes.setToggleGroup(group2);

        obsInformationList = FXCollections.observableArrayList();
        if (!simulation) {
            try {
                obsInformationList.addAll(CIMS_SA.con.getPublicInformation((int)CIMS_SA.con.getUser().getUserId()));
            } catch (Exception ex) {

                System.out.println("Error filling combobox");
            }
        } else {
            obsInformationList.addAll(LoginGuiController.information);
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
            if (!simulation) {
                obsUserList.addAll(CIMS_SA.con.getUsers());
            } else {
                User user = new PublicUser("Bas", "Koch", "123456789", "password");
                ArrayList<User> users = new ArrayList<User>();
                users.add(user);
                user = new PublicUser("Jur", "Clerkx", "234567891", "password");
                users.add(user);
                for (User u : users) {
                    PublicUser userTemp = (PublicUser) u;
                    obsUserList.add(userTemp);
                }
            }

            // Dummy Data:
            //obsInformationList.add(new Information(1, 1, "Leggo", "Eindhoven", 4, false, 2, 3));
            //obsUserList.add(new PublicUser(2, "Bas", "Koch", "123456"));
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
            if (simulation) {
                if (selectedUser != null && selectedInformation != null) {
            
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information sent");
                    alert.setContentText("Information was sent to user.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Information is null");
                    alert.showAndWait();
                }
            } else {
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
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Selects the radio buttons according to the value from Information
     */
    private void fillRadio() {
        if (selectedInformation.getDanger() == 0) {
            radioSmall.setSelected(true);
        }
        if (selectedInformation.getDanger() == 1) {
            radioMedium.setSelected(true);
        }
        if (selectedInformation.getDanger() == 2) {
            radioLarge.setSelected(true);
        }
        if (selectedInformation.getToxic() == 0) {
            radioNo.setSelected(true);
        }
        if (selectedInformation.getToxic() == 1) {
            radioYes.setSelected(true);
        }
    }

}
