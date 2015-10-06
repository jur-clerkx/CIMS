package Network;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Jense Schouten
 */
public class Client {

    private ConnectionToServer serverConnect;
    public static LinkedBlockingQueue<Object> messages;
    private Socket socket;

    /**
     * Creates an instance of the client.
     *
     * @param IPAddress
     * @param port
     * @throws IOException
     */
    public Client(String IPAddress, int port) throws IOException {

        socket = new Socket(IPAddress, port);
        messages = new LinkedBlockingQueue<>();
        //connetion class for connection of the server.
        serverConnect = new ConnectionToServer(socket);
    }

    /**
     * Send a object to the server, in most cases this is a message to be send
     * to all users.
     *
     * @param obj
     */
    public void send(Object obj) {
        serverConnect.write(obj);
    }

    public Object getMessage() {
        if (messages.size() > 0) {
            Iterator<Object> it = messages.iterator();
            return it.next();

        }
        return null;
    }

    public void removeMessage(Object message) {
        messages.remove(message);
    }

    /**
     * Useless. is been used to start the process when in developer mode.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        //ChatClient c = new Client("192.168.27.124", 4444);
        Client c = new Client("schouten", 1234);
        String[] credentials = "/Schouten".split("/");
        c.send(credentials);

    }
}
