/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Jense
 */
public class SSLClient {

    public static void main(String args[]) {
        try {
            //Open 1 client socket server with the public and to address identified
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) factory.createSocket("127.0.0.1", 1234);

            //Create streams for sending data to the server
            DataOutputStream outputstream = new DataOutputStream(sslsocket.getOutputStream());
            DataInputStream inputstream = new DataInputStream(sslsocket.getInputStream());

            //Send data to servers
            String str = "helloworld\n";
            outputstream.writeBytes(str);

            //processed the received data of the server 
            String responseStr;
            if ((responseStr = inputstream.readUTF()) != null) {
                System.out.println(responseStr);
            }

            outputstream.close();
            inputstream.close();
            sslsocket.close();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
