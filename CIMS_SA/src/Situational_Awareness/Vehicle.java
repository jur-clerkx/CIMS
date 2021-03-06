/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;

import java.io.Serializable;

/**
 *
 * @author sebas
 */
public class Vehicle extends Material implements Serializable{

    private String license;
    private int Type;
    /**
     * Constructs a vehicle object
     * @param vehicleID Greater than 0
     * @param name Not longer than 40 characters or null
     * @param license Not longer than 8 characters or null
     * @param state Not longer than 255 characters or null
     * @param availability Not null
     * @param type 1 to 5
     */
    public Vehicle(int vehicleID, String name, String license, String state, PublicUser availability, int type) {
        super(vehicleID, name, state, availability, type);
        if (license != null && license.length() < 9 && type > 0 && type < 6) {
            this.license = license;
            this.Type = type;
        } else {
            throw new IllegalArgumentException("License can't be longer than 8 characters.");
        }
        
    }

    public int getCarType() {
       return Type;
    }

}
