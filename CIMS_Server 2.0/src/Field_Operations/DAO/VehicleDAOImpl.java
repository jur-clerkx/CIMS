/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Vehicle;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author sebas
 */
public class VehicleDAOImpl implements VehicleDAO {
    private EntityManager em;
    
    public VehicleDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int count() {
        Query q = em.createNamedQuery("Vehicle.count",Vehicle.class);
        Integer amount = (Integer)q.getSingleResult();
        
        return amount.intValue();
    }

    @Override
    public void create(Vehicle v) {
        em.getTransaction().begin();
        em.persist(v);
        em.getTransaction().commit();
        
    }

    @Override
    public void edit(Vehicle v) {
        em.getTransaction().begin();
        em.merge(v);
        em.getTransaction().commit();
    }

    @Override
    public void remove(Vehicle v) {
        em.getTransaction().begin();
        em.remove(v);
        em.getTransaction().commit();
    }

    @Override
    public Vehicle find(int id) {
        return (Vehicle) em.find(Vehicle.class, id);
    }

    @Override
    public ArrayList<Vehicle> findAll() {
        Query q = em.createNamedQuery("Vehicle.getAll", Vehicle.class);
        ArrayList<Vehicle> vehicles = (ArrayList<Vehicle>) q.getResultList();
        
        return vehicles;
    }
}
