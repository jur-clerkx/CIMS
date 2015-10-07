/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import Network.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jur
 */
public class ConnectionRunnable extends Observable implements Runnable {

    private String username;
    private String password;

    private User user;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    //0 = in process, 1 = logged in, 2 = logged out/acces denied
    private byte authorized;

    public ConnectionRunnable(String username, String password) {
        this.username = username;
        this.password = password;
        this.authorized = 0;
    }

    @Override
    public void run() {
        try {
            //Try to connect to server
            socket = new Socket(cims.field.operations.unit.app.CIMSFieldOperationsUnitApp.props.getServerURL(), 4444);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            //Try to log in
            String login = username + "/" + password;
            sendData(login.split("/"));
            //Delete password
            password = "apahvohiewaldjfpaoivwe";

            //Check if login succeeded
            user = (User) in.readObject();

            if (user == null) {
                throw new Exception("fail");
            } else {
                //Update status
                authorized = 1;
                setChanged();
                notifyObservers();
                clearChanged();
            }

            //Keep thread alive
            while (socket.isConnected()) {
                Thread.sleep(50);
            }
        } catch (Exception ex) {
            //Close connection
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception ex1) {
            }
            //Update status
            setStatus((byte) 2);
            setChanged();
            notifyObservers();
            clearChanged();
        }
    }

    private synchronized void sendData(Object data) throws IOException {
        out.writeObject(data);
    }

    /**
     * Gets the status of the connection (0 = connecting, 1 = connected, 2 =
     * failed/logged out)
     *
     * @return The status as byte
     */
    public synchronized byte getStatus() {
        return authorized;
    }

    /**
     * Sets the new status of the connection
     *
     * @param newStatus Must be >= 0 AND <= 2
     */
    private synchronized void setStatus(byte newStatus) {
        if (newStatus >= 0 && newStatus <= 2) {
            authorized = newStatus;
        }
    }
    
    /**
     * Gets the user that is currently logged in
     * @return Can be null if not logged in
     */
    public synchronized User getUser() {
        return this.user;
    }
}
