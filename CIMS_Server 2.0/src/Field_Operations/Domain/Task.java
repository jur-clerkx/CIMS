/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.Domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jense Schouten
 */
@Entity
@Table(name = "Task")
@NamedQueries({
    @NamedQuery(name = "Task.count", query = "SELECT COUNT(t) FROM Task AS t"),
    @NamedQuery(name = "Task.getAll", query = "SELECT t FROM Task AS t"),
    @NamedQuery(name = "Task.getAllActive", query = "SELECT t FROM Task AS t WHERE t.status != 'Completed' AND t.status != 'Cancelled'"),
    @NamedQuery(name = "Task.getAllInActive", query = "SELECT t FROM Task AS t WHERE t.status = 'Completed' AND t.status = 'Cancelled'")
})
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String urgency;
    private String status;
    private String description;
    private String location;
    private boolean accepted;
    private String reason;

    @OneToMany(mappedBy = "Task", fetch = FetchType.EAGER)
    private List<Progress> progressList;
    @ManyToOne(fetch = FetchType.EAGER)
    private Unit unit;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Roadmap> roadmaps;

    public Task() {
    }

    /**
     * Gets the id of this task
     *
     * @return int with id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Gets the name of this task
     *
     * @return String with name
     */
    public String getName() {
        return this.name;
    }

    /**
     * sets the name of this task
     *
     * @param name new name of this task
     */
    public void setName(String name) {
        if (!this.name.equals(name)) {
            this.name = name;
        }
    }

    /**
     * Gets the urgency of this task
     *
     * @return String with urgency
     */
    public String getUrgency() {
        return this.urgency;
    }

    /**
     * Sets the urgency of this task
     *
     * @param urgency new urgency of this task
     */
    public void setUrgency(String urgency) {
        if (!this.urgency.equals(urgency)) {
            this.urgency = urgency;
        }
    }

    /**
     * Gets the status of this task
     *
     * @return String with status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the status of this task
     *
     * @param status new status of this task
     */
    public void setStatus(String status) {
        if (!this.status.equals(status)) {
            this.status = status;
        }
    }

    /**
     * Gets the location of this task
     *
     * @return String with location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Sets the location of this task
     *
     * @param location new location of this task
     */
    public void setLocation(String location) {
        if (!this.location.equals(location)) {
            this.location = location;
        }
    }

    /**
     * Gets the description of this task
     *
     * @return String with location
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of this task
     *
     * @param description new description of this task
     */
    public void setDescription(String description) {
        if (!this.description.equals(description)) {
            this.description = description;
        }
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets all units of this task
     *
     * @return List with units
     */
    public Unit getUnit() {
        return this.unit;
    }

    /**
     * Gets all progress of this task
     *
     * @return List with progress
     */
    public List<Progress> getProgressList() {
        return this.progressList;
    }

    public List<Roadmap> getRoadmaps() {
        return roadmaps;
    }

    /**
     * Adds a roadmap to the list of roadmaps working on the task
     *
     * @param roadmap can't be in the list already
     */
    public void addRoadmap(Roadmap roadmap) {
        if (!this.roadmaps.contains(roadmap)) {
            this.roadmaps.add(roadmap);
        }
    }

    /**
     * Adds a roadmap to the list of roadmaps working on the task
     *
     * @param roadmap must be in the list already
     */
    public void removeRoadmap(Roadmap roadmap) {
        if (this.roadmaps.contains(roadmap)) {
            this.roadmaps.remove(roadmap);
        }
    }

    /**
     * Adds a unit to the list of units working on the task
     *
     * @param unit can't be in the list already
     */
    public void addUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     * Removes a unit from the list of units working on the task
     *
     * @param unit has to be in the list
     */
    public void removeUnit() {
       this.unit = null;
    }

    /**
     * Sets the status of a task
     *
     * @param status Not longer than 255 characters or null
     */
    public void operateStatus(String status) {
        this.setStatus(status);
    }

    /**
     * Updates the progress of a task
     *
     * @param progress not null
     */
    public void updateProgress(Progress progress) {
        if (progress != null) {
            this.progressList.add(progress);
        }
    }

    //generate an string with task information
    public String generateInfo() {
        String info;
        long infoInt = this.id;
        info = infoInt + "||" + this.name + "||" + this.urgency + "||" + this.status + "||" + this.location + "||" + this.description;
        return info;
    }

    /**
     * Constructs a task object
     *
     * @param name Not longer than 255 characters or null
     * @param urgency Low, Medium or High
     * @param status Not longer than 255 characters or null
     * @param location Not longer than 255 characters or null
     * @param description Not longer than 255 characters or null
     */
    public Task(String name, String urgency, String status, String location, String description) {
        this.name = name;
        this.urgency = urgency;
        this.status = status;
        this.location = location;
        this.description = description;
        this.progressList = new ArrayList();
    }

    /**
     * Constructs a task object
     *
     * @param name Not longer than 255 characters or null
     * @param urgency Low, Medium or High
     * @param status Not longer than 255 characters or null
     * @param location Not longer than 255 characters or null
     * @param description Not longer than 255 characters or null
     */
    public Task(int id, String name, String urgency, String status, String location, String description) {
        this.id = id;
        this.name = name;
        this.urgency = urgency;
        this.status = status;
        this.location = location;
        this.description = description;
        this.progressList = new ArrayList();
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", Urgency: " + this.urgency + ", Status: " + this.status;
    }

}
