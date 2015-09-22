/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.Field_Operations;

import java.util.ArrayList;

/**
 *
 * @author Jense
 */
public class Task {

    private int taskID;
    private String name;
    private String urgency;
    private String status;
    private String description;
    private String location;
    private ArrayList<Unit> units;

    public void task(int taskID, String name, String urgency, String status, String location, String description) {
        this.taskID = taskID;
        this.name = name;
        this.urgency = urgency;
        this.status = status;
        this.location = location;
        this.description = description;
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

    public void setstatus(String status) {
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

    public void setdescription(String description) {
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

    public void addUnit(Unit unit) {
        if (!this.units.contains(unit)) {
            this.units.add(unit);
        }
    }
    
    //generate an string with task information
    public String generateInfo(){
        String info;
        int infoInt = this.taskID;
        info = infoInt + "||" +this.name + "||" + this.urgency+ "||" + this.status+ "||" + this.location+ "||" + this.description;    
        return info;
    }
}
