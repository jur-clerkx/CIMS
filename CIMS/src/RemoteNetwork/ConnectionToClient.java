package RemoteNetwork;

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
public class ConnectionToClient {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    /**
     * creates an instance of this class, creates the thread for getting
     * messages from the clients. it also sets the room or lobby id.
     *
     * @param socket Connection socket
     * @throws IOException
     */
    ConnectionToClient(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        Thread read = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object obj = in.readObject();
                        /*
                         //--Do stuff for server--//                        
                         */
                        if (obj != null) {

                            
                        }
                        //--End--//
                    } catch (IOException | ClassNotFoundException | NumberFormatException ex) {
                        Logger.getLogger(ConnectionToClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        read.setDaemon(false); // terminate when main ends
        read.start();
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
