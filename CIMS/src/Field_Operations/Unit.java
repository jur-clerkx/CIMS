/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations;

import java.util.ArrayList;
import java.util.List;
import Network.User;
import java.io.Serializable;

/**
 *
 * @author Jense
 */
public class Unit implements Serializable {

    private int unitID;
    private String name;
    private String description;
    private String shift;
    private List<User> members;
    private List<Material> materials;
    private List<Task> tasks;
    private List<Vehicle> vehicles;

    /**
     * Constructs a unit object
     *
     * @param unitID Greater than 0
     * @param name Not longer than 255 characters or null
     * @param description Not longer than 255 characters
     * @param shift Not longer than 255 characters or null
     */
    public Unit(int unitID, String name, String description, String shift) {
        if (unitID > 0 && name != null && name.length() < 255 && description != null && description.length() < 255 && shift != null && shift.length() < 255) {
            this.unitID = unitID;
            this.name = name;
            this.description = description;
            this.shift = shift;
            this.members = new ArrayList<>();
            this.materials = new ArrayList<>();
            this.tasks = new ArrayList<>();
            this.vehicles = new ArrayList<>();
        } else {
            throw new IllegalArgumentException("Make sure you fill in every field.");
        }

    }

    public int getUnitID() {
        return this.unitID;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getShift() {
        return this.shift;
    }

    /**
     * Unit accepts a task. Task gets added to the units task list.
     *
     * @param task not null
     */
    public void acceptTask(Task task) {
        if (task != null) {
            task.operateAcceptance();
            tasks.add(task);
        } else {
            throw new IllegalArgumentException("No task selected.");
        }

    }

    /**
     * Adds a user to the unit
     *
     * @param user not null
     */
    public void addUser(User user) {
        if (user != null) {
            this.members.add(user);
        } else {
            throw new IllegalArgumentException("No user selected.");
        }

    }

    /**
     * Removes a user from the unit
     *
     * @param user not null and in the list
     */
    public void delUser(User user) {
        if (user != null) {
            if (members.contains(user)) {
                this.members.remove(user);
            } else {
                throw new IllegalArgumentException("User isn't listed.");
            }
        } else {
            throw new IllegalArgumentException("No user selected.");
        }

    }

    /**
     * Reserves a material to the unit
     *
     * @param vehicle not null
     */
    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            this.vehicles.add(vehicle);
        } else {
            throw new IllegalArgumentException("No material selected.");
        }

    }

    /**
     * Removes a reserved vehicle from the unit
     *
     * @param vehicle no null
     */
    public void delVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            if (vehicles.contains(vehicle)) {
                this.vehicles.remove(vehicle);
            } else {
                throw new IllegalArgumentException("Material isn't listed.");
            }
        } else {
            throw new IllegalArgumentException("No material selected.");
        }

    }

    /**
     * Reserves a material to the unit
     *
     * @param material not null
     */
    public void addMaterial(Material material) {
        if (material != null) {
            this.materials.add(material);
        } else {
            throw new IllegalArgumentException("No material selected.");
        }
    }

    /**
     * Removes a reserved material from the unit
     *
     * @param material not null
     */
    public void delMaterial(Material material) {
        if (material != null) {
            if (materials.contains(material)) {
                this.materials.remove(material);
            } else {
                throw new IllegalArgumentException("Material isn't listed.");
            }
        } else {
            throw new IllegalArgumentException("No material selected.");
        }

    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        return this.name + " " + this.shift;
    }

    public ArrayList<Vehicle> getVehicles() {
        return (ArrayList) this.vehicles;
    }

    public ArrayList<User> getMembers() {
        return (ArrayList) this.members;
    }

    public ArrayList<Material> getMaterials() {
        return (ArrayList) this.materials;
    }

    public int getSize() {
        return this.members.size();
    }

    public void setTask(Task get) {
        this.tasks.add(get);
    }
}
