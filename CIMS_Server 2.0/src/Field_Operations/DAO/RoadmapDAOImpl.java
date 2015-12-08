/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Roadmap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class RoadmapDAOImpl implements RoadmapDAO {

    private EntityManager em;
    
    /**
     * Constructor for Roadmap DAO Implementation
     * @param em 
     */
    public RoadmapDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public int count() {
        Query q = em.createNamedQuery("Roadmap.count", Roadmap.class);
        Integer amount = (Integer)q.getSingleResult();
        
        return amount.intValue();
    }

    @Override
    public void create(Roadmap r) {
        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();
    }

    @Override
    public void edit(Roadmap r) {
        em.getTransaction().begin();
        em.merge(r);
        em.getTransaction().commit();
    }

    @Override
    public void remove(Roadmap r) {
        em.getTransaction().begin();
        em.remove(r);
        em.getTransaction().commit();
    }

    @Override
    public Roadmap find(long id) {
        return (Roadmap)em.find(Roadmap.class, id);
    }

    @Override
    public List<Roadmap> findAll() {
        Query q = em.createNamedQuery("Roadmap.getAll", Roadmap.class);
        List<Roadmap> roadmaps = q.getResultList();
        
        return roadmaps;
    }
    
}
