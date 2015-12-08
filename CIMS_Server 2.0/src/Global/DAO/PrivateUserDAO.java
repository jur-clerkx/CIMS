/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.DAO;

import Global.Domain.PrivateUser;
import java.util.ArrayList;

/**
 *
 * @author Jense Schouten
 */
public interface PrivateUserDAO {

    /**
     * Create a new Private User and add it to the database.
     * @param pu Not null
     */
    public void create(PrivateUser pu);

    /**
     * Edit an existing Private User in the database.
     * @param pu Not null
     */
    public void edit(PrivateUser pu);

    /**
     * Remove an existing Private User from the database.
     * @param pu 
     */
    public void remove(PrivateUser pu);

    /**
     * Find a Private user using it's id.
     * @param id Not null
     * @return Private User with @param id
     */
    public PrivateUser find(int id);

    /**
     * Find all Private Users in the database.
     * @return all Private Users from the database.
     */
    public ArrayList<PrivateUser> findAll();
        
    /**
     * Logs a Private User into the system and allow him access.
     * @param username Not null
     * @param password Not null
     * @return the logged in Private User
     */
    public PrivateUser login(String username, String password);
}
