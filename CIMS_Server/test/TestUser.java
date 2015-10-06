/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Network.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jense
 */
public class TestUser {

    public TestUser() {
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

    @Test
    public void testUserAuthorized() {
        User user = new User(1, "Jense", "Schouten", "M", "Sergant", "Fire department", "02-07-2015", 2);
        assertTrue(user.authorized());
        assertEquals(user.toString(), "Jense Schouten");
        assertEquals(user.getFirstname(), "Jense");
        assertEquals(user.getLastname(), "Schouten");
        assertEquals(user.getDateOfBirth(), "02-07-2015");
        assertEquals(user.getGender(), "M");
        assertEquals(user.getRank(), "Sergant");
        assertEquals(user.getSector(), "Fire department");
        assertEquals(user.getSecurityLevel(), 2);
        assertEquals(user.getUser_ID(), 1);
    }

    @Test
    public void testUserUnauthorized() {
        User user = new User();
        assertFalse(user.authorized());
        assertEquals(user.toString(), "null null");
        assertNull(user.getFirstname());
        assertNull(user.getLastname());
        assertNull(user.getDateOfBirth());
        assertNull(user.getGender());
        assertNull(user.getRank());
        assertNull(user.getSector());
        assertEquals(user.getSecurityLevel(), 0);
        assertEquals(user.getUser_ID(), 0);

    }
}
