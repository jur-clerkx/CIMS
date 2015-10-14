/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Field_Operations.Material;
import Field_Operations.Task;
import Field_Operations.Unit;
import Field_Operations.Vehicle;
import Network.User;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author sebas
 */
public class TestUnit {

    Unit unit;
    Unit validUnit;
    User serviceUser;
    Vehicle validVehicle;
    Material validMaterial;

    public TestUnit() {
    }

    @Before
    public void setUp() {
        validUnit = new Unit(1, "Naam", "The Beach Boys", "Avond");
        unit = validUnit;
        serviceUser = new User(1, "user1", "lastname", "Male",
                "Slave", "Police", "12-9-1980", 2);
        validVehicle = new Vehicle(1, "Ambulance", "xxxx", "Good", serviceUser, 1);
        validMaterial = new Material(1, "Material1", "Goed", serviceUser, 1);
    }

    @Test
    public void TestValidConstructor() {
        unit = new Unit(2, "2deUnit", "DreamTeam", "Middag");
        assertNotNull(unit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidIDConstructor() {
        unit = new Unit(0, "2deUnit", "DreamTeam", "Middag");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidNullNameConstructor() {
        unit = new Unit(1, null, "DreamTeam", "Middag");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidLongNameConstructor() {
        unit = new Unit(1, "Lorem ipsum dolor sit amet orci aliquam. haha234", "DreamTeam", "Middag");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidLongDescriptionConstructor() {
        String unitDescription = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";

        unit = new Unit(1, "2deUnit", unitDescription, "Middag");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidNullShift() {
        unit = new Unit(2, "2deUnit", "DreamTeam", null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidLongShift() {
        String unitShift = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";

        unit = new Unit(2, "2deUnit", "DreamTeam", unitShift);
    }

    @Test
    public void TestAcceptValidTask() {
        Task newTask = new Task(2, "Task2", "Low", "Gesloten", "Eindhoven", "Help de kat");
        unit.acceptTask(newTask);

        boolean added = false;
        Iterator<Task> itrTask = unit.getTasks().iterator();
        while (itrTask.hasNext()) {
            if (itrTask.next().equals(newTask)) {
                added = true;
            }
        }
        assertTrue(added);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAcceptNullTask() {
        unit.acceptTask(null);
    }

    @Test
    public void TestAddValidUser() {
        User user = serviceUser;
        unit.addUser(user);

        boolean added = false;
        Iterator<User> itrUser = unit.getMembers().iterator();
        while (itrUser.hasNext()) {
            if (itrUser.next().equals(user)) {
                added = true;
            }
        }
        assertTrue(added);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddNullUser() {
        unit.addUser(null);
    }

    @Test
    public void TestAddValidVehicle() {
        Vehicle vehicle = validVehicle;
        unit.addVehicle(vehicle);

        boolean added = false;
        Iterator<Vehicle> itrVehicle = unit.getVehicles().iterator();
        while (itrVehicle.hasNext()) {
            if (itrVehicle.next() == (vehicle)) {
                added = true;
            }
        }

        assertTrue(added);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddNullVehicle() {
        unit.addVehicle(null);
    }

    @Test
    public void TestAddValidMaterial() {
        Material material = validMaterial;
        unit.addMaterial(material);

        boolean added = false;
        Iterator<Material> itrMaterial = unit.getMaterials().iterator();
        while (itrMaterial.hasNext()) {
            if (itrMaterial.next().equals(material)) {
                added = true;
            }
        }
        assertTrue(added);

    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddNullMaterial() {
        unit.addMaterial(null);
    }

    @Test
    public void delValidUser() {
        Unit newUnit = validUnit;
        User user1 = serviceUser;
        User user2 = new User(2, "user2", "user2", "Male",
                "Slave", "Police", "12-9-1980", 2);
        newUnit.addUser(user1);
        newUnit.addUser(user2);

        newUnit.delUser(user1);
        Iterator<User> itrUnit = newUnit.getMembers().iterator();
        boolean deleted = true;
        while (itrUnit.hasNext()) {
            if (itrUnit.next().equals(user1)) {
                deleted = false;
            }
        }
        assertTrue(deleted);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delInvalidUser() {
        Unit newUnit = validUnit;
        User user1 = serviceUser;

        newUnit.delUser(user1);
    }

    @Test
    public void delValidVehicle() {
        Unit newUnit = validUnit;
        Vehicle vehicle1 = validVehicle;
        Vehicle vehicle2 = new Vehicle(1, "Ambulance", "yyyy", "Good", serviceUser, 1);
        newUnit.addVehicle(vehicle1);
        newUnit.addVehicle(vehicle2);

        newUnit.delVehicle(vehicle1);
        Iterator<Vehicle> itrVehicle = newUnit.getVehicles().iterator();
        boolean deleted = true;
        while (itrVehicle.hasNext()) {
            if (itrVehicle.next().equals(vehicle1)) {
                deleted = false;
            }
        }
        assertTrue(deleted);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delInvalidVehicle() {
        Unit newUnit = validUnit;
        Vehicle vehicle = validVehicle;

        newUnit.delVehicle(vehicle);
    }

    @Test
    public void delValidMaterial() {
        Unit newUnit = validUnit;
        Material material1 = validMaterial;
        Material material2 = new Material(2, "Material2", "Slecht", serviceUser, 3);
        newUnit.addMaterial(material1);
        newUnit.addMaterial(material2);

        newUnit.delMaterial(material1);
        Iterator<Material> itrMaterial = newUnit.getMaterials().iterator();
        boolean deleted = true;
        while (itrMaterial.hasNext()) {
            if (itrMaterial.next().equals(material1)) {
                deleted = false;
            }
        }
        assertTrue(deleted);
    }

    @Test(expected = IllegalArgumentException.class)
    public void delInvalidMaterial() {
        Unit newUnit = validUnit;
        Material material = validMaterial;

        newUnit.delMaterial(material);
    }

}
