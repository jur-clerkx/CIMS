/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Network.PublicUser;
import Network.User;
import Situational_Awareness.Information;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
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
    private TextField txtID;
    @FXML
    private TextField txtName;
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
    private CheckBox checkBoxPrivate;

    private ObservableList<Information> obsInformationList;
    private ObservableList<PublicUser> obsUserList;

    private Information selectedInformation;
    private PublicUser selectedUser;

    @FXML
    private AnchorPane thisAnchor;
    @FXML
    private ComboBox<Information> comboInformation;

    public boolean simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        simulation = LoginGuiController.isSimulation;
        selectedInformation = null;
        selectedUser = null;
        comboInformation.setOnAction((event) -> {
            selectedInformation = comboInformation.getSelectionModel().getSelectedItem();
            txtID.setText("" + selectedInformation.getID());
            //txtLastname.setText(selectedInformation.getName());
            txtDescription.setText(selectedInformation.getDescription());
            txtLocation.setText(selectedInformation.getLocation());
            txtNRofVictims.setText(Integer.toString(selectedInformation.getCasualities()));
            txtArea.setText(Integer.toString(selectedInformation.getImpact()));
            fillRadio();
            setImage(selectedInformation.getURL());
            
        });
        comboUser.setOnAction((event) -> {
            selectedUser = comboUser.getSelectionModel().getSelectedItem();
            txtName.setText(selectedUser.getFirstname() + " " + selectedUser.getLastname());
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
            ArrayList<Information> tempInfoList = new ArrayList<Information>();
            try {
                for(Information infoToAdd: CIMS_SA.con.getPublicInformation()) {
                    if(infoToAdd != null) {
                        tempInfoList.add(infoToAdd);
                    }
                }
                obsInformationList.addAll(tempInfoList);
            } catch (Exception ex) {

                System.out.println("Error filling combobox");
            }
        } else {
            obsInformationList.addAll(LoginGuiController.informationSimulation);
        }

        comboInformation.getItems().addAll(obsInformationList);

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
                PublicUser user = new PublicUser(1, "Bas", "Koch", "123456789");
                ArrayList<PublicUser> users = new ArrayList<PublicUser>();
                users.add(user);
                user = new PublicUser(1, "Jur", "Clerkx", "234567891");
                users.add(user);
                for (PublicUser u : users) {
                    PublicUser userTemp = (PublicUser) u;
                    obsUserList.add(userTemp);
                }
            }

            // Dummy Data:
            //obsInformationList.add(new Information(1, 1, "Leggo", "Eindhoven", 4, false, 2, 3));
            //obsUserList.add(new PublicUser(2, "Bas", "Koch", "123456"));
            comboUser.setItems(obsUserList);
//            comboInformation.setItems(obsInformationList);
//
//            comboInformation.setOnAction((event) -> {
//                selectedInformation = comboInformation.getSelectionModel().getSelectedItem();
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
//                txtID.setText("" + selectedUser.getUser_ID());
//                txtName.setText(selectedUser.getFirstname() + " " + selectedUser.getLastname());
//
//            });
//
        } catch (IOException ex) {
            Logger.getLogger(LoginGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setImage(String url) {
        String imageURL = url;
        Image img;
        if (imageURL != null) {
            if (!imageURL.isEmpty()) {
                img = new Image(imageURL);
                if (img.getHeight() == 0) {
                    img = new Image("Application/untitled.png");
                }

            } else {
                img = new Image("Application/untitled.png");
            }
        } else {
            img = new Image("Application/untitled.png");
        }

        imageView.setImage(img);
    }

    @FXML
    private void Cancel(MouseEvent event) {
        cancelSendInfoScreen();
    }
    
    private void cancelSendInfoScreen() {
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
                    boolean isPrivate = false;
                    if (checkBoxPrivate.isSelected()) {
                        isPrivate = true;
                    }
                    int privateInteger = 1; // 1 = public
                    if (isPrivate) {
                        privateInteger = 0;
                    }
                    if (CIMS_SA.con.sendInfo(selectedUser, selectedInformation, privateInteger) == true) {
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
        cancelSendInfoScreen();
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
