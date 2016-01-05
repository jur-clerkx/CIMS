/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Task;
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
public class TaskDAOImplTest {
    private TaskDAOImpl instance;
    EntityManager em;
    EntityManagerFactory emf;
    
    
    public TaskDAOImplTest() {
        emf = Persistence.createEntityManagerFactory("CIMS_ServerPU");
        em = emf.createEntityManager();
        instance = new TaskDAOImpl(em);
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of count method, of class TaskDAOImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        int expResult = 0;
        int result = instance.count();
        assertEquals(expResult, result);        
    }

    /**
     * Test of create method, of class TaskDAOImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Task t = new Task("task1", "High", "Open","Eindhoven", "Kom snel de fontys staat in de fik.");
        instance.create(t);
        
        boolean result = instance.findAll().contains(t);
        assertTrue(result);
    }

    /**
     * Test of edit method, of class TaskDAOImpl.
     */
    @Test
    public void testEdit() {
        System.out.println("edit");
        Task t = new Task("task2", "High", "Open","Eindhoven", "Kom snel de fontys staat in de fik.");
        instance.create(t);
        String initialStatus = t.getStatus();
        System.out.println("Task 2 voor Edit: " + t.getId() +" " + t.getStatus());
        t.operateStatus("Gesloten");
        instance.edit(t);
        String editedStatus = t.getStatus();
        System.out.println("Task 2 na Edit: " + t.getId() + " " + t.getStatus());
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(initialStatus, editedStatus);
    }

    /**
     * Test of remove method, of class TaskDAOImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        Task t = instance.find(1L);
        int amount = (int) instance.count();
        instance.remove(t);
        int amountAfterRemove = (int)instance.count();
        assertTrue(amountAfterRemove == amount - 1);
    }

    /**
     * Test of find method, of class TaskDAOImpl.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        long id = 0L;
        Task expResult = null;
        Task result = instance.find(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class TaskDAOImpl.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        List<Task> resultInactive = instance.findAllInactive();
        List<Task> resultActive = instance.findAllActive();
        int amount = resultInactive.size() + resultActive.size();
        List<Task> result = instance.findAll();
        assertEquals(amount, result.size());
    }

    /**
     * Test of findAllActive method, of class TaskDAOImpl.
     */
    @Test
    public void testFindAllActive() {
        System.out.println("findAllActive");
        List<Task> result = instance.findAllActive();
        boolean someInactive = false;
        Task t = new Task("task2", "High", "Active","Eindhoven", "Kom snel de fontys staat in de fik.");
        result.add(t);
        for(Task temp: instance.findAllActive()) {
            if(temp.getStatus().equals("Inactive")) {
                someInactive = true;
            }
        }
        assertTrue(someInactive);
    }

    /**
     * Test of findAllInactive method, of class TaskDAOImpl.
     */
    @Test
    public void testFindAllInactive() {
        System.out.println("findAllInactive");
        List<Task> result = instance.findAllInactive();
        boolean someActive = false;
        Task t = new Task("task3", "High", "Inactive", "Eindhoven", "Kom snel de fontys staat in de fik.");
        result.add(t);
        
        for(Task temp: instance.findAllInactive()) {
            if(temp.getStatus().equals("Active")) {
                someActive = true;
            }
        }
        assertFalse(someActive);
    }
    
}
