/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.MGR;

import Global.DAO.PublicUserDAO;
import Global.DAO.PublicUserDAOImpl;
import Global.Domain.PublicUser;
import java.util.ArrayList;
import javax.persistence.EntityManager;

/**
 *
 * @author Jense
 */
public class PublicUserMGR {

    private PublicUserDAO publicUserDAO;

    public PublicUserMGR(EntityManager em) {
        publicUserDAO = new PublicUserDAOImpl(em);
    }

    public boolean createPublicUser(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }
        Object[] objects = (Object[]) o;
        if (objects.length != 4) {
            return false;
        }
        if (!(objects[0] instanceof String) || !(objects[1] instanceof String) || 
                !(objects[2] instanceof String) || !(objects[3] instanceof String)) {
            return false;
        }
        publicUserDAO.create(new PublicUser((String) objects[0], (String) objects[1], (String) objects[2], (String) objects[3]));
        return true;
    }

    public ArrayList<PublicUser> GetAllPublicUsers() {
        return new ArrayList<>(publicUserDAO.findall());
    }

}
