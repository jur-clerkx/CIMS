/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Nick van der Mullen
 */
public class CIMS_SA extends Application {
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
       
            Parent root = FXMLLoader.load(getClass().getResource("LoginGui.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Operator");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.resizableProperty().set(false);      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
