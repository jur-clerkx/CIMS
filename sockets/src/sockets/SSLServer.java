/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author Jense
 */
public class SSLServer {

    public SSLServer(int port) throws Exception {
        try {
            String certificateName = "abc";
            String path = certificateName + ".jks";
            char[] passphrase = "dreamteam".toCharArray();
            SSLServerSocketFactory sslFactory = null;

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(path), passphrase);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, tmf.getTrustManagers(), null);
            sslFactory = ctx.getServerSocketFactory();
            SSLServerSocket s = (SSLServerSocket) sslFactory.createServerSocket(port);

            // Connect to clients.
            Thread connectClients = new Thread() {
                @Override
                public void run() {
                    System.out.println("Searching for clients..");
                    while (true) {
                        try {
                            SSLSocket client = (SSLSocket) s.accept();
                            System.out.println("New Client Connected: " + client.getInetAddress());
                            
                            DataInputStream is = new DataInputStream(client.getInputStream());
                            String input = is.readUTF();
                            System.out.println(input);
                        } catch (IOException e) {
                            System.out.println("IOException occurred: " + e.getMessage());
                        }
                    }
                }
            };
            connectClients.setDaemon(false);
            connectClients.start();

        } catch (IOException e) {
            System.out.print(e);
        }
    }

    public static void main(String args[]) throws Exception {
        SSLServer ssls = new SSLServer(1234);
    }
}
