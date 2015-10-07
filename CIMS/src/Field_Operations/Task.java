/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations;

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
    private ArrayList<Progress> progress;

    public Task(int taskID, String name, String urgency, String status, String location, String description) {
        
        this.taskID = taskID;
        this.name = name;
        this.urgency = urgency;
        this.status = status;
        this.location = location;
        this.description = description;
        this.accepted = false;
        this.progress = new ArrayList<>();
        this.units = new ArrayList<>();
    }
    public boolean isAccepted() {
        return this.accepted;
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

    /**
     * Adds a unit to the list of units working on the task
     *
     * @param unit
     */
    public void addUnit(Unit unit) {
        if (!this.units.contains(unit)) {
            this.units.add(unit);
        }
    }

    /**
     * Removes a unit from the list of units working on the task
     *
     * @param unit
     */
    public void delUnit(Unit unit) {
        if (this.units.contains(unit)) {
            this.units.remove(unit);
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
     * @param status
     */
    public void operateStatus(String status) {
        this.setStatus(status);
    }

    /**
     * Updates the progress of a task
     *
     * @param progress
     */
    public void updateProgress(Progress progress) {
        if (progress != null) {
            this.progress.add(progress);
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
