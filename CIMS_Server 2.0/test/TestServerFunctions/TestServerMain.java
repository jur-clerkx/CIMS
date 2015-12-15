/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestServerFunctions;

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
 * @author sebas
 */
public class TestServerMain extends Application {
    @Override
    public void start(Stage primaryStage) {
   
        try {
            Parent root = FXMLLoader.load(getClass().getResource("TestConnectionFXML.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Test Functions");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.resizableProperty().set(false);
        } catch (IOException ex) {
            ex.getStackTrace();
        }
     
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
