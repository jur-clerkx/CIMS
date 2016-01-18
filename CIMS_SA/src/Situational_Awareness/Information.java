/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;

import Field_Operations.Task;
import Network.PublicUser;
import java.io.Serializable;
import static java.lang.Math.toIntExact;

/**
 *
 * @author sebas
 */
public class Information implements Serializable{

    private Long ID;
    private Task task;
    private String name;
    private String description;
    private String location;
    private int casualties;
    private int toxic;
    private int danger;
    private int impact;
    private String image;
    private boolean Private;
    private PublicUser user;

    public Information(Long ID, Task task, String name, String description, String location, int casualities, int toxic, int danger, int impact, String image, PublicUser user, boolean Private) {
        if (ID > 0 && description != null && description.length() < 255 && location != null && location.length() < 120 & casualities >= 0 && toxic >= 0
                && danger >= 0  && impact > 0 && impact < 10000) {
            this.ID = ID;
            this.task = task;
            this.description = description;
            this.location = location;
            this.casualties = casualities;
            this.toxic = toxic;
            this.danger = danger;
            this.impact = impact;
            this.image = image;
            this.user = user;
            this.name = name;
            this.Private = Private;
        } else {
            throw new IllegalArgumentException("Information Exception: A parameter wasn't accepted.");
        }

    }
    public Information(Long ID, String name, String description, String location, String image){
        try{
        this.ID = ID;
        this.task = null;
        this.name = name; 
        this.description = description;
        this.location = location;
        this.image = image;
        this.casualties = 0;
        this.toxic = 0;
        this.danger = 0;
        this.impact = 0;
        this.user = null;
        this.Private = false;}
        catch(Exception e){
            System.out.println("Information Exception: A parameter wasn't accepted." + e.getMessage());
        }
        
    }

    public String getName() {
        return name;
    }

    public PublicUser getUser() {
        return user;
    }

    public int getID() {
        return toIntExact(ID);
    }

    public Task getTaskID() {
        return task;
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

    public int getToxic() {
        return toxic;
    }

    public int getDanger() {
        return danger;
    }

    public int getImpact() {
        return impact;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        if(task != null)
        {
        return "Task " + task.getName() + " on location: " + location + " | casualties: " + casualties + " | toxicity: " + toxic + " | level of dangerous: " + danger;
        }
        else return "On location: " + location + " | casualties: " + casualties + " | toxicity: " + toxic + " | level of dangerous: " + danger;
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
     * Change the toxicity level
     *
     * @param number bigger than 0
     * @return True if changed
     */
    public boolean changeToxicity(int number) {
        if (number > 0) {
            this.toxic = number;
            return true;
        }
        return false;
    }

    /**
     * Change the danger level
     *
     * @param number bigger than 0
     * @return True if changed
     */
    public boolean changeDanger(int number) {
        if (number > 0) {
            this.danger = number;
            return true;
        }
        return false;
    }

    public String getFirstName() {
        return name.substring(0, name.indexOf(" "));
    }

    public String getLastName() {
        return name.substring(name.indexOf(" ")+1) ;
    }

    public String getURL() {
        return this.image;
    }
}
