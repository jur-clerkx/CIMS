/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.DAO;

import Global.Domain.PrivateUser;
import Global.Domain.PublicUser;
import Global.Domain.User;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class PrivateUserDAOImpl implements PrivateUserDAO,UserDAO {

    private EntityManager em;
    
    /**
     * Constructor for Private User DAO Implementation
     * @param em 
     */
    public PrivateUserDAOImpl(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void create(PrivateUser pu) {
        em.getTransaction().begin();
        em.persist(pu);
        em.getTransaction().commit();
    }

    @Override
    public void edit(PrivateUser pu) {
        em.getTransaction().begin();
        em.merge(pu);
        em.getTransaction().commit();
    }

    @Override
    public void remove(PrivateUser pu) {
        em.getTransaction().begin();
        em.remove(pu);
        em.getTransaction().commit();
    }

    @Override
    public PrivateUser find(int id) {
        return (PrivateUser) em.find(PrivateUser.class, id);
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("User.count", User.class);
        Integer amount = (Integer) q.getSingleResult();
        return amount.intValue();
    }

    @Override
    public ArrayList<PrivateUser> findAll() {
        Query q = em.createNamedQuery("PrivateUser.getAll", PrivateUser.class);
        ArrayList<PrivateUser> puList = (ArrayList<PrivateUser>) q.getResultList();
                
        return puList;
    }

    @Override
    public PrivateUser login(String username, String password) {
        Query q = em.createNamedQuery("User.login", User.class);
        q.setParameter("username", username);
        q.setParameter("password", password);
        
        if (q.getSingleResult() != null) {
            PrivateUser pu = (PrivateUser) q.getSingleResult();
            return pu;
        }
        return null; 
    }
    
}
