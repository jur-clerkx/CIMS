/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

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

    public static void main(String[] args) {
        String serverIp = "";
        String port = "";
        String typeOfAccount = "";
        String username = "";
        String password = "";
        user = new User();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (!user.authorized()) {
                if (serverIp.equals("")) {
                    System.out.println("To autorize give Server-IP");
                    serverIp = scanner.nextLine();
                    System.out.println("Give Port ");
                } else if (port.equals("")) {
                    port = scanner.nextLine();
                    System.out.println("Give accounttype ");
                } else if (typeOfAccount.equals("")) {
                    typeOfAccount = scanner.nextLine();
                    System.out.println("Give Username ");
                } else if (username.equals("")) {
                    username = scanner.nextLine();
                    System.out.println("Give Password ");
                } else if (password.equals("")) {
                    password = scanner.nextLine();
                    if (setupServer(Integer.parseInt(port), serverIp)) {
                        authorize(typeOfAccount, username, password);
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

    private static boolean authorize(String accounttype, String username, String password) {
        String[] credentials = new String[3];
        credentials[0] = accounttype;
        credentials[1] = username;
        credentials[2] = password;
        serverConnection.write(credentials);

        try {
            user = (User) serverConnection.read();
            System.out.println("User: " + user.toString());
            System.out.println("Autorized:" + user.authorized());
            return user.authorized();
        } catch (Exception ex) {
        }
        return false;
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
            case "FOUS1":
                serverConnection.write(1);
                o = serverConnection.read();
                if (o instanceof Task) {
                    Task t = (Task) o;
                    System.out.println("Task: " + t.toString());
                }
                break;
            case "FOUS2":
                serverConnection.write(1);
                o = serverConnection.read();
                if (o instanceof Unit) {
                    Unit u = (Unit) o;
                    System.out.println("Unit: " + u.toString());
                }
                break;
            case "FOUS3":
                break;
            case "FOUS4":
                o = serverConnection.read();
                if (o instanceof ArrayList) {
                    ArrayList<Task> tasks = (ArrayList<Task>) o;
                    System.out.println("Tasks: " + tasks.size());
                }
                break;
        }
    }

}
