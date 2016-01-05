/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Material;
import Field_Operations.Domain.Unit;
import java.util.ArrayList;
import java.util.List;
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
public class UnitDAOImplTest {
    private UnitDAOImpl instance;
    EntityManager em;
    EntityManagerFactory emf;
    
    public UnitDAOImplTest() {
        emf = Persistence.createEntityManagerFactory("CIMS_ServerPU");
        em = emf.createEntityManager();
        instance = new UnitDAOImpl(em);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of count method, of class UnitDAOImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        int expResult = 0;
        int result = instance.count();
        assertEquals(expResult, result);

    }

    /**
     * Test of create method, of class UnitDAOImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Unit u = new Unit("Unit1Name", "Unit1Description");
        
        instance.create(u);
        
        boolean result = instance.findAll().contains(u);
        assertTrue(result);
        
    }

    /**
     * Test of edit method, of class UnitDAOImpl.
     */
    @Test
    public void testEdit() {
        System.out.println("edit");
        Unit u1 = new Unit("Unit1Name", "Unit1Descprition");
        instance.create(u1);
        int amount = u1.getMaterials().size();
        Material m = new Material("Ambulance", 2);
        u1.addMaterial(m);
        instance.edit(u1);
        int editedAmount = u1.getMaterials().size();
        
        assertEquals(amount, editedAmount - 1);
    }

    /**
     * Test of remove method, of class UnitDAOImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Unit u = instance.find(1L);
        int amount = instance.count();
        instance.remove(u);
        int amountAfterRemove = instance.count();
        
        assertTrue(amountAfterRemove == amount - 1);
    }

    /**
     * Test of find method, of class UnitDAOImpl.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        long id = 0L;
        Unit expResult = null;
        Unit result = instance.find(id);
        assertEquals(expResult, result);

    }

    /**
     * Test of findAll method, of class UnitDAOImpl.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        List<Unit> resultInactive = instance.findAllInactive();
        List<Unit> resultActive = instance.findAllActive();
        int amount = resultInactive.size() + resultActive.size();
        List<Unit> results = instance.findAll();
        assertEquals(amount , results.size());
    }

    /**
     * Test of findAllActive method, of class UnitDAOImpl.
     */
    @Test
    public void testFindAllActive() {
        System.out.println("findAllActive");
        
        
    }

    /**
     * Test of findAllInactive method, of class UnitDAOImpl.
     */
    @Test
    public void testFindAllInactive() {
        System.out.println("findAllInactive");
        
    }
    
}
