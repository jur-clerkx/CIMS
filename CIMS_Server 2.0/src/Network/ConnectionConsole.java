/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.util.Scanner;
import javax.print.DocFlavor;

/**
 *
 * @author Jense
 */
public class ConnectionConsole {

    public static void main(String[] args) {
        String serverIp = "";
        String port = "";
        String typeOfAccount = "";
        String username = "";
        String password = "";

        System.out.println("To autorize give Server-IP");
        while (true) {
            Scanner scanner = new Scanner(System.in);
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
                System.out.println("test ");
            }
        }

    }
}
