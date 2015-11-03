/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Situational_Awareness.Information;
import Situational_Awareness.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Situational_Awareness.PublicUser;
import java.util.ArrayList;

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

    public ConnectionController() throws IOException {

        user = null;
        serverAddress = "145.93.61.124";
        Login("NickMullen", "0000");
    }

    private static void KillConnection() throws IOException {
        input.close();
        output.close();
    }

    public static boolean[] Login(String username, String password) throws IOException {
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
        boolean[] result = new boolean[2];
//        if(user instaceof PublicUser)
//        {
//            result[0] = true;
//            result[1] = false;
//        }
//        else if( user != null)
//        {
//            result[0] = true;
//            result[1] = false;
//        }

        return result;
    }
    /**
     * Get specified information from the database
     * @param infID
     * @return Specified information
     * @throws IOException 
     */
    public Information getInformation(int infID) throws IOException {
        try {
            String outputMessage = "SAPU";
            output.writeObject(outputMessage);
            output.writeObject(infID);
            return (Information)input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        }
        return null;
    }
    /**
     * Get all information from the database
     * @return Get all information
     * @throws IOException 
     */
    public ArrayList<Information> getAllInformation() throws IOException {
        try {
            String outputMessage = "SAPU";
            output.writeObject(outputMessage);
            return (ArrayList<Information> )input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        }
        return null;
    }
    /**
     * Get all users from the database
     * @return Get all users
     * @throws IOException 
     */
    public ArrayList<PublicUser> getUsers() throws IOException {
        try {
            String outputMessage = "SAPU2";
            output.writeObject(outputMessage);
            return (ArrayList<PublicUser>)input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        }
        return null;
    }
    /**
     * Send specified information to specified user
     * @param user
     * @param info
     * @return True if send
     * @throws IOException 
     */
    public boolean sendInfo(PublicUser user, Information info) throws IOException {
        try {
            String outputMessage = "SAPU";
            output.writeObject(outputMessage);
            output.writeObject(user.getID());
            output.writeObject(info.getID());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        }
        return false;
    }
}
