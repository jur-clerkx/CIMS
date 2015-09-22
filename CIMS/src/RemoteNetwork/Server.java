package RemoteNetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jense Schouten
 */
public class Server {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());
    public static ArrayList<ConnectionToClient> clientList;
    public static LinkedBlockingQueue<Object> objects;
    private ServerSocket serverSocket;

    /**
     * creates a instance of the class 'Server'.
     *
     * @param port port the server is listening to.
     */
    public Server(int port) {
        clientList = new ArrayList<>();
        objects = new LinkedBlockingQueue<>();

        // Try co connect to the serversoccet.
        try {
            this.serverSocket = new ServerSocket(port);
            LOG.log(Level.INFO, "Server is running. Listening on port: {0}", serverSocket.getLocalPort());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Connect to clients.
        Thread connectClients = new Thread() {
            @Override
            public void run() {
                LOG.log(Level.INFO, "searching for clients..");
                while (true) {
                    try {
                        Socket s = serverSocket.accept();
                        clientList.add(new ConnectionToClient(s));
                        LOG.log(Level.INFO, "New Client Connected: {0}", s.getInetAddress());
                    } catch (IOException e) {
                        LOG.log(Level.WARNING, "IOException occurred: {0}", e.getMessage());
                    }
                }
            }
        };
        connectClients.setDaemon(false);
        connectClients.start();
    }

    /**
     * Sends a object to a specific user.
     *
     * @param index userId of the user-list.
     * @param object object to be send
     * @throws IndexOutOfBoundsException
     */
    public static void sendToOne(int index, Object object) throws IndexOutOfBoundsException {
        clientList.get(index).write(object);
    }

    /**
     * Sends a object to all users added in the client list.
     *
     * @param object object to be send
     */
    public static void sendToAll(Object object) {
        for (ConnectionToClient client : clientList) {
            client.write(object);
        }
    }

    /**
     * Sends the object-list to all users added in the client list.
     *
     */
    public static void sendBackupToAll() {
        for (ConnectionToClient client : clientList) {
            client.write(objects);
        }
    }

    /**
     * Important. Is been used to start the server process.
     *
     * @param args
     */
    public static void main(String[] args) {
        Server s = new Server(4444);
    }
}
