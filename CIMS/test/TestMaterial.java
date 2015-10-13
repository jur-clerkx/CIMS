/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Field_Operations.Material;
import Network.User;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebas
 */
public class TestMaterial {
    Material material;
    Material validMaterial;
    User serviceUser;
    public TestMaterial() {
    }
    
    @Before
    public void setUp() {
        serviceUser = new User(1, "user1", "lastname", "Male",
            "Slave", "Police", "12-9-1980", 2);
        validMaterial = new Material(1, "Material1", "Goed", serviceUser, 1); 
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void TestValidMaterialConstructor() {
        material = new Material(1, "Material1", "Goed", serviceUser, 1); 
        assertNotNull(material);
        material = new Material(Integer.MAX_VALUE, "Material1", "Goed", serviceUser, 5); 
        assertNotNull(material);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidIDConstructor() {
        int ID = 0;
        material = new Material(ID, "Material1", "Goed", serviceUser, 1);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidNameConstructor() {
        String materialName = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";
        material = new Material(1, materialName, "Goed", serviceUser, 2);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidNameNullConstructor() {
        String materialName = null;
        material = new Material(1, materialName, "Goed", serviceUser, 2);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidStateConstructor() {
        String materialState = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";
        material = new Material(1, "user1", materialState, serviceUser, 2);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidStateNullConstructor() {
        String materialState = null;
        material = new Material(1, "user1", materialState, serviceUser, 2);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidUserConstructor() {
        material = new Material(1,"user1", "Goed", null, 2);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidTypeToHighConstructor() {
        material = new Material(1, "user1", "Goed", serviceUser, 6);
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidTypeToLowConstructor() {
        material = new Material(1, "user1", "Goed", serviceUser, 0);
    }
    @Test 
    public void TestValidStateChange() {
        validMaterial.changeState("Slecht");
    }
    @Test (expected=IllegalArgumentException.class)
    public void TestInvalidNullStateChange() {
        validMaterial.changeState(null);
    }
    @Test (expected=IllegalArgumentException.class) 
    public void TestInvalidLongStateChange() {
        String materialState = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";
        validMaterial.changeState(materialState);
    }
}
