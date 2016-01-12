package Network;

import Field_Operations.Task;
import Field_Operations.Unit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

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
    private PublicUser user;
    private boolean reading;
    private DatabaseMediator dbMediator;
    private Cipher cipherIn;
    private Cipher cipherOut;
    private Cipher RSAcipher;

    /**
     * creates an instance of this class, creates the thread for executing
     * functions and authorizing users.
     *
     * @param socket Connection socket
     * @throws IOException
     */
    Connection(Socket socket) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        this.dbMediator = new DatabaseMediator();
        this.socket = socket;
        this.user = new User();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        //Get private key of the server
        FileInputStream privatein = new FileInputStream(new File("privatekey.txt"));
        int i, counter = 0;
        byte[] privatekeybytes = new byte[privatein.available()];
        while ((i = privatein.read()) != -1) {
            privatekeybytes[counter] = (byte) i;
            counter++;
        }
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privatekeybytes));

        //Initialize input cipher
        RSAcipher = Cipher.getInstance("RSA");
        RSAcipher.init(Cipher.DECRYPT_MODE, privateKey);

        //Get AES key
        //SealedObject aesKeySealed = (SealedObject) in.readObject();
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
        Object o;
        Task t;
        Unit u;
        switch (s.toUpperCase()) {
            case "FOUS1":
                o = read();
                t = dbMediator.getTaskById(o);
                if (t != null) {
                    t = dbMediator.getTaskLists(t);
                }
                write(t);
                break;
            case "FOUS2":
                o = read();
                u = dbMediator.getUnitById(o);
                if (u != null) {
                    u = dbMediator.getUnitLists(u);
                }
                write(u);
                break;
            case "FOUS3":
                o = read();
                if (dbMediator.updateTaskStatus(o)) {
                    write("FOUS3: carried out successfully");
                } else {
                    write("Could not execute FOUS3");
                }
                break;
            case "FOUS4":
                write(dbMediator.getTaskListByUser(this.user.getUser_ID()));
                break;
            case "FOUS5":
                o = read();
                if (dbMediator.acceptOrDeniedTask(o)) {
                    write("FOUS5: carried out successfully");
                } else {
                    write("Could not execute FOUS5");
                }
                break;
            case "FOUS6":
                write(dbMediator.getUnitListByUserId(this.user.getUser_ID()));
                break;
            case "FOUS7":
                o = read();
                if (dbMediator.createProgress(this.getUserId(), o)) {
                    write("Progress succesfully created");
                } else {
                    write("Could not create progress");
                }
            case "FOOP2":
                o = read();
                if (dbMediator.createUnit(o)) {
                    write("Unit succesfully created");
                } else {
                    write("Could not create unit");
                }
                break;
            case "FOOP3":
                o = read();
                if (dbMediator.disbandUnit(o)) {
                    write("Unit disbanded succesfully");
                } else {
                    write("Could not disband unit");
                }
                break;
            case "FOOP4":
                o = read();
                write(dbMediator.getActiveInactiveUnits(o));
                break;
            case "FOOP5":
                o = read();
                write(dbMediator.getActiveInactiveTasks(o));
                break;
            case "FOOP6":
                o = read();
                if (dbMediator.createTask(o)) {
                    write("Task succesfully created");
                } else {
                    write("Could not create task");
                }
                break;
            case "FOOP7":
                o = read();
                if (dbMediator.removeTask(o)) {
                    write("Task succesfully removed");
                } else {
                    write("Could not remove task");
                }
                break;
            case "FOOP8":
                o = read();
                if (dbMediator.assignTask(o)) {
                    write("Task succesfully created");
                } else {
                    write("Could not create task");
                }
                break;
            case "FOOP9":
                o = read();
                if (dbMediator.alterLocationTask(o)) {
                    write("Task succesfully altered");
                } else {
                    write("Could not alter task");
                }
                break;
            case "FOUS8":
                o = read();
                write(dbMediator.getRoadmapByTaskId(o));
                break;
            case "FOUS9":
                write(dbMediator.getAllRoadmaps());
                break;
            case "FOUS10":
                o = read();
                if (dbMediator.createRoadmap(o)) {
                    write("Roadmap succesfully created");
                } else {
                    write("Could not create roadmap");
                }
                break;
            case "FOOP10":
                o = read();
                if (dbMediator.assignRoadmap(o)) {
                    write("Roadmap succesfully assigned");
                } else {
                    write("Could not assign roadmap");
                }
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
        Object o;
        switch (s.toUpperCase()) {
            case "SAPU1":
                o = read();
                if (dbMediator.createPublicUser(o)) {
                    write("User succesfully created");
                } else {
                    write("Could not create user");
                }
                break;
            case "SAPU2":
                write(dbMediator.GetAllPublicUsers());
                break;
            case "SAPU3":
                o = read();
                if (dbMediator.createInformation(this.getUserId(), o)) {
                    write("Information succesfully created");
                } else {
                    write("Could not create information");
                }
                break;
            case "SAPU4":
                o = read();
                if (dbMediator.removeInformation(o)) {
                    write("Information succesfully removed");
                } else {
                    write("Could not remove information");
                }
                break;
            case "SAPU5":
                o = read();
                write(dbMediator.getInformationById(o));
                break;
            case "SAPU6":
                o = read();
                write(dbMediator.getInformationByTaskId(o));
                break;
            case "SAPU7":
                write(dbMediator.GetAllInformation());
                break;
            case "SAPU8":
                o = read();
                if (dbMediator.sendinformation(o)) {
                    write("Information succesfully send");
                } else {
                    write("Could not send information");
                }
                break;
            case "SAPU10":
                write(dbMediator.GetAllPublicInformation(getUserId()));
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
        User us = dbMediator.checkLogin(username, password);
        if (us.authorized()) {
            this.user = us;
            return true;
        }
        PublicUser pu = dbMediator.checkPublicLogin(username, password);
        if (pu.authorized()) {
            this.user = pu;
            return true;
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
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
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
        return this.user.getUser_ID();
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
