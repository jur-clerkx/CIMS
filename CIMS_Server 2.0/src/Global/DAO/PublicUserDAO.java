/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.DAO;

import Global.Domain.PublicUser;
import java.util.ArrayList;

/**
 *
 * @author Jense Schouten
 */
public interface PublicUserDAO {

    /**
     * Create a new Public User and add it to the database.
     * @param pu Not null
     */
    public void create(PublicUser pu);
/**
     * Edit an existing Public User in the database.
     * @param pu Not null
     */
    public void edit(PublicUser pu);
  /**
     * Remove an existing Public User from the database.
     * @param pu 
     */
    public void remove(PublicUser pu);
 /**
     * Find a Public User using it's id.
     * @param id Not null
     * @return Public User with @param id
     */
    public PublicUser find(int id);

    public ArrayList<PublicUser> findall();
    
    public PublicUser login(String username, String password);
}
