/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author Jur
 */
public class SSLServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Set up serversocket
        ServerSocketFactory sf = SSLServerSocketFactory.getDefault();
        final SSLServerSocket socket = (SSLServerSocket) sf.createServerSocket(4443);
        System.out.println(Arrays.toString(socket.getSupportedCipherSuites()));
        System.out.println(Arrays.toString(socket.getEnabledCipherSuites()));
        System.out.println("Add new list of enabled");
        socket.setEnabledCipherSuites(new String[] {"SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA"});
        System.out.println(Arrays.toString(socket.getEnabledCipherSuites()));

        Socket client = socket.accept();
        client.getOutputStream().write("Hello World\n".getBytes("ASCII"));
        client.getOutputStream().flush();
        client.close();
        System.out.println("Datatransfer completed!");
    }

}
