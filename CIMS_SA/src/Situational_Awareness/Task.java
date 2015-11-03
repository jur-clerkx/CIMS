/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Jense
 */
public class Task implements Serializable {

    private int taskID;
    private String name;
    private String urgency;
    private String status;
    private String description;
    private String location;
    private ArrayList<Unit> units;
    private boolean accepted;

    public boolean isAccepted() {
        return accepted;
    }
    private ArrayList<Progress> progressList;

    /**
     * Constructs a task object
     *
     * @param taskID Greater than 0
     * @param name Not longer than 255 characters or null
     * @param urgency Low, Medium or High
     * @param status Not longer than 255 characters or null
     * @param location Not longer than 255 characters or null
     * @param description Not longer than 255 characters or null
     */
    public Task(int taskID, String name, String urgency, String status, String location, String description) {
        if (taskID > 0 && (name != null && name.length() < 255) && (urgency != null && (urgency == "High" || urgency == "Medium" || urgency == "Low"))
                && (status != null && status.length() < 255) && (location != null && location.length() < 255) && (description != null && description.length() < 255)) {
            this.taskID = taskID;
            this.name = name;
            this.urgency = urgency;
            this.status = status;
            this.location = location;
            this.description = description;
            this.accepted = false;
            this.progressList = new ArrayList<>();
            this.units = new ArrayList<>();
        } else {
            throw new IllegalArgumentException("Make sure you fill in every field.");
        }
        
    }

    public int getTaskID() {
        return this.taskID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (!this.name.equals(name)) {
            this.name = name;
        }
    }

    public String getUrgency() {
        return this.urgency;
    }

    public void setUrgency(String urgency) {
        if (!this.urgency.equals(urgency)) {
            this.urgency = urgency;
        }
    }

    public String getStatus() {
        return this.status;
    }

    private void setStatus(String status) {
        if (!this.status.equals(status)) {
            this.status = status;
        }
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        if (!this.location.equals(location)) {
            this.location = location;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if (!this.description.equals(description)) {
            this.description = description;
        }
    }

    public ArrayList<Unit> getUnits() {
        return this.units;
    }

    public Unit getUnit(int ID) {
        for (Unit unit : units) {
            if (unit.getUnitID() == ID) {
                return unit;
            }
        }
        return null;
    }

    public ArrayList<Progress> getProgressList() {
        return this.progressList;
    }

    /**
     * Adds a unit to the list of units working on the task
     *
     * @param unit can't be in the list already
     */
    public void addUnit(Unit unit) {
        if (!this.units.contains(unit)) {
            this.units.add(unit);
        } else {
            throw new IllegalArgumentException("Unit is already listed.");
        }
    }

    /**
     * Removes a unit from the list of units working on the task
     *
     * @param unit has to be in the list
     */
    public void delUnit(Unit unit) {
        if (this.units.contains(unit)) {
            this.units.remove(unit);
        }
        else {
            throw new IllegalArgumentException("Unit isn't listed.");
        }
    }

    /**
     * Task gets accepted by a unit.
     */
    public void operateAcceptance() {
        this.accepted = !this.accepted;
    }

    /**
     * Sets the status of a task
     *
     * @param status Not longer than 255 characters or null
     */
    public void operateStatus(String status) {
        if (status != null && status.length() < 255) {
            this.setStatus(status);
        } else {
            throw new IllegalArgumentException("Status can't be longer than 255 characters.");
        }
        
    }

    /**
     * Updates the progress of a task
     *
     * @param progress not null
     */
    public void updateProgress(Progress progress) {
        if (progress != null) {
            this.progressList.add(progress);
        } else {
            throw new IllegalArgumentException("No progress selected.");
        }
    }

    //generate an string with task information
    public String generateInfo() {
        String info;
        int infoInt = this.taskID;
        info = infoInt + "||" + this.name + "||" + this.urgency + "||" + this.status + "||" + this.location + "||" + this.description;
        return info;
    }
}
