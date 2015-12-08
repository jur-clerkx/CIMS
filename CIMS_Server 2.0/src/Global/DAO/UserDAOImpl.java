/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.DAO;

import Global.Domain.User;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class UserDAOImpl implements UserDAO {
    EntityManager em;
    
    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public int count() {
        Query q = em.createNamedQuery("User.count", User.class);
        Integer amount = (Integer)q.getSingleResult();
        return amount.intValue();
    }
    
}
