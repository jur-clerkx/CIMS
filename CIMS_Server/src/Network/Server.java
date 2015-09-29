package Network;

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
    public static ArrayList<Connection> connections;
    public static LinkedBlockingQueue<Object> objects;
    private ServerSocket serverSocket;
    public boolean searching;

    /**
     * creates a instance of the class 'Server'.
     *
     * @param port port the server is listening to.
     */
    public Server(int port) {
        objects = new LinkedBlockingQueue<>();
        connections = new ArrayList<>();

        // Try co connect to the serversocket.
        try {
            this.serverSocket = new ServerSocket(port);
            LOG.log(Level.INFO, "Server is running. Listening on port: {0}", serverSocket.getLocalPort());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getSearching() {
        return this.searching;
    }

    public void start() {
        searching = true;
        // Connect to clients.
        Thread connectClients = new Thread() {
            @Override
            public void run() {
                LOG.log(Level.INFO, "searching for clients..");
                while (searching) {
                    try {
                        Socket s = serverSocket.accept();
                        if (searching) {
                            connections.add(new Connection(s));
                            LOG.log(Level.INFO, "New Client Connected: {0}", s.getInetAddress());
                        }
                    } catch (IOException e) {
                        LOG.log(Level.WARNING, "IOException occurred: {0}", e.getMessage());
                    }
                }

            }
        };
        connectClients.setDaemon(false);
        connectClients.start();
    }

    public void stop() {
        searching = false;
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Connection conn : connections) {
            conn.kill();
        }
        
        connections.clear();
        LOG.log(Level.INFO, "Server Stopped");
    }

    /**
     * Sends a object to a specific user.
     *
     * @param index userId of the user-list.
     * @param object object to be send
     * @throws IndexOutOfBoundsException
     */
    public static void sendToOne(int index, Object object) throws IndexOutOfBoundsException {
        connections.get(index).write(object);
    }

    /**
     * Sends a object to all users added in the client list.
     *
     * @param object object to be send
     */
    public static void sendToAll(Object object) {
        for (Connection client : connections) {
            client.write(object);
        }
    }

    /**
     * Sends the object-list to all users added in the client list.
     *
     */
    public static void sendBackupToAll() {
        for (Connection client : connections) {
            client.write(objects);
        }
    }
}
