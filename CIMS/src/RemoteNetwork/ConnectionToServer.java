package RemoteNetwork;


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

        Thread read = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Object obj = in.readObject();
                        Client.messages.add(obj);
                        System.out.println(obj);

                    } catch (ClassNotFoundException | IOException ex) {
                        Logger.getLogger(ConnectionToServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        read.setDaemon(false);
        read.start();
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
        }
    }
}
