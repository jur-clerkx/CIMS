/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Task;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class TaskDAOImpl implements TaskDAO {

    private EntityManager em;
    
    /**
     * Constructor for Task DAO Implementation
     * @param em 
     */
    public TaskDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public int count() {
        Query q = em.createNamedQuery("Task.count", Task.class);
        Integer amount = (Integer) q.getSingleResult();
        
        return amount;
    }

    @Override
    public void create(Task t) {
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().begin();
    }

    @Override
    public void edit(Task t) {
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
    }

    @Override
    public void remove(Task t) {
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
    }

    @Override
    public Task find(long id) {
        return (Task) em.find(Task.class, id);
    }

    @Override
    public List<Task> findAll() {
        Query q = em.createNamedQuery("Task.getAll", Task.class);
        List<Task> tasks = q.getResultList();
        
        return tasks;
    }

    @Override
    public List<Task> findAllActive() {
        Query q = em.createNamedQuery("Task.getAllActive", Task.class);
        List<Task> tasks = q.getResultList();
        
        return tasks;
    }

    @Override
    public List<Task> findAllInactive() {
        Query q = em.createNamedQuery("Task.getAllInActive", Task.class);
        List<Task> tasks = q.getResultList();
        
        return tasks;
    }    
}
