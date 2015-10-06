/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations;

import java.util.ArrayList;
import java.util.List;
import Network.User;

/**
 *
 * @author Jense
 */
public class Unit {

    private int unitID;
    private String name;
    private String description;
    private String shift;
    private List<User> members;
    private List<Material> materials;
    private List<Task> tasks;
    private List<Vehicle> vehicles;

    public Unit(int unitID, String name, String description, String shift) {
        this.unitID = unitID;
        this.name = name;
        this.description = description;
        this.shift = shift;
        this.members = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.vehicles = new ArrayList<>();
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
     * @param task
     * @param acceptanceReason
     */
    public void acceptTask(Task task) {
        task.operateAcceptance();
        tasks.add(task);
    }

    /**
     * Adds a user to the unit
     *
     * @param user
     */
    public void addUser(User user) {
        this.members.add(user);
    }

    /**
     * Removes a user from the unit
     *
     * @param user
     */
    public void delUser(User user) {
        if (members.contains(user)) {
            this.members.remove(user);
        }
    }

    /**
     * Reserves a material to the unit
     *
     * @param vehicle
     */
    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    /**
     * Removes a reserved vehicle from the unit
     *
     * @param vehicle
     */
    public void delVehicle(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
        }
    }

    /**
     * Reserves a material to the unit
     *
     * @param material
     */
    public void addMaterial(Material material) {
        if (material != null) {
            this.materials.add(material);
        }
    }

    /**
     * Removes a reserved material from the unit
     *
     * @param material
     */
    public void delMaterial(Material material) {
        if (materials.contains(material)) {
            this.materials.add(material);
        }
    }

    @Override
    public String toString() {
        return this.name + " " + this.shift;
    }
}
