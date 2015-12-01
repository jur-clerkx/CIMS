/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Situational_Awareness.Information;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class EditInformationController implements Initializable {

    @FXML
    private ImageView imageView;
    @FXML
    private Button btnSaveEdit;
    @FXML
    private Button btnCancel;
    @FXML
    private ComboBox<Information> comboInformation;
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

    private Information info;
    private ObservableList<Information> obsInformationList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ToggleGroup group1 = new ToggleGroup();
        ToggleGroup group2 = new ToggleGroup();
        radioLarge.setToggleGroup(group1);
        radioMedium.setToggleGroup(group1);
        radioSmall.setToggleGroup(group1);

        radioNo.setToggleGroup(group2);
        radioYes.setToggleGroup(group2);

        obsInformationList = FXCollections.observableArrayList();
        try
        {
        obsInformationList.addAll(Connection.ConnectionController.getPublicInformation(Connection.ConnectionController.user.getUser_ID()));    
        }
        catch(Exception ex)
        {
            System.out.println("Error filling combobox");
        }
        comboInformation.getItems().addAll(obsInformationList);

        if (LoginGuiController.SelectedInfoID != 0) {
            try {
                if (Connection.ConnectionController.user != null) {
                    
                                      
                    info = LoginGuiController.myController.getInformation(LoginGuiController.SelectedInfoID);
                    txtName.setText(info.getFirstName());
                    txtLastname.setText(info.getLastName());
                    txtDescription.setText(info.getDescription());
                    txtLocation.setText(info.getLocation());
                    txtNRofVictims.setText(Integer.toString(info.getCasualities()));
                    txtURL.setText(info.getURL());
                    Image newImage = new Image(txtURL.getText());
                    imageView.setImage(newImage);
                    txtArea.setText(Integer.toString(info.getImpact()));
                }
            } catch (IOException ex) {
                Logger.getLogger(EditInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void RegisterInformation(MouseEvent event) {
        String name = txtName.getText() + " " + txtLastname.getText();

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
        if (LoginGuiController.myController.EditInformation(name, txtDescription.getText(), txtLocation.getText(), Integer.parseInt(txtNRofVictims.getText()), toxic, danger, Integer.parseInt(txtArea.getText()), txtURL.getText(), LoginGuiController.SelectedInfoID)
                == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Successfull");
            alert.setContentText("Information succesfully edited");
            alert.showAndWait();
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

}
