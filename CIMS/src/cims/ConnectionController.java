/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Network.User;
import Field_Operations.Task;
import Field_Operations.Unit;
import com.sun.javaws.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;

/**
 *
 * @author Nick van der Mullen
 */
public class ConnectionController {

    private static String serverAddress;
    public static User user;
    private static Socket s;
    static ObjectOutputStream output;
    static ObjectInputStream input;
    public static int selectedUnitID = 0;
    public static int selectedTaskID = 0;
    private static Unit unitinfo;

    public ConnectionController() throws IOException {
        user = null;
        serverAddress = "145.93.61.43";
        //serverAddress = "145.93.101.166";
        Login("NickMullen", "0000");
    }

//    public static void main(String[] args) throws IOException {
//        user = new User();
//        //serverAddress = "145.93.61.45";
//        serverAddress = "127.0.0.1";
//        Login("NickMullen", "0000");
////        while (!user.authorized()) // unitinfo = getUnitInfo(1);
////        {
////        }
////        unitinfo = getUnitInfo(1);
////        System.out.println(unitinfo.toString());
//    }
    public boolean DisbandUnit(int ID) throws IOException, ClassNotFoundException {
        try {
            output.writeObject("FOOP3");
            Object[] myObject = new Object[1];
            myObject[0] = ID;
            output.writeObject(myObject);
            String message = (String) input.readObject();
            if (message != null) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
            return false;
        }
    }

    public static boolean Login(String username, String password) throws IOException {
        s = new Socket(serverAddress, 1234);
        output = new ObjectOutputStream(s.getOutputStream());
        input = new ObjectInputStream(s.getInputStream());

        boolean reading = true;
        Thread read;
        read = new Thread() {
            @Override
            public void run() {
                try {
                    String[] myObject = new String[2];
                    myObject[0] = username;
                    myObject[1] = password;
                    output.writeObject(myObject);

                    user = (User) input.readObject();
                    System.out.println(user.toString());
                } catch (IOException ex) {
                    Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
                    try {
                        KillConnection();
                    } catch (IOException ex1) {
                        Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        read.start();
        return true;
    }

    private static void KillConnection() throws IOException {
        input.close();
        output.close();
    }

    /**
     * Adds a new unit to the database
     *
     * @param Name
     * @param Location
     * @param size
     * @param selectedSpecials
     * @param PoliceCars
     * @param FireTruck
     * @param Ambulances
     * @param Policemen
     * @param FireFighters
     * @param AmbulancePeople
     * @return True if the unit was added
     * @throws IOException
     */
    boolean CreateUnit(String Name, String Location, int size, String selectedSpecials, int PoliceCars, int FireTruck, int Ambulances, int Policemen, int FireFighters, int AmbulancePeople) throws IOException {

        Object[] myUnit = new Object[10];
        myUnit[0] = "Name";
        myUnit[1] = "Location";
        myUnit[2] = "Specials";
        myUnit[3] = "PoliceCars";
        myUnit[4] = "Firetrucks";
        myUnit[5] = "Ambulance";
        myUnit[6] = "Policemen";
        myUnit[7] = "Firefighters";
        myUnit[8] = "AmbulancePeople";
        myUnit[9] = "Size";

        try {
            String outputMessage = "FOOP2";

            output.writeObject(outputMessage);
            output.writeObject(myUnit);
            return true;

        } catch (IOException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
            return false;
        }

    }

    /**
     * Fetches all inactive units from the database
     *
     * @return List of inactive units
     * @throws IOException
     */
    ArrayList<Unit> getInactiveUnits() throws IOException {
        try {
            String outputMessage = "FOOP4";
            int type = 0;
            output.writeObject(outputMessage);
            output.writeObject(false);
            return (ArrayList<Unit>) input.readObject();

        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return null;
    }

    /**
     * Fetches all active units from the database
     *
     * @return List of active units
     * @throws IOException
     */
    ArrayList<Unit> getActiveUnits() throws IOException {
        try {
            String outputMessage = "FOOP4";
            int type = 1;
            output.writeObject(outputMessage);
            output.writeObject(true);
            return (ArrayList<Unit>) input.readObject();

        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return null;

    }

    /**
     * Fetches all inactive tasks from the database
     *
     * @return List of inactive tasks
     * @throws IOException
     */
    ArrayList<Task> getInactiveTasks() throws IOException {
        try {
            String outputMessage = "FOOP5";
            int type = 0;
            output.writeObject(outputMessage);
            output.writeObject(0);
            return (ArrayList<Task>) input.readObject();
        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return null;
    }

    /**
     * Fetches all active tasks from the database
     *
     * @return List of active tasks
     * @throws IOException
     */
    ArrayList<Task> getActiveTasks() throws IOException {
        try {
            String outputMessage = "FOOP5";
            int type = 1;
            output.writeObject(outputMessage);
            output.writeObject(1);
            return (ArrayList<Task>) input.readObject();
        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return null;
    }

    /**
     * Removes an active task from the database
     *
     * @param taskID
     * @throws IOException
     */
    void removeActiveTask(int taskID) throws IOException {
        try {
            String outputMessage = "FOOP7";
            output.writeObject(outputMessage);
            output.writeObject(taskID);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        }
    }

    /**
     * Removes an inactive task from the database
     *
     * @param taskID
     * @throws IOException
     */
    void removeInactiveTask(int taskID) throws IOException {
        try {
            String outputMessage = "FOOP7";
            output.writeObject(outputMessage);
            output.writeObject(taskID);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        }
    }

    /**
     * Adds a new task to the database
     *
     * @param taskID
     * @param name
     * @param urgency
     * @param status
     * @param location
     * @param description
     * @return True if the task was created
     * @throws IOException
     */
    boolean createTask(int taskID, String name, String urgency, String status, String location, String description) throws IOException {
        Object[] myTask = new Object[12];
        myTask[0] = "TaskID";
        myTask[1] = taskID;
        myTask[2] = "Name";
        myTask[3] = name;
        myTask[4] = "Urgency";
        myTask[5] = urgency;
        myTask[6] = "Status";
        myTask[7] = status;
        myTask[8] = "Location";
        myTask[9] = location;
        myTask[10] = "Description";
        myTask[11] = description;

        try {
            String outputMessage = "FOOP6";

            output.writeObject(outputMessage);
            output.writeObject(myTask);
            return true;

        } catch (IOException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
            return false;
        }
    }

    /**
     * Edits a units information
     *
     * @param Name
     * @param Location
     * @param size
     * @param selectedSpecials
     * @param PoliceCars
     * @param FireTruck
     * @param Ambulances
     * @param Policemen
     * @param FireFighters
     * @param AmbulancePeople
     * @return True if information was changed
     * @throws IOException
     */
    boolean editUnitInfo(String Name, String Location, int size, String selectedSpecials, TextField PoliceCars, int FireTruck, int Ambulances, int Policemen, int FireFighters, int AmbulancePeople) throws IOException {
       
       Object[] myUnit = new Object[10];
        myUnit[0] = "Name";
        myUnit[1] = "Location";
        myUnit[2] = "Specials";
        myUnit[3] = "PoliceCars";
        myUnit[4] = "Firetrucks";
        myUnit[5] = "Ambulance";
        myUnit[6] = "Policemen";
        myUnit[7] = "Firefighters";
        myUnit[8] = "AmbulancePeople";
        myUnit[9] = "Size";

        try {
            String outputMessage = "FOOP1";

            output.writeObject(outputMessage);
            output.writeObject(selectedUnitID);
            output.writeObject(myUnit);
            return true;

        } catch (IOException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
            return false;
        }
    }

    /**
     * Fetches a units information from the database
     *
     * @param unitID
     * @return Unit object that was requested
     * @throws IOException
     */
    public static Unit getUnitInfo(int unitID) throws IOException {
        try {
            String outputMessage = "FOUS2";

            output.writeObject(outputMessage);
            output.writeObject(unitID);
            return (Unit) input.readObject();

        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return null;
    }

    /**
     * Cancels a task so it can't be assigned anymore
     *
     * @param taskID
     * @throws IOException
     */
    void cancelTask(int taskID) throws IOException {
        try {
            String outputMessage = "FOOP9";

            output.writeObject(outputMessage);
            output.writeObject(taskID);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        }
    }

    /**
     * Assigns a task to units
     *
     * @param taskID
     * @param units
     * @throws IOException
     */
    void assignTask(int taskID, Object[] units) throws IOException {
        try {
            String outputMessage = "FOOP8";

            output.writeObject(outputMessage);
            output.writeObject(taskID);
            output.writeObject(units);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        }
    }

    /**
     * Edits a tasks information
     *
     * @param taskID
     * @param task
     * @return True if information was changed
     * @throws IOException
     */
    boolean editTask(int taskID, Task task) throws IOException {
        Object[] myTask = new Object[12];
        myTask[0] = "ID";
        myTask[1] = taskID;
        myTask[2] = "Name";
        myTask[3] = task.getName();
        myTask[4] = "Urgency";
        myTask[5] = task.getUrgency();
        myTask[6] = "Status";
        myTask[7] = task.getStatus();
        myTask[8] = "Location";
        myTask[9] = task.getLocation();
        myTask[10] = "Description";
        myTask[11] = task.getDescription();

        try {
            String outputMessage = "FOOP10";

            output.writeObject(outputMessage);
            output.writeObject(taskID);
            output.writeObject(myTask);
            return true;

        } catch (IOException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
            return false;
        }
    }
}
