/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import static Application.MainOperatorController.SearchedInformationID;
import Situational_Awareness.Information;
import Situational_Awareness.TwitterSearch;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nick van der Mullen
 */
public class MainUserController implements Initializable,Observer {

    @FXML
    private Button btnHome;
    @FXML
    private Button btnAddInformation;
    @FXML
    private Button btnEditInformation;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private AnchorPane AnchorMain;
    @FXML
    private Button btnTwitter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //CIMS_SA.con.addObserver(this);
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("HomeSub.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnHome(MouseEvent event) {
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("HomeSub.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addInformation(MouseEvent event) {
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("CreateInformation.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnEditInformation(MouseEvent event) {
        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("EditInformation.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnSearch(MouseEvent event) {

        SearchedInformationID = Integer.parseInt(txtSearch.getText());

        try {
            Node node = (Node) FXMLLoader.load(getClass().getResource("EditInformation.fxml"));
            AnchorMain.getChildren().setAll(node);
        } catch (IOException ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
       Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {

                        Parent root = FXMLLoader.load(getClass().getResource("LoginGuiController.fxml"));

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();

                        //Close current window
                        Stage currentstage = (Stage) txtSearch.getScene().getWindow();
                        currentstage.close();

                    } catch (IOException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Login failed.");
                        alert.showAndWait();
                    }
                }
            });
    }
    @FXML
    private void btnTwitter(MouseEvent event) {
        try {
           TwitterSearch ts = new TwitterSearch();
           //get tweet list
            ArrayList<Information> information = ts.twitterFeed("%23" + txtSearch.getText());
           
           for (Information info : information){
              //duplicate check 
              if(dupCheck(info)){ 
              //create information
              if(CIMS_SA.con.createInformation(info.getName(), info.getDescription(), info.getLocation(), 0, 0, 0, 0, info.getImage(), false)){
                  System.out.println("Twitter information retrieved");
              }else {
                  System.out.println("Twitter information retrieval error");
              }
              }
           }

        } catch (Exception ex) {
            Logger.getLogger(MainOperatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean dupCheck(Information inf) throws IOException{
        boolean checkDups = true;
        ArrayList<Information> chkInfo = CIMS_SA.con.getAllInformation();
        for(Information oldInfo : chkInfo){
            if(oldInfo.getDescription().equals(inf.getDescription())){
                checkDups = false;
            }
        }
        return checkDups;
    }


}

