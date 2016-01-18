/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Application.CIMS_SA;
import Application.SendInformationController;
import Field_Operations.Unit;
import Network.PublicUser;
import Network.User;
import Situational_Awareness.Information;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author Jur
 */
public class ConnectionRunnable extends Observable implements Runnable {

    private String username;
    private String password;

    public User user;
    private Unit unit;
    private final String serverAddress;

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    private Cipher cipherIn;
    private Cipher cipherOut;

    private boolean keepRunning;

    //0 = in process, 1 = logged in, 2 = logged out/acces denied
    private byte authorized;

    public ConnectionRunnable(String username, String password) {
        this.username = username;
        this.password = password;
        this.authorized = 0;
        this.keepRunning = true;
        //serverAddress = "145.93.85.35";
        serverAddress = "localhost";
    }

    @Override
    public void run() {
        try {
            //Try to connect to server
            socket = new Socket(serverAddress, 1234);
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            
            //Generate AES key
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            SecretKey aesKey = kgen.generateKey();
            //Send key to server
            //SealedObject aesKeySealed = new SealedObject(aesKey.getEncoded(), RSAcipher);
            output.writeObject(aesKey);
            output.flush();
            
            // Initialize ciphers
            IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
            cipherOut = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherOut.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
            cipherIn = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherIn.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

            //Try to log in
            String login = username + "/" + password;
            sendData(login.split("/"));

            //Check if login succeeded
            user = (User) readData();

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
            Logger.getLogger(SendInformationController.class.getName()).log(Level.SEVERE, null, ex);
            setStatus((byte) 2);
            //Close connection
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
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
        try {
            SealedObject so = new SealedObject((Serializable) data, cipherOut);
            output.writeObject(so);
            output.flush();
        } catch (Exception ex) {
            System.out.println("tjup");
        }
    }

    private synchronized Object readData() throws IOException, ClassNotFoundException {
        SealedObject so = (SealedObject) input.readObject();
        try {
            return so.getObject(cipherIn);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ConnectionRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ConnectionRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets the status of the connection (0 = connectinputg, 1 = connected, 2 =
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
     *
     * @return Can be null if not logged in
     */
    public synchronized User getUser() {
        return this.user;
    }

    /**
     * Ends the connection to the server
     */
    public synchronized void quitConnection() {
        this.keepRunning = false;
    }

    private void KillConnection() throws IOException {
        input.close();
        output.close();
        this.authorized = 2;
    }

    /**
     * Get specified information from the database
     *
     * @param infID
     * @return Specified information
     * @throws IOException
     */
    public synchronized Information getInformation(int infID) throws IOException {
        if (authorized == 1) {
            try {
                String outputMessage = "SAPU5";
                output.writeObject(outputMessage);
                output.writeObject(infID);
                return (Information) input.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(CIMS_SA.class.getName()).log(Level.SEVERE, null, ex);
                KillConnection();
            }
        }
        return null;
    }

    /**
     * Get all information from the database
     *
     * @return Get all information
     * @throws IOException
     */
    public synchronized ArrayList<Information> getAllInformation() throws IOException {
        if (authorized == 1) {
            try {
                ArrayList<Information> info = new ArrayList();
                sendData("SAPU7");
                Object o = readData();
                if (o instanceof String) {
                    info = null;
                } else {
                    info = (ArrayList<Information>) o;
                }
                return info;
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(CIMS_SA.class.getName()).log(Level.SEVERE, null, ex);
                KillConnection();
            }
        }
        return null;
    }

    /**
     * Get all users from the database
     *
     * @return Get all users
     * @throws IOException
     */
    public synchronized ArrayList<PublicUser> getUsers() throws IOException {
        if (authorized == 1) {
            try {
                sendData("SAPU2");
                return (ArrayList<PublicUser>) readData();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(CIMS_SA.class.getName()).log(Level.SEVERE, null, ex);
                KillConnection();
            }
        }
        return null;
    }

    /**
     * Send specified information to specified user
     *
     * @param user
     * @param info
     * @return True if send
     * @throws IOException
     */
    public synchronized boolean sendInfo(PublicUser user, Information info) throws IOException {
        if (authorized == 1) {
            try {
                String outputMessage = "SAPU8";
                output.writeObject(outputMessage);
                output.writeObject((int)user.getUser_ID());
                output.writeObject(info.getID());
                return true;
            } catch (IOException ex) {
                Logger.getLogger(CIMS_SA.class.getName()).log(Level.SEVERE, null, ex);
                KillConnection();
            }
        }
        return false;
    }

    public synchronized boolean createInformation(String Name, String description, String location, int casualties, int toxic, int danger, int impact, String URL, boolean Private) {
        Object result = null;
        if (authorized == 1) {
            try {
                String outputMessage = "SAPU3";
                Object[] thisOutputMessage = new Object[9];
                thisOutputMessage[0] = null;
                thisOutputMessage[1] = Name;
                thisOutputMessage[2] = description;
                thisOutputMessage[3] = location;
                thisOutputMessage[4] = casualties;
                thisOutputMessage[5] = toxic;
                thisOutputMessage[6] = danger;
                thisOutputMessage[7] = impact;
                thisOutputMessage[8] = URL;
                
                output.writeObject(outputMessage);
                output.writeObject(thisOutputMessage);
                result = input.readObject();
                System.out.println(result.toString());

            } catch (IOException | ClassNotFoundException ex1) {
                Logger.getLogger(CIMS_SA.class.getName()).log(Level.SEVERE, null, ex1);

                System.out.println("Error creating new Information");
            }
        }
        if (result != null) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean EditInformation(String name, String description, String location, int casualties, int toxic, int danger, int impact, String URL, int id, boolean Private) {
        Object result = null;
        if (authorized == 1) {

            try {
                output.writeObject("SAPU4");
                output.writeObject(id);

                String outputMessage = "SAPU3";
                Object[] thisOutputMessage = new Object[9];
                thisOutputMessage[0] = name;
                thisOutputMessage[1] = description;
                thisOutputMessage[2] = location;
                thisOutputMessage[3] = casualties;
                thisOutputMessage[4] = toxic;
                thisOutputMessage[5] = danger;
                thisOutputMessage[6] = impact;
                thisOutputMessage[7] = URL;
                thisOutputMessage[8] = Private;

                output.writeObject(outputMessage);
                output.writeObject(thisOutputMessage);
                result = input.readObject();

            } catch (IOException | ClassNotFoundException ex1) {
                System.out.println("Error creating new Information");
            }
        }
        if (result != null) {
            return true;
        } else {
            return false;
        }

    }

    public synchronized ArrayList<Information> getPublicInformation(int Userid) throws IOException {
        ArrayList<Information> returnInfo = new ArrayList();
        if (authorized == 1) {
            try {
                String outputMessage = "SAPU10";
                sendData(outputMessage);
                Object o = readData();
                if (o instanceof ArrayList) {
                    returnInfo = (ArrayList<Information>) o;
                }
                System.out.println(o);

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(CIMS_SA.class.getName()).log(Level.SEVERE, null, ex);
                KillConnection();
            }
        }
        return returnInfo;

    }
}
