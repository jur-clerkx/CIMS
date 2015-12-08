/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Unit;
import java.util.ArrayList;

/**
 *
 * @author Jense Schouten
 */
public interface UnitDAO {

    /**
     * Count the number of unit in the database.
     *
     * @return number of units
     */
    public int count();

    /**
     * Create a new unit and add it to the database.
     *
     * @param u Not null
     */
    public void create(Unit u);

    /**
     * Edit an existing unit in the database.
     *
     * @param u Not null
     */
    public void edit(Unit u);

    /**
     * Remove an existing unit from the database.
     *
     * @param u Not null
     */
    public void remove(Unit u);

    /**
     * Find a unit using it's id.
     *
     * @param id
     * @return unit with @param id
     */
    public Unit find(int id);

    /**
     * Find all units in the database.
     *
     * @return all units from the database.
     */
    public ArrayList<Unit> findAll();

    /**
     * Find all active units in the database.
     *
     * @return all active units from the database.
     */
    public ArrayList<Unit> findallActive();

    /**
     * Find all inactive units in the database.
     *
     * @return all inactive units from the database.
     */
    public ArrayList<Unit> findallInactive();
}
