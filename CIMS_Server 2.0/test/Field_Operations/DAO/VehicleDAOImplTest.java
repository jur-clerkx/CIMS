/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Vehicle;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebas
 */
public class VehicleDAOImplTest {
    private VehicleDAOImpl instance;
    EntityManager em;
    EntityManagerFactory emf;
    private int amountInTestCase;
    
    public VehicleDAOImplTest() {
        emf = Persistence.createEntityManagerFactory("CIMS_ServerPU");
        em = emf.createEntityManager();
        instance = new VehicleDAOImpl(em);
        amountInTestCase = 0;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of count method, of class VehicleDAOImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        long expResult = instance.count() + 1;
        Vehicle v = new Vehicle("Ambulance", "ABC-123", 2);
        instance.create(v);
        amountInTestCase++;
        long result = instance.count();
        assertEquals(expResult, result);
    }

    /**
     * Test of create method, of class VehicleDAOImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Vehicle v = new Vehicle("Ambulance", "ABC-123", 2);
        instance.create(v);
        amountInTestCase++;
        boolean result = instance.findAll().contains(v);
        assertTrue(result);
    }

    /**
     * Test of edit method, of class VehicleDAOImpl.
     */
    @Test
    public void testEdit() {
        System.out.println("edit");
        Vehicle v = null;
        VehicleDAOImpl instance = null;
        instance.edit(v);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class VehicleDAOImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Vehicle m = new Vehicle("Ambulance", "ABC-123", 2);
        int amountBeforeRemove = instance.count();
        instance.remove(m);
        amountInTestCase--;
        int amountAfterRemove = instance.count();
        assertTrue(amountBeforeRemove - 1 == amountAfterRemove);
    }

    /**
     * Test of find method, of class VehicleDAOImpl.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        int id = 17;
        Vehicle expResult = null;
        Vehicle result = instance.find(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class VehicleDAOImpl.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        
        int expResult = amountInTestCase;
        int result = instance.findAll().size();
        assertEquals(expResult, result);
        
        
    }

    /**
     * Test of findAllByType method, of class VehicleDAOImpl.
     */
    @Test
    public void testFindAllByType() {
        System.out.println("findAllByType");
        int typeId = 2;
        
        int expResult = amountInTestCase++;
        int result = instance.findAllByType(typeId).size();
        assertEquals(expResult, result);
        
        int typeId2 = 1;
        expResult = 0;
        result = instance.findAllByType(typeId2).size();
        assertEquals(expResult, result);
        
    }
    
}
