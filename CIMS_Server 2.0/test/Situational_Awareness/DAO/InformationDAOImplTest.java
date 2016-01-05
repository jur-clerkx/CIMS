/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness.DAO;

import Field_Operations.Domain.Task;
import Global.Domain.User;
import Situational_Awareness.Domain.Information;
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
public class InformationDAOImplTest {
    private InformationDAOImpl instance;
    EntityManager em;
    EntityManagerFactory emf;
    
    private int amountInTestCase;
    public InformationDAOImplTest() {
        amountInTestCase = 0;
        emf = Persistence.createEntityManagerFactory("CIMS_ServerPU");
        em = emf.createEntityManager();
        instance = new InformationDAOImpl(em);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of count method, of class InformationDAOImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        long expResult = instance.count() + 1;
        Task t = new Task("task1", "High", "Open","Eindhoven", "Kom snel de fontys staat in de fik.");
        Information i = new Information(t, null, null, null, 0, 0, 0, 0, null, new User());
        instance.create(i);
        
        long result = instance.count();
        assertEquals(expResult, result);
    }

    /**
     * Test of create method, of class InformationDAOImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Task t = new Task("task1", "High", "Open","Eindhoven", "Kom snel de fontys staat in de fik.");
        Information i = new Information(t, null, null, null, 0, 0, 0, 0, null, new User());
        instance.create(i);
        amountInTestCase++;
        boolean result = instance.findAll().contains(i);
        assertTrue(result);
    }

    /**
     * Test of edit method, of class InformationDAOImpl.
     */
    @Test
    public void testEdit() {
        System.out.println("edit");
        Task t = new Task("task1", "High", "Open","Eindhoven", "Kom snel de fontys staat in de fik.");
        Information i = new Information(t, null, null, null, 0, 0, 0, 0, null, new User());
        instance.create(i);
        int casualtiesBeforeEdit = 0;
        amountInTestCase++;
        for (Information temp : instance.findAll()) {
            if(temp.getTask().getName().equals(t.getName())) {
                casualtiesBeforeEdit = temp.getCasualities();
            }
        }
        int casualties = 2;
        i.addCasualties(casualties);
        instance.edit(i);
        int casualtiesAfterEdit = 0;
        for (Information temp : instance.findAll()) {
            if(temp.getTask().getName().equals(t.getName())) {
                casualtiesAfterEdit = temp.getCasualities();
            }
        }
        Information found = null;
        for (Information temp : instance.findAll()) {
            if(temp.getTask().getName().equals(t.getName())) {
                found = temp;
            }
        }
        int actualCasualties = found.getCasualities();
        assertEquals(casualties, actualCasualties);
        
    }

    /**
     * Test of remove method, of class InformationDAOImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Task t = new Task("task1", "High", "Open","Eindhoven", "Kom snel de fontys staat in de fik.");
        Information i = new Information(t, null, null, null, 0, 0, 0, 0, null, new User());
        instance.create(i);
        int amountBeforeRemove = instance.count();
        instance.remove(i);
        int amountAfterRemove = instance.count();
        assertTrue(amountBeforeRemove - 1 == amountAfterRemove);
    }

    /**
     * Test of find method, of class InformationDAOImpl.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        int id = 17;
        Information expResult = null;
        Information result = instance.find(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class InformationDAOImpl.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        
        int expResult = amountInTestCase;
        int result = instance.findAll().size();
        assertEquals(expResult, result);
    }
    
}
