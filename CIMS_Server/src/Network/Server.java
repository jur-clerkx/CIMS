package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Jense Schouten
 */
public class Server {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());
    public static ArrayList<Connection> connections;
    private ServerSocket serverSocket;
    public boolean searching;

    /**
     * creates a instance of the class 'Server'.
     *
     * @param port port the server is listening to.
     */
    public Server(int port) {
        connections = new ArrayList<>();

        try {
            this.serverSocket = new ServerSocket(port);
            LOG.log(Level.INFO, "Server is running. Listening on port: {0}", serverSocket.getLocalPort());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * gets if server is searching for users.
     *
     * @return if still searching for users
     */
    public boolean getSearching() {
        return this.searching;
    }

    /**
     * starts the server
     */
    public void start() {
        searching = true;
        Thread connectClients;
        connectClients = new Thread() {
            @Override
            public void run() {
                LOG.log(Level.INFO, "searching for clients..");
                while (searching) {
                    try {
                        Socket s = serverSocket.accept();
                        if (searching) {
                            Connection c = new Connection(s);
                            cleanUpList(c.getUserId());
                            connections.add(c);
                            LOG.log(Level.INFO, "New Client Connected: {0}", s.getInetAddress());
                        }
                    } catch (IOException e) {
                        LOG.log(Level.WARNING, "IOException occurred: {0}", e.getMessage());
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        };
        connectClients.setDaemon(false);
        connectClients.start();
    }

    /**
     * Cleans the connection list, removing all inactive users
     *
     * @param userid id of a inactive user.
     */
    private void cleanUpList(int userid) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                connections.removeIf(c -> c.getUserId() == userid);
            }
        });

    }

    /**
     * Stops the server
     */
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
}
