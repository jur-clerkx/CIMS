/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness.DAO;

import Situational_Awareness.Domain.Information;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class InformationDAOImpl implements InformationDAO {

    private EntityManager em;
    public InformationDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public int count() {
        Query q = em.createNamedQuery("Information.count");
        Integer amount = (Integer)q.getSingleResult();
        
        return amount;
    }

    @Override
    public void create(Information i) {
        em.getTransaction().begin();
        em.persist(i);
        em.getTransaction().commit();
    }

    @Override
    public void edit(Information i) {
        em.getTransaction().begin();
        em.persist(i);
        em.getTransaction().commit();
    }

    @Override
    public void remove(Information i) {
        em.getTransaction().begin();
        em.remove(i);
        em.getTransaction().commit();
    }

    @Override
    public Information find(long id) {
        return (Information) em.find(Information.class, id);
    }

    @Override
    public List<Information> findAll() {
        Query q = em.createNamedQuery("Information.getAll", Information.class);
        List<Information> infos = q.getResultList();
        
        return infos;
    }
    
}
