/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Field_Operations.Unit;
import Field_Operations.User;
import java.util.Date;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebas
 */
public class TestUser {

    User validUser;
    User user;
    Unit validUnit;

    public TestUser() {
    }

    @Before
    public void setUp() {
        validUser = new User("TestVNM", "TestNM", new Date(15, 5, 1992), "M");
        validUnit = new Unit(1, "Naam", "The Beach Boys", "Avond");
    }

    @Test
    public void TestValidConstructor() {
        user = new User("TestVNF", "TestNF", new Date(15, 5, 1995), "F");
        assertNotNull(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestLongFirstnameConstructor() {
        user = new User("Lorem ipsum dolor sit posuere.wdgw", "TestNF", new Date(15, 5, 1995), "M");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestNullFirstnameConstructor() {
        user = new User(null, "TestNF", new Date(15, 5, 1995), "M");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestLongLastnameConstructor() {
        user = new User("TestVNF", "Lorem ipsum dolor sit amet, consectetur cras amet. ewf", new Date(15, 5, 1995), "F");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestNullLastnameConstructor() {
        user = new User("TestVNF", null, new Date(15, 5, 1995), "F");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestNullGenderConstructor() {
        user = new User("TestVNF", "TestNF", new Date(15, 5, 1995), null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void TestLongGenderConstructor() {
        user = new User("TestVNF", "TestNF", new Date(15, 5, 1995), "Male");

    }

    @Test
    public void TestChangeValidSector() {
        User newUser = validUser;
        newUser.changeSector("Police");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestChangeNullSector() {
        User newUser = validUser;
        newUser.changeSector(null);
    }

    @Test
    public void TestChangeValidRank() {
        User newUser = validUser;
        newUser.changeRank("Commander");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestChangeNullRank() {
        User newUser = validUser;
        newUser.changeRank(null);
    }

    @Test
    public void TestAddValidUnit() {
        User newUser = validUser;
        boolean added = false;
        newUser.addUnit(validUnit);

        Iterator<Unit> itrUnit = newUser.getUnits().iterator();
        while (itrUnit.hasNext()) {
            if (itrUnit.equals(newUser)) {
                added = true;
            }
        }
        assertTrue(added);

    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddNullUnit() {
        User newUser = validUser;
        newUser.addUnit(null);
    }

}
