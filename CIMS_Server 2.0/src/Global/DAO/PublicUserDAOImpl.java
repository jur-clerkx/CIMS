/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.DAO;

import Global.Domain.PublicUser;
import Global.Domain.User;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class PublicUserDAOImpl implements PublicUserDAO, UserDAO {

    private EntityManager em;

    /**
     * Constructor for Public User DAO Implementation
     *
     * @param em
     */
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
    public PublicUser find(int id) {
        return (PublicUser) em.find(PublicUser.class, id);
    }

    @Override
    public ArrayList<PublicUser> findall() {
        Query q = em.createNamedQuery("PublicUser.getAllPublicUsers", PublicUser.class);
        ArrayList<PublicUser> puList = (ArrayList<PublicUser>) q.getResultList();

        return puList;
    }

    @Override
    public PublicUser login(String username, String password) {
        Query q = em.createNamedQuery("User.login", User.class);
        q.setParameter("username", username);
        q.setParameter("password", password);
        try {
            if (q.getSingleResult() != null) {
                PublicUser pu = (PublicUser) q.getSingleResult();
                return pu;
            }
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("User.count", User.class);
        Integer amount = (Integer) q.getSingleResult();
        return amount;
    }

}
