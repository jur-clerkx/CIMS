/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Jur
 */
public class PropertiesMediator {
    private Properties props;
    
    /**
     * Creates a new properties mediator, that loads the properties file if its valid. If it's not valid it will create a default properties file.
     */
    public PropertiesMediator() {
        //Try to load properties
        FileInputStream in;
        props = new Properties();
        try {
            in = new FileInputStream("settings.properties");
            props.load(in);
        } catch (FileNotFoundException ex) {
            props.setProperty("serverip", "127.0.0.1");
            props.setProperty("user", "Default user");
        } catch (IOException ex) {
            props.setProperty("serverip", "127.0.0.1");
            props.setProperty("user", "Default user");
        }
        //Check if properties are valid, else go back to default values
        if(!checkProperties()){
            props.clear();
            props.setProperty("serverip", "127.0.0.1");
            props.setProperty("user", "Default user");
        }
    }
    
    /**
     * Checks if the properties contains all the info that is needed, else return false
     */
    private boolean checkProperties() {
        if(props.containsKey("serverip") && props.containsKey("user")){
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Sets the new server URL
     * @param newURL If empty set to 127.0.0.1
     */
    public void setServerURL(String newURL) {
        if(newURL.isEmpty()) {
            props.setProperty("serverip", "127.0.0.1");
        } else {
            props.setProperty("serverip", newURL);
        }
        saveProps();
    }
    
    /**
     * Sets the new default user name
     * @param newUser Can be empty
     */
    public void setUser(String newUser) {
        props.setProperty("user", newUser);
        saveProps();
    }
    
    /**
     * Gets the username of the user
     * @return A string of the username, can be empty.
     */
    public String getUser() {
        return props.getProperty("user");
    }
    
    /**
     * Save the properties to a file
     */
    private void saveProps() {
        FileOutputStream out;

        try {
            out = new FileOutputStream("settings.properties");
            props.store(out, null);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found, trying to create a new one...");
            System.out.println(ex.getMessage());
            try {
                out = new FileOutputStream(new File("settings.properties"));
                props.store(out, null);
            } catch (FileNotFoundException ex1) {
                System.out.println("Saving properties failedfailed!");
                System.out.println(ex1.getMessage());
            } catch (IOException ex1) {
                System.out.println("Saving properties failed!");
                System.out.println(ex1.getMessage());
            }
        } catch (IOException ex) {
            System.out.println("Something went wrong! Trying to create new file...");
            System.out.println(ex.getMessage());
            try {
                out = new FileOutputStream(new File("settings.properties"));
                props.store(out, null);
            } catch (FileNotFoundException ex1) {
                System.out.println("Saving properties failed!");
                System.out.println(ex1.getMessage());
            } catch (IOException ex1) {
                System.out.println("Saving properties failed!");
                System.out.println(ex1.getMessage());
            }
        }
    }

    public String getServerURL() {
        return props.getProperty("serverip");
    }
}
