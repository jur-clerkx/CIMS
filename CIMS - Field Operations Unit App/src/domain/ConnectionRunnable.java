/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import Field_Operations.Task;
import Field_Operations.Unit;
import Network.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
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
    private Unit unit;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private boolean keepRunning;

    //0 = in process, 1 = logged in, 2 = logged out/acces denied
    private byte authorized;

    public ConnectionRunnable(String username, String password) {
        this.username = username;
        this.password = password;
        this.authorized = 0;
        this.keepRunning = true;
    }

    @Override
    public void run() {
        try {
            //Try to connect to server
            socket = new Socket(cims.field.operations.unit.app.CIMSFieldOperationsUnitApp.props.getServerURL(), 1234);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            //Try to log in
            String login = username + "/" + password;
            sendData(login.split("/"));
            //Delete password
            password = "apahvohiewaldjfpaoivwe";

            //Check if login succeeded
            user = (User) readData();
            
            //Get the unit of the user
            sendData("FOUS6");
            unit = (Unit) readData();

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
                if (getStatus() == 2 || keepRunning == false) {
                    throw new Exception("Connection ended!");
                }
            }
        } catch (Exception ex) {
            setStatus((byte) 2);
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
        out.flush();
    }
    
    private synchronized Object readData() throws IOException, ClassNotFoundException {
        return in.readObject();
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
     * Returns the task list if connected, else null
     *
     * @return Can be null
     */
    public synchronized ArrayList<Task> getTaskList() {
        if (getStatus() == 1) {
            try {
                sendData("FOUS4");
                return (ArrayList<Task>) readData();
            } catch (IOException ex) {
                System.out.println("IO exception!");
                setStatus((byte) 2);
                return null;
            } catch (ClassNotFoundException ex) {
                System.out.println("Class not found exception!");
                setStatus((byte) 2);
                return null;
            }
        } else {
            return null;
        }
    }
    
    /**
     * Gets the Unit that the user is part of
     * @return Returns the unit that the user is part of, can be null.
     */
    public synchronized Unit getUsersUnit() {
        if (getStatus() == 1) {
            try {
                sendData("FOUS6");
                return (Unit) readData();
            } catch (IOException ex) {
                System.out.println("IO exception!");
                setStatus((byte) 2);
                return null;
            } catch (ClassNotFoundException ex) {
                System.out.println("Class not found exception!");
                setStatus((byte) 2);
                return null;
            }
        } else {
            return null;
        }
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
     *
     * @return Can be null if not logged in
     */
    public synchronized User getUser() {
        return this.user;
    }
    
    /**
     * Gets the unit of the user that is currently logged in
     * @return Can be null if not logged in
     */
    public synchronized Unit getUnit() {
        return this.unit;
    }

    /**
     * Updates the status of the given task
     *
     * @param taskID The id of the task, must be positive
     * @param newStatus The new status of the task, not empty
     */
    public synchronized void updateTaskStatus(int taskID, String newStatus) {
        if (!(taskID < 0 || newStatus.isEmpty() || getStatus() != 1)) {
            try {
                sendData((Object) "FOUS3");
                Object[] params = new Object[2];
                params[0] = taskID;
                params[1] = newStatus;
                sendData(params);
                System.out.println((String)readData());
            } catch (IOException ex) {
                System.out.println("Update status failed!");
                setStatus((byte)2);
            } catch (ClassNotFoundException ex) {
                System.out.println("Update status failed!");
                setStatus((byte)2);
            }
        }
    }
    
    /**
     * Accepts or denies the task for the given user
     * @param taskID The task id of the task
     * @param accepted Boolean if accepted, false is denied
     * @param reason The reason why it is denied
     */
    public synchronized void acceptDenyTask(int taskID, boolean accepted, String reason) {
        Object[] params = new Object[4];
        params[0] = this.unit.getUnitID();
        params[1] = taskID;
        if(accepted) {
            params[2] = 1;
        } else {
            params[2] = 0;
        }
        params[3] = reason;
        try {
            //Transmit data
            sendData("FOUS5");
            sendData(params);
            System.out.println((String)readData());
        } catch (IOException ex) {
            System.out.println("Transmission failed!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Unexpected result!");
        }
        
    }
    
    /**
     * Ends the connection to the server
     */
    public synchronized void quitConnection() {
        this.keepRunning = false;
    }
}
