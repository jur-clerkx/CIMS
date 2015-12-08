/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness.Domain;

import Field_Operations.Domain.Task;
import Global.Domain.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebas & Jense Schouten
 */
@Entity
@Table(name = "Information")
@NamedQueries({
    @NamedQuery(name = "Information.count", query = "SELECT COUNT(i) FROM Information AS i"),
    @NamedQuery(name = "Information.getAll", query = "SELECT i FROM Information AS i")
})
public class Information implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String location;
    private int casualties;
    private int toxic;
    private int danger;
    private int impact;
    private String image;
    @OneToOne
    private User user;
    @OneToOne
    private Task task;

    /**
     * Gets the name of this information
     *
     * @return String with name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user of this information
     *
     * @return User with user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the id of this information
     *
     * @return int with id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the task of this information
     *
     * @return Task with task
     */
    public Task getTask() {
        return task;
    }

    /**
     * Gets the description of this information
     *
     * @return String with description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the location of this information
     *
     * @return String with location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the casualties of this information
     *
     * @return int with casualties
     */
    public int getCasualities() {
        return casualties;
    }

    /**
     * Gets the toxic of this information
     *
     * @return int with toxic
     */
    public int getToxic() {
        return toxic;
    }

    /**
     * Gets the danger of this information
     *
     * @return int with danger
     */
    public int getDanger() {
        return danger;
    }

    /**
     * Gets the impact of this information
     *
     * @return int with impact
     */
    public int getImpact() {
        return impact;
    }

    /**
     * Gets the imageURL of this information
     *
     * @return String with imageURL
     */
    public String getImageURL() {
        return image;
    }

    public Information() {
    }

    /**
     * Constructor of the class information
     *
     * @param task task of this information
     * @param name name of this information
     * @param description description of this information
     * @param location location of this information
     * @param casualities casualties in this information
     * @param toxic toxic level of this information
     * @param danger danger level of this information
     * @param impact impact level of this information
     * @param image image URL of this information
     * @param user public user that has registered this information
     */
    public Information(Task task, String name, String description,
            String location, int casualities, int toxic, int danger, int impact,
            String image, User user) {
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

    /**
     * Override for the toString function
     *
     * @return all values of this information.
     */
    @Override
    public String toString() {
        if (task != null) {
            return "Task " + task.getName() + " on location: " + location + " | casualties: " + casualties + " | toxicity: " + toxic + " | level of dangerous: " + danger;
        } else {
            return "On location: " + location + " | casualties: " + casualties + " | toxicity: " + toxic + " | level of dangerous: " + danger;
        }
    }

}
