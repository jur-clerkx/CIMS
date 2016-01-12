package Network;

import Field_Operations.MGR.ProgressMGR;
import Field_Operations.MGR.RoadmapMGR;
import Field_Operations.MGR.TaskMGR;
import Field_Operations.MGR.UnitMGR;
import Global.Domain.User;
import Global.DAO.PrivateUserDAOImpl;
import Global.DAO.PublicUserDAOImpl;
import Global.Domain.PrivateUser;
import Situational_Awareness.MGR.InformationMGR;
import Global.MGR.PublicUserMGR;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jense Schouten
 */
public class Connection {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private User user;
    private Cipher cipherIn;
    private Cipher cipherOut;
    private Cipher RSAcipher;

    private EntityManager em;

    boolean developermode = true;
    private boolean reading = false;

    /**
     * creates an instance of this class, creates the thread for executing
     * functions and authorizing users.
     *
     * @param socket Connection socket
     * @throws IOException
     */
    Connection(Socket socket, EntityManagerFactory emf) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, ClassNotFoundException {
        em = emf.createEntityManager();

        this.socket = socket;
        this.user = new User();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        //Get AES key
        //SealedObject aesKeySealed = (SealedObject) read();
        //byte[] easKeyEncoded = (byte[]) aesKeySealed.getObject(RSAcipher);
        //SecretKey AESKey = new SecretKeySpec(easKeyEncoded, 0, easKeyEncoded.length, "AES");
        //System.out.println(AESKey.getEncoded());
        SecretKey AESKey = (SecretKey) in.readObject();

        //Get the publickey from the client and initializa output cipher
        IvParameterSpec ivParameterSpec = new IvParameterSpec(AESKey.getEncoded());
        cipherOut = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherOut.init(Cipher.ENCRYPT_MODE, AESKey, ivParameterSpec);
        cipherIn = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherIn.init(Cipher.DECRYPT_MODE, AESKey, ivParameterSpec);
        
        reading = true;

        Thread read;
        read = new Thread() {
            @Override
            public void run() {
                while (reading) {
                    try {
                        Object obj = read();
                        if (user.authorized()) {
                            if (obj instanceof String) {
                                String s = obj.toString();
                                RequestData(s);
                            } else {
                                write("Data rejected, not the correct format");
                            }
                        } else {
                            if (obj instanceof String[]) {
                                String[] credentials = (String[]) obj;
                                if (credentials.length == 2) {
                                    if (login(credentials[0], credentials[1])) {
                                        write(user);
                                        System.out.println("connection made, Access authorized");
                                    } else {
                                        write(user);
                                        System.out.println("Wrong credentials, Acces denied");
                                        reading = false;
                                    }
                                } else {
                                    System.out.println("No credentials format length approved, Acces denied");
                                    reading = false;
                                }
                            } else {
                                System.out.println("No credentials format entered, Acces denied");
                                reading = false;
                            }
                        }
                    } catch (IOException | ClassNotFoundException | NumberFormatException ex) {
                    }
                }
                Logger.getLogger(Connection.class.getName()).log(Level.INFO, "Connection closed: {0}",
                        socket.getLocalAddress());
                closeconn();
            }
        };

        read.setDaemon(true); // terminate when main ends
        read.start();
    }

    /**
     * To delegate to functions
     *
     * @param s Function name
     * @throws IOException throws a IOException
     * @throws ClassNotFoundException if cannot cast to class
     */
    private void RequestData(String s) throws IOException, ClassNotFoundException {
        String command = s.substring(0, 4);
        switch (command.toUpperCase()) {
            case "FOUS":
                fieldOperations(s);
                break;
            case "FOOP":
                fieldOperations(s);
                break;
            case "SAPU":
                situationalAwareness(s);
                break;
        }

    }

    /**
     * field Operations functions
     *
     * @param s Function name
     * @throws IOException throws a IOException
     * @throws ClassNotFoundException if cannot cast to class
     */
    private void fieldOperations(String s) throws IOException, ClassNotFoundException {
        TaskMGR taskMGR = new TaskMGR(em);
        UnitMGR unitMGR = new UnitMGR(em);
        ProgressMGR progressMGR = new ProgressMGR(em);
        RoadmapMGR roadmapMGR = new RoadmapMGR(em);
        Object o;
        switch (s.toUpperCase()) {
            case "FOUS1": //Get task by taskId input int
                o = read();
                if (o instanceof Integer) {
                    write(taskMGR.findTask((int) o));
                } else {
                    write("Error, Param is not a integer");
                }
                break;
            case "FOUS2": //Get unit by UnitId input int
                o = read();
                if (o instanceof Integer) {
                    write(unitMGR.findUnit((int) o));
                } else {
                    write("Error, Param is not a integer");
                }
                break;
            case "FOUS3": //Edit Task Satus input object[] of int, string
                o = read();
                if (o instanceof Object[]) {
                    if (taskMGR.editTaskStatus((Object[]) o)) {
                        write("EditTaskStatus: carried out successfully");
                        break;
                    }
                }
                write("Error, can not edit task status");
                break;
            case "FOUS4"://Get tasks of this user            
                write(new ArrayList<>(taskMGR.findAllTasksByUserId(getUserId())));
                break;
            case "FOUS5"://Accep or deny task input object[] of int, boolean,(optimal) string
                o = read();
                if (o instanceof Object[]) {
                    if (taskMGR.accepOrDenyTasks((Object[]) o)) {
                        write("AccepOrDenyTasks: carried out successfully");
                        break;
                    }
                }
                write("Error, Could not accept or deny task");
                break;
            case "FOUS6": //Gets all units of this user                
                write(new ArrayList<>(unitMGR.findAllUnitsByUserId(getUserId())));
                break;
            case "FOUS7": //Creates progress for task by user input object[] of int and string
                o = read();
                if (o instanceof Object[]) {
                    if (progressMGR.createProgress((Object[]) o, (PrivateUser) this.user)) {
                        write("Progress succesfully created");
                        break;
                    }
                }
                write("Could not create progress");
                break;
            case "FOOP1"://Edit unit input unit
                o = read();
                if (unitMGR.editUnit(o)) {
                    write("Unit succesfully edit");
                    break;
                }
                write("Could not edit unit");
                break;
            case "FOOP2"://Create unit input object[] with values
                o = read();
                if (unitMGR.createUnit(o)) {
                    write("Unit succesfully created");
                    break;
                }
                write("Could not create unit");
                break;
            case "FOOP3"://Disband unit
                o = read();
                if (unitMGR.disbandUnit(o)) {
                    write("Unit disbanded succesfully");
                    break;
                }
                write("Error, could not disband unit");
                break;
            case "FOOP4"://Get all active units
                o = read();
                write(new ArrayList<>(unitMGR.getActiveInactiveUnits(o)));
                break;
            case "FOOP5"://Get all inactive units
                o = read();
                write(new ArrayList<>(taskMGR.getActiveInactiveTasks(o)));
                break;
            case "FOOP6"://Creates a task requires string array length 5
                o = read();
                if (taskMGR.createTask(o)) {
                    write("Task succesfully created");
                } else {
                    write("Error, values incorrect");
                }
                break;
            case "FOOP7": //Removes a task from database params int id
                o = read();
                if (taskMGR.removeTask(o)) {
                    write("Task succesfully removed");
                    break;
                }
                write("Error, param is not a integer");
                break;
            case "FOOP8"://Assigns multiple units to a task
                o = read();
                if (unitMGR.assignUnitToTask(o)) {
                    write("Task succesfully assigned");
                    break;
                }
                write("Could not assign to task");
                break;
            case "FOOP9"://Alters the a task requests a task
                o = read();
                if (taskMGR.alterTask(o)) {
                    write("Task location succesfully altered");
                    break;
                }
                write("Could not alter task");
                break;
            case "FOUS8":
                o = read();
                write(roadmapMGR.getRoadmapByTaskId(o));
                break;
            case "FOUS9":
                write(new ArrayList<>(roadmapMGR.getAllRoadmaps()));
                break;
            case "FOUS10":
                o = read();
                if (roadmapMGR.createRoadmap(o)) {
                    write("Roadmap succesfully created");
                    break;
                }
                write("Could not create roadmap");
                break;
            case "FOOP10":
                o = read();
                if (roadmapMGR.assignRoadmap(o)) {
                    write("Roadmap succesfully assigned");
                    break;
                }
                write("Could not assign roadmap");
                break;
        }
    }

    /**
     * situational Awareness functions
     *
     * @param s Function name
     * @throws IOException throws a IOException
     * @throws ClassNotFoundException if cannot cast to class
     */
    private void situationalAwareness(String s) throws IOException, ClassNotFoundException {
        InformationMGR informationMGR = new InformationMGR(em);
        PublicUserMGR PublicUserMGR = new PublicUserMGR(em);
        Object o;
        switch (s.toUpperCase()) {
            case "SAPU1":
                o = read();
                if (PublicUserMGR.createPublicUser(o)) {
                    write("User succesfully created");
                    break;
                }
                write("Could not create user");
                break;
            case "SAPU2":
                write(PublicUserMGR.GetAllPublicUsers());
                break;
            case "SAPU3":
                o = read();
                if (informationMGR.createInformation(this.user, o)) {
                    write("Information succesfully created");
                    break;
                }
                write("Could not create information");

                break;
            case "SAPU4":
                o = read();
                if (informationMGR.removeInformation(o)) {
                    write("Information succesfully removed");
                    break;
                }
                write("Could not remove information");

                break;
            case "SAPU5":
                o = read();
                write(informationMGR.getInformationById(o));
                break;
            case "SAPU6":
                o = read();
                write(informationMGR.getInformationByTaskId(o));
                break;
            case "SAPU7":
                write(informationMGR.GetAllInformation());
                break;
            case "SAPU8":
                o = read();
                if (informationMGR.sendinformation(o)) {
                    write("Information succesfully send");
                    break;
                }
                write("Could not send information");
                break;
            case "SAPU10":
                write(informationMGR.GetAllPublicInformation(getUserId()));
                break;
        }
    }

    /**
     * Logs a user in on db.
     *
     * @param username Username of a user
     * @param password Password of a user
     * @return if login is success or not
     */
    private boolean login(String username, String password) {

        if (username == null || password == null) {
            return false;
        }
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        Global.DAO.PublicUserDAO pudao = new PublicUserDAOImpl(em);
        this.user = pudao.login(username, password);
        if (this.user != null) {
            this.user.setAuthorized(true);
            return true;
        } else {
            Global.DAO.PrivateUserDAO prudao = new PrivateUserDAOImpl(em);
            this.user = prudao.login(username, password);
            if (this.user != null) {
                this.user.setAuthorized(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Closes the connection
     */
    public void closeconn() {
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
        }
    }

    /**
     * Kills the connection
     */
    public void kill() {
        reading = false;
        try {
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Connection.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean isOpen() {
        return this.reading;
    }

    /**
     * Gets the userId of a logged on user
     *
     * @return a userId
     */
    public int getUserId() {
        return (int) this.user.getUserId();
    }

    /**
     * Writes an object to to the server
     *
     * @param obj object to be send to ChatServer
     */
    public void write(Object obj) {
        try {
            SealedObject so = new SealedObject((Serializable) obj, cipherOut);
            out.writeObject(so);
            out.flush();
        } catch (IOException ex) {
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Read an object that the client send
     *
     * @return The object
     */
    public Object read() {
        try {
            SealedObject so = (SealedObject) in.readObject();
            return so.getObject(cipherIn);
        } catch (Exception ex) {
        }
        reading = false;
        return null;
    }
}
