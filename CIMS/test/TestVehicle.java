/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Field_Operations.Vehicle;
import Network.User;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author sebas
 */
public class TestVehicle {

    Vehicle vehicle;
    User serviceUser;

    public TestVehicle() {
    }

    @Before
    public void setUp() {
        serviceUser = new User(1, "user1", "lastname", "Male",
                "Slave", "Police", "12-9-1980", 2);
    }

    @Test
    public void TestValidConstructor() {
        vehicle = new Vehicle(1, "Ambulance", "xxxxx", "Good", serviceUser, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidIDConstructor() {
        vehicle = new Vehicle(0, "Ambulance", "xxxxx", "Good", serviceUser, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestNullNameConstructor() {
        vehicle = new Vehicle(1, null, "xxxxx", "Good", serviceUser, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestLongNameConstructor() {
        vehicle = new Vehicle(1, "Lorem ipsum dolor sit amet orci aliquam. ef", "xxxxx", "Good", serviceUser, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestNullLicenseConstructor() {
        vehicle = new Vehicle(1, "Ambulance", null, "Good", serviceUser, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestLongLicenseConstructor() {
        vehicle = new Vehicle(1, "Ambulance", "xxxxxxxxxxxxx", "Good", serviceUser, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestNullStateConstructor() {
        vehicle = new Vehicle(1, "Ambulance", "xxxxx", null, serviceUser, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestLongStateConstructor() {
        vehicle = new Vehicle(1, "Ambulance", "xxxxx", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer dictum gravida ipsum ac eleifend. Vestibulum sed maximus mauris. Praesent egestas, tellus in egestas blandit, lectus felis pellentesque mi, at elementum arcu odio quis turpis. Morbi posuere. ", serviceUser, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestNullUserConstructor() {
        vehicle = new Vehicle(1, "Ambulance", "xxxxx", "Good", null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestTypeSmallConstructor() {
        vehicle = new Vehicle(1, "Ambulance", "xxxxx", "Good", serviceUser, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestTypeBigConstructor() {
        vehicle = new Vehicle(1, "Ambulance", "xxxxx", "Good", serviceUser, 6);
    }

}
