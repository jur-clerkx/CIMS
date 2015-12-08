/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.DAO;

import Global.Domain.PublicUser;
import java.util.ArrayList;
import javax.persistence.EntityManager;

/**
 *
 * @author sebas
 */
public class PublicUserDAOImpl implements PublicUserDAO,UserDAO {
    private EntityManager em;
    
    public PublicUserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(PublicUser pu) {
        em.getTransaction().begin();
        em.persist(pu);
        em.getTransaction().commit();
    }

    @Override
    public void edit(PublicUser pu) {
        em.getTransaction().begin();
        em.merge(pu);
        em.getTransaction().commit();
    }

    @Override
    public void remove(PublicUser pu) {
        em.getTransaction().begin();
        em.remove(pu);
        em.getTransaction().commit();
    }

    @Override
    public void find(PublicUser pu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<PublicUser> findall() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
