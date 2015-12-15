/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestServerFunctions;

import Global.Domain.User;
import Network.Connection;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author sebas
 */
public class TestConnectionFXMLController implements Initializable {

    @FXML
    private Label lblIsConnected;
    @FXML
    private Button btnConnectToServer;
    @FXML
    private Button btnSend;
    @FXML
    private TextField tfFunction;
    @FXML
    private Label lblResult;
    @FXML
    private TextField tfIpAddress;
    @FXML
    private TextField tfPort;

    private ConnectionToServer serverConnection;
    public static LinkedBlockingQueue<Object> messages;
    private Socket socket;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        serverConnection = null;
        messages = new LinkedBlockingQueue<>();
        socket = null;
    }

    @FXML
    private void connectToServer(MouseEvent event) throws ClassNotFoundException {
        try {
            int port = Integer.parseInt(tfPort.getText());
            socket = new Socket(tfIpAddress.getText(), port);
            messages = new LinkedBlockingQueue<>();
            //connetion class for connection of the serverConnection.
            serverConnection = new ConnectionToServer(socket);
            authorize();
            if(serverConnection != null) {
                System.out.println("Connected");
            } else {
                System.out.println("Not Connectsd");
            }
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
    @FXML
    private void sendCommand(MouseEvent event) {
        try {
            if(!authorize()) {
                return;
            }
            
            String command = tfFunction.getText();
            serverConnection.write(command);
        } catch(Exception ex) {
            ex.getStackTrace();
        }
    }
    
    private boolean authorize() throws ClassNotFoundException {
        String[] credentials = new String[3];
        credentials[0] = "1";
        credentials[1] = "Sebas";
        credentials[2] = "0000";
        serverConnection.write(credentials);
        User u = (User)serverConnection.read();
        if(u != null) {
            return u.authorized();
        }
        return false;
    }
}
