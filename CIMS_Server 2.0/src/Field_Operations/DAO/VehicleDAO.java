/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Vehicle;
import java.util.ArrayList;

/**
 *
 * @author Jense Schouten
 */
public interface VehicleDAO {

    /**
     * Count the number of Vehicle in the database.
     * @return number of Vehicles
     */
    public int count();

    /**
     * Create a new Vehicle and add it to the database.
     *
     * @param v Not null
     */
    public void create(Vehicle v);

    /**
     * Edit an existing Vehicle in the database.
     *
     * @param v Not null
     */
    public void edit(Vehicle v);

    /**
     * Remove an existing Vehicle from the database.
     * @param v Not null
     */
    public void remove(Vehicle v);

    /**
     * Find a Vehicle using it's id.
     * @param id 
     * @return Vehicle with @param id
     */
    public Vehicle find(int id);

    /**
     * Find all Vehicles in the database.
     * @return all vehicles from the database.
     */
    public ArrayList<Vehicle> findAll();
}
