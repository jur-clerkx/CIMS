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

    public void create(PrivateUser pu);

    public void edit(PrivateUser pu);

    public void remove(PrivateUser pu);

    public PrivateUser find(int id);

    public ArrayList<PrivateUser> findAll();
        
    public PrivateUser login(String username, String password);
}
