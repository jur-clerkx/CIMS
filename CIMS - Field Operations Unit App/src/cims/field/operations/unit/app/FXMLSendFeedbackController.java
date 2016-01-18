/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jur
 */
public class FXMLSendFeedbackController implements Initializable {

    @FXML
    private TextArea textAreaMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleSendFeedback(ActionEvent event) {
        CIMSFieldOperationsUnitApp.con.sendFeedback((int) CIMSFieldOperationsUnitApp.currentTask.getTaskID(), textAreaMessage.getText());
        Stage currentstage = (Stage) textAreaMessage.getScene().getWindow();
        currentstage.close();
    }

}
