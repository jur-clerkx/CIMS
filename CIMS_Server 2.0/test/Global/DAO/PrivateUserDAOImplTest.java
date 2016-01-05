/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.DAO;

import Global.Domain.PrivateUser;
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
public class PrivateUserDAOImplTest {
    private PrivateUserDAOImpl instance;
    EntityManager em;
    EntityManagerFactory emf;
    
    private int amountInTestCase;
    
    public PrivateUserDAOImplTest() {
        amountInTestCase = 0;
        emf = Persistence.createEntityManagerFactory("CIMS_ServerPU");
        em = emf.createEntityManager();
        instance = new PrivateUserDAOImpl(em);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class PrivateUserDAOImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        PrivateUser pu = new PrivateUser();
        instance.create(pu);
        amountInTestCase++;
        boolean result = instance.findAll().contains(pu);
        assertTrue(result);
    }

    /**
     * Test of edit method, of class PrivateUserDAOImpl.
     */
    @Test
    public void testEdit() {
        System.out.println("edit");
        PrivateUser pu = null;
        
        instance.edit(pu);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class PrivateUserDAOImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        PrivateUser pu = new PrivateUser("Henk", "van Verre", "M", "Sergant", "FireDepartemnt", "12-dec-1990", 4, "Henkie2");
        instance.create(pu);
        int amountBeforeRemove = instance.count();
        instance.remove(pu);

        int amountAfterRemove = instance.count();
        assertTrue(amountBeforeRemove - 1 == amountAfterRemove);
    }

    /**
     * Test of find method, of class PrivateUserDAOImpl.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        int id = 17;
        PrivateUser expResult = null;
        PrivateUser result = instance.find(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of count method, of class PrivateUserDAOImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        long expResult = instance.count() + 1;
        PrivateUser pu = new PrivateUser("Henk", "van Verre", "M", "Sergant", "FireDepartemnt", "12-dec-1990", 4, "Henkie2");
        instance.create(pu);
        
        long result = instance.count();
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class PrivateUserDAOImpl.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        
        int expResult = amountInTestCase;
        int result = instance.findAll().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of login method, of class PrivateUserDAOImpl.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        String username = "";
        String password = "";
        PrivateUser expResult = null;
        PrivateUser result = instance.login(username, password);
        assertEquals(expResult, result);
        
        username = "Henk";
        password = "Henkie2";
        String genderExpected = "M";
        String genderResult = instance.login(username, password).getGender();
        assertEquals(genderExpected, genderResult);
    }

    /**
     * Test of findAllBySector method, of class PrivateUserDAOImpl.
     */
    @Test
    public void testFindAllBySector() {
        System.out.println("findAllBySector");
        String sector = "Fire";
        ArrayList<PrivateUser> expResult = null;
        ArrayList<PrivateUser> result = instance.findAllBySector(sector);
        assertEquals(expResult, result);
        
        PrivateUser pu = new PrivateUser("Henk", "van Verre", "M", "Sergant", "Fire", "12-dec-1990", 4, "Henkie2");
        instance.create(pu);
        result = instance.findAllBySector(sector);
    }
    
}
