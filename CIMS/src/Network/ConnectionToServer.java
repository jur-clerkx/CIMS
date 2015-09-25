package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jense Schouten
 */
public class ConnectionToServer {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private boolean reading;

    /**
     * creates an instance of this class, creates the thread for getting
     * messages from the server.
     *
     * @param socket Connection socket
     * @throws IOException
     */
    ConnectionToServer(Socket socket) throws IOException {
        this.socket = socket;
        in = new ObjectInputStream(this.socket.getInputStream());
        out = new ObjectOutputStream(this.socket.getOutputStream());
        reading = true;

        Thread read;
        read = new Thread() {
            @Override
            public void run() {
                while (reading) {
                    try {
                        Object obj = in.readObject();
                        Client.messages.add(obj);
                        System.out.println(obj);
                    } catch (ClassNotFoundException | IOException ex) {
                        reading = false;
                        Logger.getLogger(ConnectionToServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                closeconn();
            }
        };

        read.setDaemon(false);
        read.start();
    }

    private void closeconn() {
        try {
            if (!socket.isClosed()) {
                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException ex) {
        }
    }

    /**
     * Writes an object to to the server
     *
     * @param obj object to be send to Server
     */
    public void write(Object obj) {
        try {
            out.writeObject(obj);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
