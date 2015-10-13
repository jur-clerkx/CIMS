package Network;

import Field_Operations.Task;
import Field_Operations.Unit;
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
    private DatabaseMediator dbMediator;

    /**
     * creates an instance of this class, creates the thread for getting
     * messages from the clients. it also sets the room or lobby id.
     *
     * @param socket Connection socket
     * @throws IOException
     */
    Connection(Socket socket) throws IOException {
        this.dbMediator = new DatabaseMediator();
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
                Logger.getLogger(Connection.class.getName()).log(Level.INFO, "Connection closed: {0}", socket.getLocalAddress());
                closeconn();
            }
        };

        read.setDaemon(false); // terminate when main ends
        read.start();
    }

    private void RequestData(String s) throws IOException, ClassNotFoundException {
        Object o;
        Task t;
        Unit u;
        switch (s.toUpperCase()) {
            case "FOUS1":
                o = in.readObject();
                t = dbMediator.getTask(o);
                if (t != null) {
                    t = dbMediator.getTaskLists(t);
                }
                write(t);
                break;
            case "FOUS2":
                o = in.readObject();
                u = dbMediator.getUnit(o);
                if (u != null) {
                    u = dbMediator.getUnitLists(u);
                }
                write(u);
                break;
            case "FOUS3":
                o = in.readObject();
                if (dbMediator.updateTaskStatus(o)) {
                    write("FOUS3: carried out successfully");
                } else {
                    write("Could not execute FOUS3");
                }
                break;
            case "FOUS4":
                write(dbMediator.getTaskListByUser(this.user.getUser_ID()));
                break;
            case "FOOP1":
                o = in.readObject();
                write("not implemented yet");
                break;
            case "FOOP2":
                o = in.readObject();
                if (dbMediator.createUnit(o)) {
                    write("Unit succesfully created");
                } else {
                    write("Could not create unit");
                }
                break;
            case "FOOP3":
                o = in.readObject();
                if (dbMediator.disbandUnit(o)) {
                    write("Unit disbanded succesfully");
                } else {
                    write("Could not disband unit");
                }
                break;
            case "FOOP4":
                o = in.readObject();
                write(dbMediator.getActiveInactiveUnits(o));
                break;
            case "FOOP5":
                o = in.readObject();
                write(dbMediator.getActiveInactiveTasks(o));
                break;
            case "FOOP6":
                o = in.readObject();
                if (dbMediator.createTask(o)) {
                    write("Task succesfully created");
                } else {
                    write("Could not create task");
                }
                break;
            case "FOOP7":
                o = in.readObject();
                if (dbMediator.removeTask(o)) {
                    write("Task succesfully removed");
                } else {
                    write("Could not remove task");
                }
                break;
            case "FOOP8":
                o = in.readObject();
                if (dbMediator.assignTask(o)) {
                    write("Task succesfully created");
                } else {
                    write("Could not create task");
                }
                break;
            case "FOOP9":
                o = in.readObject();
                if (dbMediator.alterLocationTask(o)) {
                    write("Task succesfully altered");
                } else {
                    write("Could not alter task");
                }
                break;
        }

    }

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
        return false;
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

    public Boolean isOpen() {
        return this.reading;
    }

    public int getuser_ID() {
        return this.user.getUser_ID();
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
