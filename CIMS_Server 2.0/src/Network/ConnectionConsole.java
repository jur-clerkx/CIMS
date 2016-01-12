/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import Field_Operations.Domain.Roadmap;
import Field_Operations.Domain.Task;
import Field_Operations.Domain.Unit;
import Global.Domain.User;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jense
 */
public class ConnectionConsole {

    private static ConnectionToServer serverConnection;
    private static Socket socket;
    private static User user;

    public static void main(String[] args) throws ClassNotFoundException {
        String serverIp = "";
        String port = "";
        String username = "";
        String password = "";
        user = new User();
        Scanner scanner;
        while (true) {
            scanner = new Scanner(System.in);
            if (!user.authorized()) {
                if (serverIp.equals("")) {
                    System.out.println("To autorize give Server-IP");
                    serverIp = scanner.nextLine();
                    System.out.println("Give Port ");
                } else if (port.equals("")) {
                    port = scanner.nextLine();
                    System.out.println("Give Username ");
                } else if (username.equals("")) {
                    username = scanner.nextLine();
                    System.out.println("Give Password ");
                } else if (!password.equals("")) {
                    password = scanner.nextLine();
                    if (setupServer(Integer.parseInt(port), serverIp)) {
                        authorize(username, password);
                    }
                }
            } else {
                System.out.println("Give Function");
                sendCommand(scanner.nextLine());
                System.out.println("");
            }
        }
    }

    private static boolean setupServer(int port, String serverIp) {
        try {
            socket = new Socket(serverIp, port);
            serverConnection = new ConnectionToServer(socket);

            if (serverConnection != null) {
                return true;
            }
        } catch (IOException ex) {
            ex.getStackTrace();
        }
        return false;
    }

    private static boolean authorize(String username, String password) throws ClassNotFoundException {
        String[] credentials = new String[2];
        credentials[0] = username;
        credentials[1] = password;
        serverConnection.write(credentials);

        user = (User) serverConnection.read();
        System.out.println("User: " + user.toString());
        System.out.println("Autorized:" + user.authorized());
        return user.authorized();
    }

    private static void sendCommand(String command) {
        try {
            if (!user.authorized()) {
                return;
            }
            System.out.println("Working..");
            serverConnection.write(command);
            functionControl(command);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    private static void functionControl(String function) throws ClassNotFoundException {
        Object o;
        switch (function) {
            case "FOUS1": {
                serverConnection.write(1);                      //id
                o = serverConnection.read();
                if (o instanceof Task) {
                    Task t = (Task) o;
                    System.out.println("Task: " + t.toString());
                } else {
                    System.out.println("No task available");
                }
                break;
            }
            case "FOUS2": {
                serverConnection.write(1);                      //id
                o = serverConnection.read();
                if (o instanceof Unit) {
                    Unit u = (Unit) o;
                    System.out.println("Unit: " + u.toString());
                } else {
                    System.out.println("No unit available");
                }
                break;
            }
            case "FOUS3": {
                Object[] objects = new Object[2];
                objects[0] = 1;                                 //id
                objects[1] = "Cancelled";                       //status
                serverConnection.write(objects);
                o = serverConnection.read();
                System.out.println("Message: " + o);
                break;
            }
            case "FOUS4": {
                o = serverConnection.read();
                if (o instanceof ArrayList) {
                    ArrayList<Task> tasks = (ArrayList<Task>) o;
                    System.out.println("Tasks: " + tasks.size());
                } else {
                    System.out.println("No tasks available");
                }
                break;
            }
            case "FOUS5": {
                Object[] objects = new Object[3];
                objects[0] = 1;                                 //id
                objects[1] = false;                             //accepted
                objects[2] = "Onderbezet";                      //reason (optimal)
                serverConnection.write(objects);
                o = serverConnection.read();
                System.out.println("Message: " + o);
                break;
            }
            case "FOUS6": {
                o = serverConnection.read();
                if (o instanceof ArrayList) {
                    ArrayList<Unit> units = (ArrayList<Unit>) o;
                    System.out.println("Units: " + units.size());
                } else {
                    System.out.println("No units available");
                }
                break;
            }
            case "FOUS7": {
                Object[] objects = new Object[2];
                objects[0] = 1;                                 //taskId
                objects[1] = "Brand onder controle.";           //message
                serverConnection.write(objects);
                o = serverConnection.read();
                System.out.println("Message: " + o);
                break;
            }
            case "FOUS8": {
                serverConnection.write(1);                      //id
                o = serverConnection.read();
                if (o instanceof ArrayList) {
                    ArrayList<Roadmap> roadmaps = (ArrayList<Roadmap>) o;
                    System.out.println("Roadmaps: " + roadmaps.size());
                } else {
                    System.out.println("No roadmaps available");
                }
                break;
            }
            case "FOUS9": {
                o = serverConnection.read();
                if (o instanceof ArrayList) {
                    ArrayList<Roadmap> roadmaps = (ArrayList<Roadmap>) o;
                    System.out.println("Roadmaps: " + roadmaps.size());
                } else {
                    System.out.println("No roadmaps available");
                }
                break;
            }
            case "FOUS10": {
                Object[] objects = new Object[2];
                objects[0] = "Brand blussen";                   //name
                objects[1] = "1. draai de brandkraan open "
                        + "\r\n2. enz..";                       //description 
                serverConnection.write(objects);
                o = serverConnection.read();
                System.out.println("Message: " + o);
                break;
            }
            case "FOOP3": {
                serverConnection.write(1);                      //id
                o = serverConnection.read();
                System.out.println("Message: " + o);
                break;
            }
            case "FOOP5": {
                serverConnection.write(1);                      //id
                o = serverConnection.read();
                if (o instanceof ArrayList) {
                    ArrayList<Task> tasks = (ArrayList<Task>) o;
                    System.out.println("Tasks: " + tasks.size());
                } else {
                    System.out.println("No tasks available");
                }
                break;
            }
            case "FOOP6": {
                Object[] objects = new Object[5];
                objects[0] = "Marktkraam staat in de brand";    //name
                objects[1] = "High";                            //urgency
                objects[2] = "Active";                          //status
                objects[3] = "Markt Eindhoven";                 //location
                objects[4] = "Burnnnnn";                        //description
                serverConnection.write(objects);
                o = serverConnection.read();
                System.out.println("Message: " + o);
                break;
            }
            case "FOOP7": {
                serverConnection.write(1);                      //id
                o = serverConnection.read();
                System.out.println("Message: " + o);
                break;
            }
            case "FOOP8": {
                Object[] objects = new Object[3];
                objects[0] = 1;                                 //taskId
                objects[1] = 1;                                 //unitId
                objects[2] = 2;                                 //unitId etc.
                serverConnection.write(objects);
                o = serverConnection.read();
                System.out.println("Message: " + o);
                break;
            }
        }
    }

}
