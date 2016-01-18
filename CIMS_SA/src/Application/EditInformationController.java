/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Global.Domain.PublicUser;
import Situational_Awareness.Domain.Information;
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
import javafx.scene.control.ToggleButton;
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
    private TextField txtURL;
    @FXML
    private AnchorPane thisAnchor;

    private Information info;
    private ObservableList<Information> obsInformationList;
    @FXML
    private ToggleButton toggleButton;

    private PublicUser user;
    private boolean simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        simulation = LoginGuiController.simulation;
        comboInformation.setOnAction((event) -> {
            if (simulation) {
                int index = -1;
                for (Information infoLoop : LoginGuiController.information) {
                    if (infoLoop.equals(comboInformation.getSelectionModel().getSelectedItem())) {
                        index = LoginGuiController.information.indexOf(infoLoop);
                    }
                }
                info = LoginGuiController.information.get(index);

                txtID.setText("" + info.getId());
                txtName.setText(info.getName());
                txtDescription.setText(info.getDescription());
                txtLocation.setText(info.getLocation());
                txtNRofVictims.setText(Integer.toString(info.getCasualities()));
                if(info.getImageURL() != null) {
                    txtURL.setText(info.getImageURL());
                } else {
                    txtURL.setText("Application/untitled.png");
                }
                
                Image newImage = new Image(txtURL.getText());
                imageView.setImage(newImage);
                txtArea.setText(Integer.toString(info.getImpact()));
                fillRadio();
            } else {
                try {
                    if (CIMS_SA.con.getUser() != null) {

                        info = CIMS_SA.con.getInformation(LoginGuiController.SelectedInfoID);
                        txtID.setText("" + info.getId());
                        txtName.setText(info.getName());
                        txtDescription.setText(info.getDescription());
                        txtLocation.setText(info.getLocation());
                        txtNRofVictims.setText(Integer.toString(info.getCasualities()));
                        txtURL.setText(info.getImageURL());
                        Image newImage = new Image(txtURL.getText());
                        imageView.setImage(newImage);
                        txtArea.setText(Integer.toString(info.getImpact()));
                        fillRadio();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EditInformationController.class.getName()).log(Level.SEVERE, null, ex);
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
        if (!simulation) {
            try {
                obsInformationList.addAll(CIMS_SA.con.getInformation((int) CIMS_SA.con.getUser().getUserId()));
            } catch (Exception ex) {
                System.out.println("Error filling combobox");
            }
        } else {
            obsInformationList.addAll(LoginGuiController.information);
        }

        comboInformation.getItems().addAll(obsInformationList);

        if (LoginGuiController.SelectedInfoID != 0) {
            if (simulation) {
                int infoID = MainOperatorController.SelectedInformationID;

                for (Information infoLoop : LoginGuiController.information) {
                    if (infoLoop.getId() == infoID) {
                        int indexFound = LoginGuiController.information.indexOf(infoLoop);
                        info = LoginGuiController.information.get(indexFound);
                    }
                }
                txtID.setText("" + info.getId());
                txtName.setText(info.getName());
                txtDescription.setText(info.getDescription());
                txtLocation.setText(info.getLocation());
                txtNRofVictims.setText(Integer.toString(info.getCasualities()));
                txtURL.setText(info.getImageURL());
                Image newImage = new Image(txtURL.getText());
                imageView.setImage(newImage);
                txtArea.setText(Integer.toString(info.getImpact()));
                fillRadio();
            } else {

                try {
                    if (CIMS_SA.con.getUser() != null) {

                        info = CIMS_SA.con.getInformation(LoginGuiController.SelectedInfoID);
                        txtID.setText("" + info.getId());
                        txtName.setText(info.getName());
                        txtDescription.setText(info.getDescription());
                        txtLocation.setText(info.getLocation());
                        txtNRofVictims.setText(Integer.toString(info.getCasualities()));
                        txtURL.setText(info.getImageURL());
                        Image newImage = new Image(txtURL.getText());
                        imageView.setImage(newImage);
                        txtArea.setText(Integer.toString(info.getImpact()));
                        fillRadio();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EditInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    /**
     * Fills all Radio buttons
     */
    private void fillRadio() {
        if (info.getDanger() == 0) {
            radioSmall.setSelected(true);
        }
        if (info.getDanger() == 1) {
            radioMedium.setSelected(true);
        }
        if (info.getDanger() == 2) {
            radioLarge.setSelected(true);
        }
        if (info.getToxic() == 0) {
            radioNo.setSelected(true);
        }
        if (info.getToxic() == 1) {
            radioYes.setSelected(true);
        }
    }

    @FXML
    private void RegisterInformation(MouseEvent event
    ) {
        String name = txtName.getText();

        boolean Private;
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
            Private = true;
        } else {
            Private = false;
        }
        if (simulation) {
            user = new PublicUser("Bas", "Koch", "", "00000");
            Information editedInfo = new Information(info.getTask(), name, txtDescription.getText(), txtLocation.getText(),
                    Integer.parseInt(txtNRofVictims.getText()), toxic, danger, Integer.parseInt(txtArea.getText()), null, user);
            /*Information infoOG = LoginGuiController.information.get(0);
             if (infoOG != null) {
             LoginGuiController.information.remove(infoOG);
             LoginGuiController.information.add(info);
             }
             obsInformationList.addAll(LoginGuiController.information);*/

            if (editedInfo != null) {
                Information infoFound = null;
                for (Information infoLoop : LoginGuiController.information) {
                    if (infoLoop.getId() == editedInfo.getId()) {
                        infoFound = infoLoop;
                    }
                }
                LoginGuiController.information.remove(infoFound);
                LoginGuiController.information.add(editedInfo);

            }
            obsInformationList.clear();
            obsInformationList.addAll(LoginGuiController.information);

        } else {
            if (CIMS_SA.con.EditInformation(name, txtDescription.getText(),
                    txtLocation.getText(), Integer.parseInt(txtNRofVictims.getText()),
                    toxic, danger, Integer.parseInt(txtArea.getText()), txtURL.getText(),
                    (int) this.comboInformation.getSelectionModel().getSelectedItem().getId(), Private)
                    == true) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successfull");
                alert.setContentText("Information succesfully edited");
                alert.showAndWait();
            }
        }

    }

    @FXML
    private void Cancel(MouseEvent event
    ) {

        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("HomeSub.fxml"));
            thisAnchor.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
