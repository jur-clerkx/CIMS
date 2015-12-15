package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
     *//*
    public void write(Object obj) {
        try {
            out.writeObject(obj);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }*/
    
    public void write(Object function) {
        try {
            Object obj = function;
            out.writeObject(obj);
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
    public Object read() throws ClassNotFoundException {
        try {
            return in.readObject();
            
        } catch(IOException ex){}
        return null;
    }
}
