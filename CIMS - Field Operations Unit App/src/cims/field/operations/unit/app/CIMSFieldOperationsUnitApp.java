/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.field.operations.unit.app;

import Field_Operations.Domain.Task;
import domain.ConnectionRunnable;
import domain.PropertiesMediator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jur
 */
public class CIMSFieldOperationsUnitApp extends Application {
    public static PropertiesMediator props;
    public static ConnectionRunnable con;
    public static Task currentTask;
    public static Task taskInfo;
    
    @Override
    public void start(Stage stage) throws Exception {
        //Load properties
        props = new PropertiesMediator();
        
        //Load UI
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
