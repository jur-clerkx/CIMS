/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;


import Field_Operations.Roadmap;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class allRoadmapsController implements Initializable {

    @FXML
    private ListView<Roadmap> roadmapView;
    @FXML
    private Button btnViewRoadmap;
    ObservableList<Roadmap> roadmaps;

    private boolean Simulation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Simulation = OperatorMainController.is_Simulation;
        if (!Simulation) {
            if (OperatorMainController.myController.user != null) {
                try {
                    roadmaps = FXCollections.observableArrayList(OperatorMainController.myController.getRoadmaps());
                } catch (IOException ex) {
                    Logger.getLogger(allRoadmapsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (roadmaps != null) {
                roadmapView.setItems(roadmaps);
            }
        } else {
            roadmaps = FXCollections.observableArrayList(OperatorMainController.roadmaps);

            if (roadmaps != null) {
                roadmapView.setItems(roadmaps);
            }
        }
    }

    @FXML
    private void mouseClick(MouseEvent event) {
        OperatorMainController.selectedRoadmap = (Roadmap) roadmapView.getSelectionModel().getSelectedItem();

        if (OperatorMainController.selectedRoadmap != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewRoadmap.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("View Roadmap");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception x) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("An error has occured.");
                alert.showAndWait();
            }
        }
    }

}
