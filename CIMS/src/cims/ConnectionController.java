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
    private static User user;
    private static Socket s;
    static ObjectOutputStream output;
    static ObjectInputStream input;
    public static int selectedUnitID = 0;
    private static Unit unitinfo;

    public ConnectionController() throws IOException {

    }

    public static void main(String[] args) throws IOException {
        user = new User();
        serverAddress = "145.93.60.189";
        Login("NickMullen", "0000");
        while (!user.authorized()) // unitinfo = getUnitInfo(1);
        {
        }
        unitinfo = getUnitInfo(1);
        System.out.println(unitinfo.toString());
    }

    public boolean DisbandUnit(int ID) throws IOException {
        try {
            output.writeObject("FOOP4");
            Object[] myObject = new Object[1];
            myObject[0] = ID;
            output.writeObject(myObject);
            return true;
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

    boolean CreateUnit(String Name, String Location, int size, String selectedSpecials, int PoliceCars, int FireTruck, int Ambulances, int Policemen, int FireFighters, int AmbulancePeople) throws IOException {

        Object[] myUnit = new Object[20];
        myUnit[0] = "Name";
        myUnit[1] = Name;
        myUnit[2] = "Location";
        myUnit[3] = Location;
        myUnit[4] = "Specials";
        myUnit[5] = selectedSpecials;
        myUnit[6] = "PoliceCars";
        myUnit[7] = PoliceCars;
        myUnit[8] = "Firetrucks";
        myUnit[9] = FireTruck;
        myUnit[10] = "Ambulance";
        myUnit[11] = Ambulances;
        myUnit[12] = "Policemen";
        myUnit[13] = Policemen;
        myUnit[14] = "Firefighters";
        myUnit[15] = FireFighters;
        myUnit[16] = "AmbulancePeople";
        myUnit[17] = AmbulancePeople;
        myUnit[18] = "Size";
        myUnit[19] = size;

        try {
            String outputMessage = "FOOP1";

            output.writeObject(outputMessage);
            output.writeObject(myUnit);
            return true;

        } catch (IOException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
            return false;
        }

    }

    ArrayList<Unit> getInactiveUnits() throws IOException {
        try {
            String outputMessage = "FOOP30";
            output.writeObject(outputMessage);
            return (ArrayList<Unit>) input.readObject();

        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return null;
    }

    ArrayList<Unit> getActiveUnits90() throws IOException
    {
        try {
            String outputMessage = "FOOP31";
            output.writeObject(outputMessage);
            return (ArrayList<Unit>) input.readObject();

        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return null;

    }

    ArrayList<Task> getActiveTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    ArrayList<Task> getUnassignedTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void removeActiveTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean createTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean editUnitInfo(String Name, String Location, int size, String selectedSpecials, TextField PoliceCars, int FireTruck, int Ambulances, int Policemen, int FireFighters, int AmbulancePeople) throws IOException {
        Object[] myUnit = new Object[18];
        myUnit[0] = "Name";
        myUnit[1] = Name;
        myUnit[2] = "Location";
        myUnit[3] = Location;
        myUnit[4] = "Specials";
        myUnit[5] = selectedSpecials;
        myUnit[6] = "PoliceCars";
        myUnit[7] = PoliceCars;
        myUnit[8] = "Firetrucks";
        myUnit[9] = FireTruck;
        myUnit[10] = "Ambulance";
        myUnit[11] = Ambulances;
        myUnit[12] = "Policemen";
        myUnit[13] = Policemen;
        myUnit[14] = "Firefighters";
        myUnit[15] = FireFighters;
        myUnit[16] = "AmbulancePeople";
        myUnit[17] = AmbulancePeople;
        myUnit[18] = "Size";
        myUnit[19] = size;

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

    void cancelTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
