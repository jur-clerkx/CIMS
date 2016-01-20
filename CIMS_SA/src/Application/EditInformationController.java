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
        simulation = LoginGuiController.isSimulation;
        comboInformation.setOnAction((event) -> {
            if (simulation) {
                int index = -1;
                for (Information infoLoop : LoginGuiController.informationSimulation) {
                    Information infoSelectionTemp = comboInformation.getSelectionModel().getSelectedItem();
                    if (infoLoop.getID() == infoSelectionTemp.getID()) {
                        index = LoginGuiController.informationSimulation.indexOf(infoLoop);
                    }
                }
                info = LoginGuiController.informationSimulation.get(index);

                this.setPageInformation(info);
            } else {
                try {
                    if (CIMS_SA.con.getUser() != null) {
                        Information infoSelectionTemp = comboInformation.getSelectionModel().getSelectedItem();

                        info = CIMS_SA.con.getInformation(infoSelectionTemp.getID());
                        this.setPageInformation(info);
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
            ArrayList<Information> tempInfoList = new ArrayList<Information>();
            try {
                for (Information infoToAdd : CIMS_SA.con.getPublicInformation()) {
                    if (infoToAdd != null) {
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

        if (MainOperatorController.SearchedInformationID != 0) {
            if (!simulation) {
                int infoID = MainOperatorController.SearchedInformationID;
                try {
                    info = CIMS_SA.con.getInformation(infoID);
                } catch (IOException ex) {
                    Logger.getLogger(EditInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.setPageInformation(info);
            }

        }

        if (LoginGuiController.SelectedFromAvailableListInfoID != 0) {
            if (simulation) {
                int infoID = LoginGuiController.SelectedFromAvailableListInfoID;

                for (Information infoLoop : LoginGuiController.informationSimulation) {
                    if (infoLoop.getID() == infoID) {
                        int indexFound = LoginGuiController.informationSimulation.indexOf(infoLoop);
                        info = LoginGuiController.informationSimulation.get(indexFound);
                    }
                }
                this.setPageInformation(info);
            } else {

                try {
                    if (CIMS_SA.con.getUser() != null) {

                        info = CIMS_SA.con.getInformation(LoginGuiController.SelectedFromAvailableListInfoID);
                        this.setPageInformation(info);
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
        String editedValue = null;

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
            user = new PublicUser(1, "Bas", "Koch", "00000");
            Information editedInfo = new Information(1, info.getTaskID(), name, txtDescription.getText(), txtLocation.getText(),
                    Integer.parseInt(txtNRofVictims.getText()), toxic, danger, Integer.parseInt(txtArea.getText()), txtURL.getText(), user, false);
            /*Information infoOG = LoginGuiController.informationSimulation.get(0);
             if (infoOG != null) {
             LoginGuiController.informationSimulation.remove(infoOG);
             LoginGuiController.informationSimulation.add(info);
             }
             obsInformationList.addAll(LoginGuiController.informationSimulation);*/

            if (editedInfo != null) {
                Information infoFound = null;
                for (Information infoLoop : LoginGuiController.informationSimulation) {
                    if (infoLoop.getID() == editedInfo.getID()) {
                        infoFound = infoLoop;

                    }
                }
                LoginGuiController.informationSimulation.remove(infoFound);
                LoginGuiController.informationSimulation.add(editedInfo);

            }
            obsInformationList.clear();
            obsInformationList.addAll(LoginGuiController.informationSimulation);

        } else {
            Information infoToEdit = null;
            if (LoginGuiController.SelectedFromAvailableListInfoID != 0) {
                try {
                    infoToEdit = CIMS_SA.con.getInformation(LoginGuiController.SelectedFromAvailableListInfoID);
                } catch (IOException ex) {
                    Logger.getLogger(EditInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                infoToEdit = this.comboInformation.getSelectionModel().getSelectedItem();
            }

            boolean result = false;
            if (infoToEdit != null) {
                if (!infoToEdit.getDescription().equals(txtDescription.getText())) {
                    result = editStringInServer(infoToEdit.getID(), "description", txtDescription.getText());
                }
                if (!infoToEdit.getLocation().equals(txtLocation.getText())) {
                    result = editStringInServer(infoToEdit.getID(), "location", txtLocation.getText());
                }
                if (!infoToEdit.getName().equals(name)) {
                    result = editStringInServer(infoToEdit.getID(), "name", name);
                }
                if (infoToEdit.getDanger() != danger) {
                    result = editIntInServer(infoToEdit.getID(), "danger", danger);
                }
                if (infoToEdit.getToxic() != toxic) {
                    result = editIntInServer(infoToEdit.getID(), "toxic", toxic);
                }
                if (infoToEdit.getCasualities() != Integer.parseInt(txtNRofVictims.getText())) {
                    result = editIntInServer(infoToEdit.getID(), "casualties", Integer.parseInt(txtNRofVictims.getText()));
                }
                if (infoToEdit.getImpact() != Integer.parseInt(txtArea.getText())) {
                    result = editIntInServer(infoToEdit.getID(), "impect", Integer.parseInt(txtArea.getText()));
                }
                if (infoToEdit.getURL() != null) {
                    if (!infoToEdit.getURL().equals(txtURL.getText())) {
                        result = editStringInServer(infoToEdit.getID(), "image", txtURL.getText());
                    }
                }
                if (toggleButton.isSelected()) {
                    result = editIntInServer(infoToEdit.getID(), "private", 1);
                } else {
                    result = editIntInServer(infoToEdit.getID(), "private", 0);
                }
            }


            /*result = CIMS_SA.con.EditInformation(name, txtDescription.getText(),
             txtLocation.getText(), Integer.parseInt(txtNRofVictims.getText()),
             toxic, danger, Integer.parseInt(txtArea.getText()), txtURL.getText(),
             (int) this.comboInformation.getSelectionModel().getSelectedItem().getID(), Private);*/
            if (result == true) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successfull");
                alert.setContentText("Information succesfully edited");
                alert.showAndWait();
            }
        }
        cancelEditInfoScreen();
    }

    private void cancelEditInfoScreen() {
        {

            try {
                Node node = (Node) FXMLLoader.load(getClass().getResource("HomeSub.fxml"));
                thisAnchor.getChildren().setAll(node);
            } catch (IOException ex) {
                Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Fills the page with the informationSimulation received.
     *
     * @param info
     */
    private void setPageInformation(Information info) {
        if (info != null) {
            txtID.setText("" + info.getID());
            txtName.setText(info.getName());
            txtDescription.setText(info.getDescription());
            txtLocation.setText(info.getLocation());
            txtNRofVictims.setText(Integer.toString(info.getCasualities()));
            setImage(info.getURL());
            txtArea.setText(Integer.toString(info.getImpact()));
            fillRadio();
        }

    }

    @FXML
    private void MouseExit(MouseEvent event) {
        setImage(txtURL.getText());
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

    /**
     * Edit the informationSimulation based on ID
     *
     * @param ID ID of the informationSimulation to edit
     * @param attribute Attribute that needs to be edited
     * @param text Text that the attribute is changed by.
     * @return
     */
    private boolean editStringInServer(int ID, String attribute, String text) {
        return CIMS_SA.con.EditInformationString(ID, attribute, text);
    }

    /**
     * Edit the informationSimulation based on ID
     *
     * @param ID ID of the informationSimulation to edit
     * @param attribute Attribute that needs to be edited
     * @param number Number that the attribute is changed by
     * @return
     */
    private boolean editIntInServer(int ID, String attribute, int number) {
        return CIMS_SA.con.EditInformationInt(ID, attribute, number);
    }

    @FXML
    private void Cancel(MouseEvent event
    ) {
        cancelEditInfoScreen();
    }

}
