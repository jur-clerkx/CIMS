/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import Field_Operations.InfoWrapper;
import Field_Operations.Roadmap;
import Situational_Awareness.Information;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jur
 */
public class FXMLShowInfoController implements Initializable {

    @FXML
    private ListView listViewInfo;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextArea textAreaDescription;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Fill the screen with current tasks roadmaps, if no task selected, fill with all roadmaps
        if (CIMSFieldOperationsUnitApp.currentTask != null) {
            ArrayList<Information> information = CIMSFieldOperationsUnitApp.con.getInformationByTask();
            //Put all infoobjects in wrappers
            ArrayList<InfoWrapper> infoWrappers = new ArrayList();
            for(Information i : information) {
                infoWrappers.add(new InfoWrapper(i));
            }
            if (information != null) {
                listViewInfo.getItems().addAll(infoWrappers);
            }
        }

        textFieldName.setText("No information selected");
        textAreaDescription.setText("No information selected");
    }

    @FXML
    private void handleInfoSelectionChanged(Event event) {
        InfoWrapper inf = (InfoWrapper) listViewInfo.getSelectionModel().getSelectedItem();
        if (inf != null) {
            textFieldName.setText(inf.getInfo().getName());
            textAreaDescription.setText(inf.getInfo().getDescription());
        } else {
            textFieldName.setText("No information selected");
            textAreaDescription.setText("No information selected");
        }
    }

}
