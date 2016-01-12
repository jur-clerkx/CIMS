/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class StartSimController implements Initializable {
    @FXML
    private Button btnStart;
    @FXML
    private CheckBox cBoxSim;
    
    public static boolean is_sim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void startClick(MouseEvent event) {
        
        if(cBoxSim.isSelected())
        {
           is_sim = true;
        }
        else
        {
            is_sim= false;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OperatorMain.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Operator");
            stage.setScene(new Scene(root1));
            stage.show();
            
            Stage stage2 = (Stage)cBoxSim.getScene().getWindow();
            stage2.close();
        } catch (Exception x) {
            System.out.println(x.getMessage());
        }
    }
    
}
