/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Roadmap;
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
public class RoadmapDAOImplTest {
    private RoadmapDAOImpl instance;
    EntityManager em;
    EntityManagerFactory emf;
    private int amountInTestCase;
    public RoadmapDAOImplTest() {
        emf = Persistence.createEntityManagerFactory("CIMS_ServerPU");
        em = emf.createEntityManager();
        instance = new RoadmapDAOImpl(em);
        amountInTestCase = 0;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of count method, of class RoadmapDAOImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        long expResult = instance.count() + 1;
        Roadmap r = new Roadmap("To Fontys", "5km straight, Left, Right");
        instance.create(r);
        amountInTestCase++;
        long result = instance.count();
        assertEquals(expResult, result);
    }

    /**
     * Test of create method, of class RoadmapDAOImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Roadmap r = new Roadmap("To Fontys", "5km straight, Left, Right");
        instance.create(r);
        amountInTestCase++;
        boolean result = instance.findAll().contains(r);
        assertTrue(result);
    }

    /**
     * Test of edit method, of class RoadmapDAOImpl.
     */
    @Test
    public void testEdit() {
        System.out.println("edit");
        Roadmap r = null;
        instance.edit(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class RoadmapDAOImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Roadmap m = new Roadmap("To Fontys", "5km straight, Left, Right");
        int amountBeforeRemove = instance.count();
        instance.remove(m);
        amountInTestCase--;
        int amountAfterRemove = instance.count();
        assertTrue(amountBeforeRemove - 1 == amountAfterRemove);
    }

    /**
     * Test of find method, of class RoadmapDAOImpl.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        int id = 17;
        Roadmap expResult = null;
        Roadmap result = instance.find(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class RoadmapDAOImpl.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        
        int expResult = amountInTestCase;
        int result = instance.findAll().size();
        assertEquals(expResult, result);
    }
    
}
