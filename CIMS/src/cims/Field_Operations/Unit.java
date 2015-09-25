/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.Field_Operations;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jense
 */
public class Unit {

    private int unitID;
    private String name;
    private String description;
    private List<User> members;
    private List<Material> materials;
    private List<Task> tasks;
    private List<Vehicle> vehicles;
    
    public void Unit(String name, String description) {
        this.name = name;
        this.description = description;
        this.members = new ArrayList<User>();
        this.materials = new ArrayList<Material>();
        this.tasks = new ArrayList<Task>();
        this.vehicles = new ArrayList<Vehicle>();
    }

    public int getUnitID() {
        return this.unitID;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    /**
     * Unit accepts a task. Task gets added to the units task list.
     * @param task
     * @param acceptanceReason 
     */
    public void acceptTask(Task task, String acceptanceReason) {
        if (tasks.size() == 0) {
            task.operateAcceptance();
            tasks.add(task);
        }        
    
    }
    /**
     * Adds a user to the unit
     * @param user 
     */
    public void addUser(User user) {
        this.members.add(user);
    }
    /**
     * Removes a user from the unit
     * @param user 
     */
    public void delUser(User user) {
        if (members.contains(user)) {
            this.members.remove(user);
        }
    }
    /**
     * Reserves a material to the unit
     * @param vehicle 
     */
    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }
    /**
     * Removes a reserved vehicle from the unit
     * @param vehicle 
     */
    public void delVehicle(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
        }
    }
    /**
     * Reserves a material to the unit
     * @param material 
     */
    public void addMaterial(Material material) {
        if (material != null) {
            this.materials.add(material);
            material.setUnit(this);
        }       
    }
    /**
     * Removes a reserved material from the unit
     * @param material 
     */
    public void delMaterial(Material material) {
        if (materials.contains(material)) {
            this.materials.add(material);
        }
    }
}
