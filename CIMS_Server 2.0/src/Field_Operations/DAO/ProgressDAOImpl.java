/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Progress;
import Field_Operations.Domain.Task;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class ProgressDAOImpl implements ProgressDAO {

    private EntityManager em;
    
    /**
     * Constructor for Progress DAO Implementation
     * @param em 
     */
    public ProgressDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public int count() {
        Query q = em.createNamedQuery("Progress.count", Progress.class);
        Integer amount = (Integer) q.getSingleResult();
        
        return amount.intValue();
    }

    @Override
    public void create(Progress p) {
        em.getTransaction();
        em.persist(p);
        em.getTransaction().commit();
    }

    @Override
    public void edit(Progress p) {
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

    @Override
    public void remove(Progress p) {
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    @Override
    public Progress find(long id) {
        return (Progress)em.find(Progress.class, id);
    }

    @Override
    public Progress findByTask(Task t) {
        Query q = em.createNamedQuery("Progress.findByTask", Progress.class);
        q.setParameter("task", t);
        
        return (Progress) q.getSingleResult();
    }

    @Override
    public List<Progress> findAll() {
        Query q = em.createNamedQuery("Progress.getAll", Progress.class);
        List<Progress> progresses = (List<Progress>) q.getResultList();
        
        return progresses;
    }
    
}
