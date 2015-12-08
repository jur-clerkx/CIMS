/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.Domain;

import Global.Domain.PrivateUser;
import java.util.ArrayList;
import Network.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jense Schouten
 */
@Entity
@Table(name = "Unit")
@NamedQueries({
    @NamedQuery(name = "Unit.count", query = "SELECT COUNT(u) FROM Unit AS u"),
    @NamedQuery(name = "Unit.getAll", query = "SELECT u FROM Unit AS u")
})
public class Unit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @OneToMany
    private ArrayList<PrivateUser> members;
    @OneToMany
    private ArrayList<Material> materials;
    @OneToMany
    private ArrayList<Vehicle> vehicles;

    public Unit() {
    }

    /**
     * Gets the id of this Unit
     *
     * @return int with id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the name of this Unit
     *
     * @return String with name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the description of this Unit
     *
     * @return String with description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets all Vehicles of this Unit
     *
     * @return ArrayList with Vehicles
     */
    public ArrayList<Vehicle> getVehicles() {
        return (ArrayList) this.vehicles;
    }

    /**
     * Gets all PrivateUsers of this Unit
     *
     * @return ArrayList with PrivateUsers
     */
    public ArrayList<PrivateUser> getMembers() {
        return (ArrayList) this.members;
    }

    /**
     * Gets all Materials of this Unit
     *
     * @return ArrayList with Materials
     */
    public ArrayList<Material> getMaterials() {
        return (ArrayList) this.materials;
    }

    /**
     * Constructs a unit object
     *
     * @param id Greater than 0
     * @param name Not longer than 255 characters or null
     * @param description Not longer than 255 characters
     * @param shift Not longer than 255 characters or null
     */
    public Unit(int id, String name, String description) {
        if (id > 0 && name != null && name.length() < 255 && description != null && description.length() < 255) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.members = new ArrayList<>();
            this.materials = new ArrayList<>();
            this.vehicles = new ArrayList<>();
        } else {
            throw new IllegalArgumentException("Make sure you fill in every field.");
        }

    }

    /**
     * Adds a user to the unit
     *
     * @param user not null
     */
    public void addUser(PrivateUser user) {
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
    public void removeUser(User user) {
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
     * @param vehicle not null
     */
    public void removeVehicle(Vehicle vehicle) {
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
    public void removeMaterial(Material material) {
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

    /**
     * Overrides the toString method
     *
     * @return name of unit, amount of members, amount of vehicles and amount of
     * materials
     */
    @Override
    public String toString() {
        return this.name + " "
                + this.members.size() + " Members, "
                + this.vehicles.size() + "Vehicles "
                + this.materials.size() + "materials";
    }
}
