/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import Global.Domain.User;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        System.out.println("To autorize give Server-IP");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (password.equals("")) {
                if (serverIp.equals("")) {
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
                        if(authorize(typeOfAccount, username, password))
                            System.out.println("Give Function");
                        
                    }                    
                }
            }else{
                sendCommand(scanner.nextLine());
                System.out.println("Working..");
                System.out.println("");
                System.out.println("Give Function");
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionConsole.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(user.toString());
        if (user != null) {
            
            return user.authorized();
        }
        return false;
    }

    private static void sendCommand(String command) {
        try {
            if (!user.authorized()) {
                return;
            }
            serverConnection.write(command);
            functionControl(command);
            System.out.println(serverConnection.read());
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    private static void functionControl(String function) {
        switch (function) {
            case "FOUS1":
                serverConnection.write(1);
                break;
            case "FOUS2":
                serverConnection.write(1);
                break;
        }
    }

}
