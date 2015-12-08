/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Connection.ConnectionRunnable;
import Connection.PropertiesMediator;
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

    public static Stage primaryStage;
    public static PropertiesMediator props;
    public static ConnectionRunnable con;

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;
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
