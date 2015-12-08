/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.DAO;

/**
 *
 * @author Jense Schouten
 */
public interface UserDAO {
    
    /**
     * Count the number of Users in the database.
     * @return number of users
     */
    public int count();
}
