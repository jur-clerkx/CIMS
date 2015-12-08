/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Material;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jense Schouten
 */
public class MaterialDAOImplTest {

    EntityManager em;
    EntityManagerFactory emf;
    MaterialDAOImpl instance;

    public MaterialDAOImplTest() {
        emf = Persistence.createEntityManagerFactory("CIMS_ServerPU");
        em = emf.createEntityManager();
        instance = new MaterialDAOImpl(em);
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of count method, of class MaterialDAOImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        Material m = new Material("Hamer", 1);
        instance.create(m);
        int expResult = 1;
        int result = instance.count();
        assertEquals(expResult, result);
    }

    /**
     * Test of create method, of class MaterialDAOImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Material m = new Material("henkie", 2);
        instance.create(m);
    }

    /**
     * Test of remove method, of class MaterialDAOImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Material m = new Material("henkie", 2);
        instance.remove(m);
    }

    /**
     * Test of find method, of class MaterialDAOImpl.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        int id = 1;
        Material expResult = null;
        Material result = instance.find(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class MaterialDAOImpl.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        Material m = new Material("Sand", 2);
        instance.create(m);
        int expResult = 2;
        List<Material> result = instance.findAll();
        assertEquals(expResult, result.size());
    }

}
