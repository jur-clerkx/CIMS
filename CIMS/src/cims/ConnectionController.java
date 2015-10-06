/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import Network.User;
import cims.Field_Operations.Task;
import cims.Field_Operations.Unit;
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

    public ConnectionController() throws IOException {

    }

    public static void main(String[] args) throws IOException {
        serverAddress = "145.93.61.91";
        Login("NickMullen", "0000");
    }

    public boolean DisbandUnit(int ID) throws IOException {
        try {
            output.writeObject("FO;Disband");
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

    

    boolean CreateUnit(String Name, String Location, int size, String selectedSpecials, int PoliceCars, int FireTruck, int Ambulances, int Policemen, int FireFighters, int AmbulancePeople) {
        return true;
    }

    ArrayList<Unit> getInactiveUnits() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    ArrayList<Task> getActiveTasks(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    ArrayList<Task> getUnassignedTasks(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    void removeActiveTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    boolean createTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean editUnitInfo(String text, String text0, int size, String selectedSpecials, TextField textfieldPPCPolice, int NrOfFireTrucks, int NrOfAmbulances, int NrOFPolicemen, int NRofFireFIghters, int NRofAmbulancePeople) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    void cancelTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
