/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Task;
import java.util.ArrayList;
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
        
        return amount.intValue();
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
    public Task find(int id) {
        return (Task) em.find(Task.class, id);
    }

    @Override
    public ArrayList<Task> findAll() {
        Query q = em.createNamedQuery("Task.getAll", Task.class);
        ArrayList<Task> tasks = (ArrayList<Task>) q.getResultList();
        
        return tasks;
    }

    @Override
    public ArrayList<Task> findAllActive() {
        Query q = em.createNamedQuery("Task.getAllActive", Task.class);
        ArrayList<Task> tasks = (ArrayList<Task>) q.getResultList();
        
        return tasks;
    }

    @Override
    public ArrayList<Task> findAllInactive() {
        Query q = em.createNamedQuery("Task.getAllInActive", Task.class);
        ArrayList<Task> tasks = (ArrayList<Task>) q.getResultList();
        
        return tasks;
    }
    
}
