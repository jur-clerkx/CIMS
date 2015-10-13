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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        validUnit = new Unit(1,"Naam", "The Beach Boys", "Avond");
        serviceUser = new User(1, "user1", "lastname", "Male",
            "Slave", "Police", "12-9-1980", 2);
        validVehicle = new Vehicle(1, "Ambulance", "xxxxxxxx", "Good", serviceUser, 1);
        validMaterial = new Material(1, "Material1", "Goed", serviceUser, 1);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void TestValidConstructor() {
        unit = new Unit(2,"2deUnit", "DreamTeam", "Middag");
        assertNotNull(unit);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidIDConstructor() {
        unit = new Unit(0,"2deUnit", "DreamTeam", "Middag");
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidNullNameConstructor() {
        unit = new Unit(1,null, "DreamTeam", "Middag");
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidLongNameConstructor() {
        unit = new Unit(1,"Lorem ipsum dolor sit amet orci aliquam. haha234", "DreamTeam", "Middag");
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidLongDescriptionConstructor() {
        String unitDescription = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";
      
        unit = new Unit(1,"2deUnit", unitDescription, "Middag");
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidNullShift() {
        unit = new Unit(2,"2deUnit", "DreamTeam", null);

    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidLongShift() {
        String unitShift = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";
      
        unit = new Unit(2,"2deUnit", "DreamTeam", unitShift);
    }
    @Test
    public void TestAcceptValidTask() {
        Task newTask = new Task(2, "Task2", "Low", "Gesloten", "Eindhoven", "Help de kat");
        unit.acceptTask(newTask);
        
        boolean added = false;
        Iterator<Task> itrTask = unit.getTasks().iterator();
        while(itrTask.hasNext()) {
            if(itrTask == newTask) {
                added = true;
            }
        }
        assertTrue(added);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestAcceptNullTask() {
        unit.acceptTask(null);
    }
    @Test
    public void TestAddValidUser() {
        User user = serviceUser;
        unit.addUser(user);
        
        boolean added = false;
        Iterator<User> itrUser = unit.getMembers().iterator();
        while(itrUser.hasNext()) {
            if(itrUser == user) {
                added = true;
            }
        }
        assertTrue(added);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestAddNullUser() {
        unit.addUser(null);
    }
    @Test 
    public void TestAddValidVehicle() {
        Vehicle vehicle = validVehicle;
        unit.addVehicle(vehicle);
        
        boolean added = false;
        Iterator<Vehicle> itrVehicle = unit.getVehicles().iterator();
        while(itrVehicle.hasNext()) {
            if(itrVehicle == vehicle) {
                added = true;
            }
        }
                
        assertTrue(added);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestAddNullVehicle() {
        unit.addVehicle(null);
    }
    @Test
    public void TestAddValidMaterial() {
        Material material = validMaterial;
        unit.addMaterial(material);
        
        boolean added = false;
        Iterator<Material> itrMaterial = unit.getMaterials().iterator();
        while(itrMaterial.hasNext()) {
            if(itrMaterial.next() == material) {
                added = true;
            }
        }
        assertTrue(added);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestAddnullMaterial() {
        unit.addMaterial(null);
    }
    @Test
    public void delValidUser() {
        
    }
    
}
