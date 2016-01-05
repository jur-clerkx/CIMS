/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.DAO;

import Global.Domain.PublicUser;
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
public class PublicUserDAOImplTest {
    private PublicUserDAOImpl instance;
    EntityManager em;
    EntityManagerFactory emf;
    
    private int amountInTestCase;
    
    public PublicUserDAOImplTest() {
        amountInTestCase = 0;
        emf = Persistence.createEntityManagerFactory("CIMS_ServerPU");
        em = emf.createEntityManager();
        instance = new PublicUserDAOImpl(em);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class PublicUserDAOImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        PublicUser pu = new PublicUser();
        instance.create(pu);
        amountInTestCase++;
        boolean result = instance.findall().contains(pu);
        assertTrue(result);
    }

    /**
     * Test of edit method, of class PublicUserDAOImpl.
     */
    @Test
    public void testEdit() {
        System.out.println("edit");
        PublicUser pu = null;
        PublicUserDAOImpl instance = null;
        instance.edit(pu);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class PublicUserDAOImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        PublicUser pu = new PublicUser("Henk", "van Verre", "1564876", "Henkie23");
        instance.create(pu);
        int amountBeforeRemove = instance.count();
        instance.remove(pu);
        int amountAfterRemove = instance.count();
        assertTrue(amountBeforeRemove - 1 == amountAfterRemove);
    }

    /**
     * Test of find method, of class PublicUserDAOImpl.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        int id = 17;
        PublicUser expResult = null;
        PublicUser result = instance.find(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findall method, of class PublicUserDAOImpl.
     */
    @Test
    public void testFindall() {
        System.out.println("findAll");
        
        int expResult = amountInTestCase;
        int result = instance.findall().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of login method, of class PublicUserDAOImpl.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        String username = "";
        String password = "";
        PublicUser expResult = null;
        PublicUser result = instance.login(username, password);
        assertEquals(expResult, result);
        
        username = "Henk";
        password = "Henkie23";
        String bsnExpected = "M";
        String bsnResult = instance.login(username, password).getBSNnumber();
        assertEquals(bsnExpected, bsnResult);
    }

    /**
     * Test of count method, of class PublicUserDAOImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        long expResult = instance.count() + 1;
        PublicUser pu = new PublicUser("Henk", "van Verre", "1564876", "Henkie23");
        instance.create(pu);
        
        long result = instance.count();
        assertEquals(expResult, result);
    }
    
}
