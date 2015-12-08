/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Material;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class MaterialDAOImpl implements MaterialDAO {

    private EntityManager em;
    
    /**
     * Constructor for Material DAO Implementation
     * @param em 
     */
    public MaterialDAOImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public int count() {
        Query q = em.createNamedQuery("Material.count", Material.class);
        Integer amount = (Integer)q.getSingleResult();
        
        return amount.intValue();
    }

    @Override
    public void create(Material m) {
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }

    @Override
    public void edit(Material m) {
        em.getTransaction().begin();
        em.merge(m);
        em.getTransaction().commit();
    }

    @Override
    public void remove(Material m) {
        em.getTransaction().begin();
        em.remove(m);
        em.getTransaction().commit();
    }

    @Override
    public Material find(int id) {
        return (Material) em.find(Material.class, id);
    }

    @Override
    public ArrayList<Material> findAll() {
        Query q = em.createNamedQuery("Material.getAll", Material.class);
        ArrayList<Material> materials = (ArrayList<Material>)q.getResultList();
        
        return materials;
    }
    
}
