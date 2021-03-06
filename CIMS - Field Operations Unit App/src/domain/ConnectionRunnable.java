/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import Field_Operations.Domain.Roadmap;
import Field_Operations.Domain.Task;
import Field_Operations.Domain.Unit;
import Global.Domain.PrivateUser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
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
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Jur
 */
public class ConnectionRunnable extends Observable implements Runnable {

    private String username;
    private String password;

    private PrivateUser user;
    private Unit unit;

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Cipher cipherIn;
    private Cipher cipherOut;
    private Cipher RSAcipher;

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
            //Get public key of server
            FileInputStream publicin = new FileInputStream(new File("publickey.txt"));
            int i, counter = 0;
            byte[] publickeybytes = new byte[publicin.available()];
            while ((i = publicin.read()) != -1) {
                publickeybytes[counter] = (byte) i;
                counter++;
            }
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publickey = kf.generatePublic(new X509EncodedKeySpec(publickeybytes));
            RSAcipher = Cipher.getInstance("RSA");
            RSAcipher.init(Cipher.ENCRYPT_MODE, publickey);

            //Try to connect to server
            socket = new Socket(cims.field.operations.unit.app.CIMSFieldOperationsUnitApp.props.getServerURL(), 1250);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            //Generate AES key
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            SecretKey aesKey = kgen.generateKey();
            //Send key to server
            //SealedObject aesKeySealed = new SealedObject(aesKey.getEncoded(), RSAcipher);
            out.writeObject(aesKey);
            out.flush();

            // Initialize ciphers
            IvParameterSpec ivParameterSpec = new IvParameterSpec(aesKey.getEncoded());
            cipherOut = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherOut.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
            cipherIn = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherIn.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);
            
            //Try to log in
            String login = username + "/" + password;
            sendData(login.split("/"));
            //Delete password
            password = "apahvohiewaldjfpaoivwe";

            //Check if login succeeded
            user = (PrivateUser) readData();

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
        try {
            SealedObject so = new SealedObject((Serializable) data, cipherOut);
            out.writeObject(so);
            out.flush();
        } catch (Exception ex) {
            System.out.println("tjup");
        }
    }

    private synchronized Object readData() throws IOException, ClassNotFoundException {
        SealedObject so = (SealedObject) in.readObject();
        try {
            return so.getObject(cipherIn);
        } catch (Exception ex) {
            System.out.println("Reading data failed!");
            System.out.println(ex.getMessage());
        }
        return null;
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
     *
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
    public synchronized PrivateUser getUser() {
        return this.user;
    }

    /**
     * Gets the unit of the user that is currently logged in
     *
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
                System.out.println((String) readData());
            } catch (IOException ex) {
                System.out.println("Update status failed!");
                setStatus((byte) 2);
            } catch (ClassNotFoundException ex) {
                System.out.println("Update status failed!");
                setStatus((byte) 2);
            }
        }
    }

    /**
     * Sends feedback of the current task to the operator
     *
     * @param taskID The id of the task, must be positive
     * @param feedback The feedback about the task
     */
    public synchronized void sendFeedback(int taskID, String feedback) {
        if (!(taskID < 0 || feedback.isEmpty() || getStatus() != 1)) {
            try {
                sendData((Object) "FOUS7");
                Object[] params = new Object[2];
                params[0] = taskID;
                params[1] = feedback;
                sendData(params);
                System.out.println((String) readData());
            } catch (IOException ex) {
                System.out.println("Send feedback failed!");
                setStatus((byte) 2);
            } catch (ClassNotFoundException ex) {
                System.out.println("Send feedback failed!");
                setStatus((byte) 2);
            }
        }
    }

    /**
     * Accepts or denies the task for the given user
     *
     * @param taskID The task id of the task
     * @param accepted Boolean if accepted, false is denied
     * @param reason The reason why it is denied
     */
    public synchronized void acceptDenyTask(int taskID, boolean accepted, String reason) {
        Object[] params = new Object[4];
        params[0] = this.unit.getId();
        params[1] = taskID;
        if (accepted) {
            params[2] = 1;
        } else {
            params[2] = 0;
        }
        params[3] = reason;
        try {
            //Transmit data
            sendData("FOUS5");
            sendData(params);
            System.out.println((String) readData());
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

    /**
     * Gets the roadmaps that are assigned to the current task
     *
     * @return A list of roadmaps, can be null if connection is lost
     */
    public synchronized ArrayList<Roadmap> getRoadmapsByTask() {
        if (getStatus() == 1) {
            try {
                sendData("FOUS8");
                sendData(cims.field.operations.unit.app.CIMSFieldOperationsUnitApp.currentTask.getId());
                return (ArrayList<Roadmap>) readData();
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
     * Gets all the roadmaps in the system
     *
     * @return A list of roadmaps, can be null if connection is lost
     */
    public synchronized ArrayList<Roadmap> getAllRoadmaps() {
        if (getStatus() == 1) {
            try {
                sendData("FOUS9");
                return (ArrayList<Roadmap>) readData();
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
}
