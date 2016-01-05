/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Material;
import java.util.List;
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
     *
     * @param em
     */
    public MaterialDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        Query q = em.createNamedQuery("Material.count", Material.class);
        return (long) q.getSingleResult();

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
    public Material find(long id) {
        return (Material) em.find(Material.class, id);
    }

    @Override
    public List<Material> findAll() {
        Query q = em.createNamedQuery("Material.getAll", Material.class);
        List<Material> materials = q.getResultList();

        return materials;
    }

    @Override
    public List<Material> findAllByType(int type) {
        Query q = em.createNamedQuery("Material.getAllByType", Material.class);
        q.setParameter("typeId", type);
        List<Material> materials = q.getResultList();
        return materials;
    }

}
