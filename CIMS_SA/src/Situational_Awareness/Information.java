/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;

import java.sql.Blob;
import java.util.ArrayList;

/**
 *
 * @author sebas
 */
public class Information {

    private int ID;
    private int taskID;
    private String description;
    private String location;
    private int casualties;
    private boolean toxic;
    private int danger;
    private int impact;
    private Blob image;
    private ArrayList<PublicUser> users;

    public Information(int ID, int taskID, String description, String location, int casualities, boolean toxic, int danger, int impact) {
        if (ID > 0 && taskID > 0 && description != null && description.length() < 255 && location != null && location.length() < 120 & casualities > 0 
                && (danger == 0 || danger == 1 || danger == 2) && impact > 0 ) {
            this.ID = ID;
            this.taskID = taskID;
            this.description = description;
            this.location = location;
            this.casualties = casualities;
            this.toxic = toxic;
            this.danger = danger;
            this.impact = impact;
            this.image = null;
            users = new ArrayList<PublicUser>();
        } else {
            throw new IllegalArgumentException("Information Exception: A parameter wasn't accepted.");
        }

    }

    public Information(int ID, int taskID, String description, String location, int casualities, boolean toxic, int danger, int impact, Blob image) {
        this(ID, taskID, description, location, casualities, toxic, danger, impact);
        this.image = image;
    }

    public int getID() {
        return ID;
    }

    public int getTaskID() {
        return taskID;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getCasualities() {
        return casualties;
    }

    public boolean getToxic() {
        return toxic;
    }

    public int getDanger() {
        return danger;
    }

    public int getImpact() {
        return impact;
    }

    public Blob getImage() {
        return image;
    }

    public ArrayList<PublicUser> getUsers() {
        return users;
    }

    public String toString() {
        return "Task " + taskID + " on location: " + location + " | casualties: " + casualties + " | toxicity: " + toxic + " | level of dangerous: " + danger;

    }

    /**
     * Change the casualty counter
     *
     * @param number bigger than 0
     * @return True if changed
     */
    public boolean addCasualties(int number) {
        if (number > 0) {
            this.casualties += number;
            return true;
        }
        return false;
    }

    /**
     * Change toxicity
     */
    public void changeToxicity() {
        this.toxic = !this.toxic;
    }
    
    /**
     * Change the danger level
     *
     * @param number 1, 2 or 3
     * @return True if changed
     */
    public boolean changeDanger(int number) {
        if (number == 1 || number == 2 || number == 3) {
            this.danger = number;
            return true;
        }
        return false;
    }

    /**
     * Add a user to the information
     * @param user not null
     * @return True if added
     */
    public boolean addUser(PublicUser user) {
        if (user != null) {
            users.add(user);
            return true;
        }
        return false;
    }
    /**
     * Removes a user from the list
     * @param user not null and in list
     * @return True if deleted
     */
    public boolean delUser(PublicUser user) {
        if (user != null) {
            if (users.contains(user)) {
                this.users.remove(user);
                return true;
            } else {
                throw new IllegalArgumentException("User isn't listed.");
            }
        } else {
            throw new IllegalArgumentException("No user selected.");
        }
    }

    public String getFirstName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getLastName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getURL() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
