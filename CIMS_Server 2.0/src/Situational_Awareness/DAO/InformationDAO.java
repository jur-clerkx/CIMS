/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness.DAO;

import Situational_Awareness.Domain.Information;
import java.util.ArrayList;

/**
 *
 * @author Jense Schouten
 */
public interface InformationDAO {

    /**
     * Count the number of Vehicle in the database.
     *
     * @return number of Vehicles
     */
    public int count();

    /**
     * Create a new Vehicle and add it to the database.
     *
     * @param i Not null
     */
    public void create(Information i);

    /**
     * Edit an existing Vehicle in the database.
     *
     * @param i Not null
     */
    public void edit(Information i);

    /**
     * Remove an existing Vehicle from the database.
     *
     * @param i Not null
     */
    public void remove(Information i);

    /**
     * Find a Vehicle using it's id.
     *
     * @param id
     * @return Vehicle with @param id
     */
    public Information find(int id);

    /**
     * Find all Vehicles in the database.
     *
     * @return all vehicles from the database.
     */
    public ArrayList<Information> findAll();
}
