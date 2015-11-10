/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class CreateInformationController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageView.setImage(new Image("Application/untitled.png"));
    }

    @FXML
    private void RegisterInformation(MouseEvent event) {
    }

    @FXML
    private void Cancel(MouseEvent event) {
    }

    @FXML
    private void ClearInformation(MouseEvent event) {
    }

    @FXML
    private void MouseExit(MouseEvent event) {

        if (!txtURL.getText().isEmpty()) {
            Image img = new Image(txtURL.getText());
            while(img.getProgress() < 1) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CreateInformationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(img.getHeight() == 0) {
                img = new Image("Application/untitled.png");
            }
            imageView.setImage(img);
        }
    }

}
