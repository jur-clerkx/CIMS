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

    public void create(PublicUser pu);

    public void edit(PublicUser pu);

    public void remove(PublicUser pu);

    public PublicUser find(int id);

    public ArrayList<PublicUser> findall();
    
    public PublicUser login(String username, String password);
}
