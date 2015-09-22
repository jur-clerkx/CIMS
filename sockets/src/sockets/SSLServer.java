/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author Jense
 */
public class SSLServer {

    public static void main(String args[]) throws Exception {
        try {
            String host = "https://host name here";
            // int port = 9444;
            String certificateName = "abc";
            String path = certificateName + ".jks";
            char[] passphrase = "dreamteam".toCharArray();
            SSLServerSocketFactory sslFactory = null;

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream(path), passphrase);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);
            SSLContext ctx = SSLContext.getInstance("SSL");
            ctx.init(null, tmf.getTrustManagers(), null);
            sslFactory = ctx.getServerSocketFactory();
            SSLServerSocket s = (SSLServerSocket) sslFactory.createServerSocket(1234);
            s.accept();
            
            
            
        } catch (IOException e) {
            System.out.print(e);
        }
    }
}
