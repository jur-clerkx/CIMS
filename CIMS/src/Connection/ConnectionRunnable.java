/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import Field_Operations.Progress;
import Field_Operations.Roadmap;
import Field_Operations.Task;
import Field_Operations.Unit;
import Network.User;
import cims.OperatorMainController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;
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
    private String serverAddress;

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private Cipher cipherIn;
    private Cipher cipherOut;

    private boolean keepRunning;

    //0 = in process, 1 = logged in, 2 = logged out/acces denied
    private byte authorized;
    public int selectedTaskID;
    public int selectedUnitID;

    public ConnectionRunnable(String username, String password) {
        this.username = username;
        this.password = password;
        this.authorized = 0;
        this.keepRunning = true;
        serverAddress = "145.93.84.138";
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
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println(ex.getMessage() + 1);
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
    public boolean CreateUnit(String Name, String Location, int size, String selectedSpecials, int PoliceCars, int FireTruck, int Ambulances, int Policemen, int FireFighters, int AmbulancePeople) throws IOException {

        Object[] myUnit = new Object[9];
        myUnit[0] = Name;
        myUnit[1] = Location;
        myUnit[2] = selectedSpecials;
        myUnit[3] = PoliceCars;
        myUnit[4] = FireTruck;
        myUnit[5] = Ambulances;
        myUnit[6] = Policemen;
        myUnit[7] = FireFighters;
        myUnit[8] = AmbulancePeople;

        try {
            String outputMessage = "FOOP2";

            sendData(outputMessage);
            sendData(myUnit);
            String feedback = (String) readData();
            return feedback.equals("Unit succesfully created");
        } catch (IOException ex2) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    /**
     * Fetches all inactive units from the database
     *
     * @return List of inactive units
     * @throws IOException
     */
    public ArrayList<Unit> getInactiveUnits() throws IOException {
        try {
            String outputMessage = "FOOP4";
            sendData(outputMessage);
            sendData(0);
            Object o = readData();
            if (o instanceof ArrayList) {
                return (ArrayList<Unit>) o;
            }

        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return new ArrayList<Unit>();
    }

    /**
     * Fetches all active units from the database
     *
     * @return List of active units
     * @throws IOException
     */
    public ArrayList<Unit> getActiveUnits() throws IOException {
        try {
            String outputMessage = "FOOP4";
            sendData(outputMessage);
            sendData(1);
            Object o = readData();
            if (o instanceof ArrayList) {
                return (ArrayList<Unit>) o;
            }

        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return new ArrayList<Unit>();

    }

    /**
     * Fetches all inactive tasks from the database
     *
     * @return List of inactive tasks
     * @throws IOException
     */
    public ArrayList<Task> getInactiveTasks() throws IOException {
        try {
            String outputMessage = "FOOP5";
            sendData(outputMessage);
            sendData(0);
            Object o = readData();
            if (o instanceof ArrayList) {
                return (ArrayList<Task>) o;
            }
        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return new ArrayList<>();
    }

    /**
     * Fetches all active tasks from the database
     *
     * @return List of active tasks
     * @throws IOException
     */
    public ArrayList<Task> getActiveTasks() throws IOException {
        try {
            String outputMessage = "FOOP5";
            sendData(outputMessage);
            sendData(1);
            Object o = readData();
            if (o instanceof ArrayList) {
                return (ArrayList<Task>) o;
            }
        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return new ArrayList<>();
    }

    /**
     * Removes an active task from the database
     *
     * @param taskID
     * @throws IOException
     */
    public void removeActiveTask(int taskID) throws IOException {
        try {
            String outputMessage = "FOOP7";
            sendData(outputMessage);
            sendData(taskID);
            readData();
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Removes an inactive task from the database
     *
     * @param taskID
     * @throws IOException
     */
    public void removeInactiveTask(int taskID) throws IOException {
        try {
            String outputMessage = "FOOP7";
            sendData(outputMessage);
            sendData(taskID);
            readData();
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionRunnable.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean createTask(String name, int urgency, String description,String Location) throws IOException {
        Object[] myTask = new Object[4];

        
        myTask[0] = description;
        myTask[1] = name;
        myTask[3] = Location;
        myTask[2] = urgency;

        try {
            String outputMessage = "FOOP6";

            sendData(outputMessage);
            sendData(myTask);
            readData();
            return true;

        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex2);
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
    public boolean editUnitInfo(String Name, String Location, int size, String selectedSpecials, int PoliceCars, int FireTruck, int Ambulances, int Policemen, int FireFighters, int AmbulancePeople) throws IOException {

        Object[] myUnit = new Object[9];
        myUnit[0] = Name;
        myUnit[1] = Location;
        myUnit[2] = selectedSpecials;
        myUnit[3] = PoliceCars;
        myUnit[4] = FireTruck;
        myUnit[5] = Ambulances;
        myUnit[6] = Policemen;
        myUnit[7] = FireFighters;
        myUnit[8] = AmbulancePeople;

        try {
            String outputMessage = "FOOP2";
            String feedback;
            sendData(outputMessage);
            sendData(selectedUnitID);
            sendData(myUnit);
            feedback = (String) readData();
            return feedback.equals("Unit succesfully created");

        } catch (IOException ex2) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
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
    public Unit getUnitInfo(int unitID) throws IOException {
        try {
            String outputMessage = "FOUS2";

            sendData(outputMessage);
            sendData(unitID);
            return (Unit) readData();

        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex2);
            KillConnection();
        }
        return null;
    }

    /**
     * Fetches a tasks information from the database
     *
     * @param taskID
     * @return Task object that was requested
     * @throws IOException
     */
    public Task getTaskInfo(int taskID) throws IOException {
        try {
            String outputMessage = "FOUS1";

            sendData(outputMessage);
            sendData(taskID);
            
           Object o = readData();
           if(o instanceof Task)
           {
               return (Task)o;
           }
        } catch (IOException | ClassNotFoundException ex2) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex2);
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
    public void cancelTask(int taskID) throws IOException {
        try {
            String outputMessage = "FOUS3";
            Object[] o = new Object[2];
            o[0] = taskID;
            o[1] = "Cancelled";
            sendData(outputMessage);
            sendData(o);
            readData();
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Assigns a task to units
     *
     * @param taskID
     * @param objects
     * @throws IOException
     */
    public void assignTask(Object[] objects) throws IOException {
        try {
            String outputMessage = "FOOP8";

            sendData(outputMessage);
            sendData(objects);
            readData();
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionRunnable.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean editTask(Object[] object) throws IOException {
        

        try {
            String outputMessage = "FOOP9";

            sendData(outputMessage);
            sendData(object);
            readData();
            return true;

        } catch (IOException |ClassNotFoundException  ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
            return false;
        }
    }

    /**
     *
     * @param username
     * @param Description
     * @param password
     * @return
     * @throws java.io.IOException
     */
    public boolean createRoadmap(String username, String Description) throws IOException, ClassNotFoundException {

        Object[] roadmap = new Object[2];
        roadmap[0] = username;
        roadmap[1] = Description;
        try {
            String outputMessage = "FOUS10";

            sendData(outputMessage);
            sendData(roadmap);
            readData();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
            return false;
        }
    }

    public ArrayList<Roadmap> getRoadmaps() throws IOException {
        ArrayList<Roadmap> roadmaps = new ArrayList();
        try {
            String outputMessage = "FOUS9";
            sendData(outputMessage);
            try {
                Object o = readData();
                if (o instanceof ArrayList){
                ArrayList<Roadmap> myRoads = (ArrayList<Roadmap>) o;
                roadmaps.addAll(myRoads);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roadmaps;
    }

    public boolean assignRoadmaps(int taskID, int roadmapID) throws IOException {
        Object[] message = new Object[2];

        try {
            String outputMessage = "FOOP10";
            sendData(outputMessage);
            sendData(message);
            readData();
            return true;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean DisbandUnit(int ID) throws IOException, ClassNotFoundException {
        try {
            sendData("FOOP3");
            
            sendData(ID);
            String message = (String) readData();
            if (message != null) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(OperatorMainController.class.getName()).log(Level.SEVERE, null, ex);
            KillConnection();
            return false;
        }
    }

    public void RegisterObserver(Observer aThis) throws IOException {

        try {
            sendData("FOOP11");
        } catch (IOException ex) {
            Logger.getLogger(ConnectionRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        {
            try {
                Object myObject = aThis;
                readData();
                sendData(myObject);
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ConnectionRunnable.class.getName()).log(Level.SEVERE, null, ex);
                KillConnection();
            }
        }
    }
    
    public ArrayList<Progress> getProgress(int TaskId) throws IOException{
        try
        {
            sendData("FOUS11");
            sendData(TaskId);
            Object o = readData();
            if(o instanceof ArrayList)
            {
                return (ArrayList<Progress>)o;
            }
            else return new ArrayList<>();
        }
        catch(Exception ex)
        {
            System.out.println("Progress Error");
            return new ArrayList<>();
        }
    }

}
