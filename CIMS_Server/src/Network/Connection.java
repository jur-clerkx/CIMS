package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private boolean reading;

    /**
     * creates an instance of this class, creates the thread for getting
     * messages from the clients. it also sets the room or lobby id.
     *
     * @param socket Connection socket
     * @throws IOException
     */
    Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.user = new User();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        reading = true;

        Thread read;
        read = new Thread() {
            @Override
            public void run() {
                while (reading) {
                    try {
                        Object obj = in.readObject();
                        if (user.authorized()) {
                            System.out.println("Access already granted, Access approved");
                        } else {
                            if (obj instanceof String[]) {
                                String[] credentials = (String[]) obj;
                                if (credentials.length == 2) {
                                    Authorization authorize = new User();
                                    if (authorize.login(credentials[0], credentials[1])) {                                        
                                        user = (User) authorize;
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
                Logger.getLogger(Connection.class.getName()).log(Level.INFO, "Connection closed: {0}", socket.getLocalAddress());
                closeconn();
            }
        };

        read.setDaemon(false); // terminate when main ends
        read.start();
    }

    public void closeconn() {
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
        }
    }

    public void kill() {
        reading = false;
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Writes an object to to the server
     *
     * @param obj object to be send to ChatServer
     */
    public void write(Object obj) {
        try {
            out.writeObject(obj);
        } catch (IOException ex) {
        }
    }
}