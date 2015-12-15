/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Progress;
import Field_Operations.Domain.Task;
import Global.Domain.PrivateUser;
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
public class ProgressDAOImplTest {

    EntityManager em;
    EntityManagerFactory emf;
    ProgressDAOImpl instance;
    Field_Operations.DAO.TaskDAO taskDAO;
    Global.DAO.PrivateUserDAO privateUserDAO;

    public ProgressDAOImplTest() {
        emf = Persistence.createEntityManagerFactory("CIMS_ServerPU");
        em = emf.createEntityManager();
        instance = new ProgressDAOImpl(em);
        taskDAO = new Field_Operations.DAO.TaskDAOImpl(em);
        privateUserDAO = new Global.DAO.PrivateUserDAOImpl(em);
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
     * Test of count method, of class ProgressDAOImpl.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        long expResult = instance.count() + 1;
        PrivateUser pu = new PrivateUser("Henk", "van Verre", "M", "Sergant", "FireDepartemnt", "12-dec-1990", 4, "Henkie2");
        privateUserDAO.create(pu);
        Task t = new Task("henk", "High", "Active", "Mierlo", "");
        taskDAO.create(t);
        Progress p = new Progress(pu, t, "Hallo");
        instance.create(p);
        long result = instance.count();
        assertEquals(expResult, result);
    }

    /**
     * Test of create method, of class ProgressDAOImpl.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        PrivateUser pu = new PrivateUser("Henk", "van Verre", "M", "Sergant", "FireDepartemnt", "12-dec-1990", 4, "Henkie2");
        privateUserDAO.create(pu);
        Task t = new Task("henk", "High", "Active", "Mierlo", "");
        taskDAO.create(t);
        Progress p = new Progress(pu, t, "Hallo");
        instance.create(p);
    }

    /**
     * Test of remove method, of class ProgressDAOImpl.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        PrivateUser pu = new PrivateUser("Henk", "van Verre", "M", "Sergant", "FireDepartemnt", "12-dec-1990", 4, "Henkie2");
        Task t = new Task("henk", "High", "Active", "Mierlo", "");
        Progress p = new Progress(pu, t, "Hallo");
        instance.remove(p);
    }

    /**
     * Test of find method, of class ProgressDAOImpl.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        long id = 5;
        Progress expResult = instance.find(id);
        Progress result = instance.find(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class ProgressDAOImpl.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        PrivateUser pu = new PrivateUser("Henk", "van Verre", "M", "Sergant", "FireDepartemnt", "12-dec-1990", 4, "Henkie2");
        privateUserDAO.create(pu);
        Task t = new Task("henk", "High", "Active", "Mierlo", "");
        taskDAO.create(t);
        Progress p = new Progress(pu, t, "Hallo");
        instance.create(p);
        long expResult = instance.count();
        List<Progress> result = instance.findAll();
        assertEquals(expResult, result.size());
    }

}
