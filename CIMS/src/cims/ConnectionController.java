/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick van der Mullen
 */
public class ConnectionController {

    private String serverAddress;
    private User user;
    private Socket s;
    ObjectOutputStream output;
    ObjectInputStream input;

    public ConnectionController() throws IOException {
        Socket s = new Socket(serverAddress, 9090);
        output = new ObjectOutputStream(s.getOutputStream());
        input = new ObjectInputStream(s.getInputStream());
    }

    public boolean DisbandUnit(int ID) throws IOException {
        try {
            output.writeObject("Disband");
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

    public boolean Login(String username, String password) throws IOException {
        s = new Socket(serverAddress, 9090);
        output = new ObjectOutputStream(s.getOutputStream());
        input = new ObjectInputStream(s.getInputStream());
        
        boolean reading = true;
        Thread read;
        read = new Thread() {
            @Override
            public void run() {
                try {
                    Object[] myObject = new Object[2];
                    myObject[0] = username;
                    myObject[1] = password;
                    output.writeObject(myObject);

                    while (reading) {
                        user = (User) input.readObject();
                        reading = false;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ConnectionController.class.getName()).log(Level.SEVERE, null, ex);
                    KillConnection();
                }

            }
        };
    }

    
    private void KillConnection() throws IOException
    {
        input.close();
        output.close();
    }
}
