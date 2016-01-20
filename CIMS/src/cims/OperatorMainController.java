/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Connection.ConnectionRunnable;
import Field_Operations.Progress;
import Field_Operations.Roadmap;
import Field_Operations.Task;
import Field_Operations.Unit;
import Network.User;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author rick
 */
public class OperatorMainController implements Initializable, Observer {

    static ConnectionRunnable myController;
    static int selectedUnitID;
    static int selectedTaskID;
    @FXML
    public AnchorPane MainField;
    @FXML
    private TitledPane TMenu;
    @FXML
    private Hyperlink HyperLinkITask;
    @FXML
    private Hyperlink HyperLinkATask;
    @FXML
    private TitledPane UMenu;
    @FXML
    private Hyperlink HyperLinkAUnit;
    @FXML
    private Hyperlink HyperLinkIUnit;
    @FXML
    private Hyperlink createRoadmap;
    @FXML
    private Hyperlink AssignRoadmap;

    public static Roadmap selectedRoadmap = null;

    /**
     * This is simulation
     */
    public static boolean is_Simulation = true;

    public static ArrayList<Unit> active_Units;
    public static ArrayList<Unit> inactive_Units;
    public static ArrayList<Task> active_Tasks;
    public static ArrayList<Task> inactive_Task;
    public static ArrayList<Roadmap> roadmaps;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        is_Simulation = StartSimController.is_sim;
        if (!is_Simulation) {
            myController = new ConnectionRunnable("SebasKoch", "1111");
            Thread t = new Thread(myController);
            t.setDaemon(true);
            t.start();
            if (myController != null) {
                myController.addObserver(this);
            }
        } else {
            active_Tasks = new ArrayList();
            inactive_Task = new ArrayList();
            active_Units = new ArrayList();
            inactive_Units = new ArrayList();
            roadmaps = new ArrayList();

            // Test Units
            Unit unit1 = new Unit(1,"PoliceForce1", "Standard Police Unit","");
            Unit unit2 = new Unit(2, "PoliceForce2", "Standard Police Unit","");
            Unit unit3 = new Unit(3,"PoliceForce3", "Standard Police Unit","");
            Unit unit4 = new Unit(4, "PoliceForce4", "Standard Police Unit","");
            Unit unit5 = new Unit(5, "PoliceForce5", "Standard Police Unit","");
            Unit unit11 = new Unit(6, "Ambulance1", "Standard Ambolance Unit","");
            Unit unit12 = new Unit(7, "FireSquad1", "Standard FireSquad Unit","");
            Unit unit13 = new Unit(8, "SWAT", "Specialized Police Unit","");
            Unit unit14 = new Unit(9, "SpecialistForce", "Specialized Unit for Disasters","");

            // Test Tasks
            Task task1 = new Task( 1,"Fire1", "High", "Unassigned", "Eindhoven Airport", "Plane on fire");
            Task task2 = new Task( 2,"Fire2", "High", "Unassigned", "Beukelaan 3", "Kitchen Fire");
            Task task3 = new Task( 3,"Fire3", "High", "Completed", "Ijswendelweg 4-7", "Car Fire");
            Task task4 = new Task( 4,"Health1", "High", "Completed", "Keukenseweg 2004", "Elder man");
            Task task5 = new Task( 5,"Health2", "Low", "Unassigned", "AH Eindhoven", "Elderly lady fell");
            Task task6 = new Task( 6,"Health3", "Low", "Completed", "Fontys Eindhoven", "Student fainted");
            Task task7 = new Task( 7,"Robbery", "High", "Completed", "ABN-AMRO Eindhoven", "Bankheist");
            Task task8 = new Task( 8,"Accident", "Medium", "Completed", "A2 - 54", "Car on car");
            Task task9 = new Task( 9,"Domestic Dispute", "Low", "Unassigned", "Heggeweg 24", "People heard screaming");

            // Test Personeel
            User u = new User(101,"dave", "test", "male", "Boss", "Fire", "1-1-2001", 1);
            User u1 = new User(102, "dave", "test", "male", "Boss", "Medical", "1-1-2001", 1);
            User u2 = new User(103, "dave", "test", "male", "Boss", "Fire", "1-1-2001", 1);

            unit1.addUser(u);
            unit2.addUser(u);
            unit3.addUser(u);
            unit4.addUser(u);
            unit5.addUser(u);
            unit11.addUser(u1);
            unit12.addUser(u2);
            unit13.addUser(u);
            unit14.addUser(u1);
            unit14.addUser(u2);
            unit14.addUser(u);
            
            
            
            Roadmap roadmap1 = new Roadmap(101,"Domestic Dispute", "When at the scene stay calm and try to calm the situation down.");
            Roadmap roadmap2 = new Roadmap(102,"Fire", "Never risk your own life.");

            unit1.setTask(task1);
            unit2.setTask(task1);
            unit3.setTask(task2);
            unit11.setTask(task5);
            unit12.setTask(task9);
            unit13.setTask(task9);

            task1.addUnit(unit1);
            task1.addUnit(unit2);
            task2.addUnit(unit3);
            task5.addUnit(unit11);
            task9.addUnit(unit12);
            task9.addUnit(unit13);
            task1.updateProgress(new Progress(101, u2, task1,"Evacuation comes first"));
            
            task5.updateProgress(new Progress(101, u2, task1,"Check for broken bones"));
            
            roadmaps.add(roadmap1);
            roadmaps.add(roadmap2);

            active_Tasks.add(task1);
            active_Tasks.add(task2);
            active_Tasks.add(task5);
            active_Tasks.add(task9);
            inactive_Task.add(task3);
            inactive_Task.add(task4);
            inactive_Task.add(task6);
            inactive_Task.add(task7);
            inactive_Task.add(task8);

            active_Units.add(unit1);
            active_Units.add(unit2);
            active_Units.add(unit3);
            active_Units.add(unit11);
            active_Units.add(unit12);
            active_Units.add(unit13);
            inactive_Units.add(unit4);
            inactive_Units.add(unit14);
            inactive_Units.add(unit5);

        }
    }

    @FXML
    private void MenuClick(MouseEvent event) {
        try {
            String url = event.getSource().toString();
            Node node = null;
            if (url.contains("ATask")) {
                node = (Node) FXMLLoader.load(getClass().getResource("ActiveTasks.fxml"));
            } else if (url.contains("TMenu")) {
                node = (Node) FXMLLoader.load(getClass().getResource("Tasks.fxml"));
            } else if (url.contains("ITask")) {
                node = (Node) FXMLLoader.load(getClass().getResource("UnassignedTasks.fxml"));
            } else if (url.contains("AUnit")) {
                node = (Node) FXMLLoader.load(getClass().getResource("ActiveUnits.fxml"));
            } else if (url.contains("IUnit")) {
                node = (Node) FXMLLoader.load(getClass().getResource("InactiveUnits.fxml"));
            } else if (url.contains("UMenu")) {
                node = (Node) FXMLLoader.load(getClass().getResource("Units.fxml"));
            } else if (url.contains("create")) {
                node = (Node) FXMLLoader.load(getClass().getResource("RoadmapCreate.fxml"));
            } else if (url.contains("Assign")) {
                node = (Node) FXMLLoader.load(getClass().getResource("AssignRoadmap.fxml"));
            } else if (url.contains("Roadmap")) {
                node = (Node) FXMLLoader.load(getClass().getResource("AllRoadmaps.fxml"));
            }

            if (node != null) {
                MainField.getChildren().setAll(node);
            }
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof String) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update");
                alert.setContentText((String) arg);
                alert.showAndWait();
            });
        }

        //Check if login is succesfull
        ConnectionRunnable con = (ConnectionRunnable) o;
        //Remove as listener from connection

        //Check if login succeeded
        if (con.getStatus() != 1) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Connection Closed.");
                alert.showAndWait();
                con.deleteObserver(this);
                System.exit(1);
            });

        }
    }
}
