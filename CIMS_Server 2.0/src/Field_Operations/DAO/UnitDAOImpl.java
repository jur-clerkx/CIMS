/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Unit;
import Network.Server;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class UnitDAOImpl implements UnitDAO {

    private EntityManager em;
    
    /**
     * Constructor for Unit DAO Implementation
     * @param em 
     */
    public UnitDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public int count() {
        Query q = em.createNamedQuery("Unit.count", Unit.class);
        Integer amount = (Integer) q.getSingleResult();
        
        return amount.intValue();
    }

    @Override
    public void create(Unit u) {
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    @Override
    public void edit(Unit u) {
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
    }

    @Override
    public void remove(Unit u) {
        em.getTransaction().begin();
        em.remove(u);
        em.getTransaction().commit();
    }

    @Override
    public Unit find(long id) {
        return (Unit) em.find(Unit.class, id);
    }

    @Override
    public ArrayList<Unit> findAll() {
        Query q = em.createNamedQuery("Unit.getAll", Unit.class);
        
        return (ArrayList<Unit>)q.getResultList();
    }

    @Override
    public ArrayList<Unit> findAllActive() {
        Set<Unit> hs = new HashSet<>();
        /*for (Network.Connection c : Server.connections) {
            if (c.isOpen()) {
                Query q = em.createNamedQuery("Unit.getAllByUser", Unit.class);
                q.setParameter("id", c.getUserId());
                
                hs.addAll(q.getResultList());
                
            }
        }     */
        
        return new ArrayList<>(hs);
        
    }

    @Override
    public ArrayList<Unit> findAllInactive() {
        Set<Unit> hs = new HashSet<>();
        /*for (Network.Connection c : Server.connections) {
            if (!c.isOpen()) {
                Query q = em.createNamedQuery("Unit.getAllByUser", Unit.class);
                q.setParameter("id", c.getUserId());
                
                hs.addAll(q.getResultList());
                
            }
        }   */  
        
        return new ArrayList<>(hs);
    }
    
}
