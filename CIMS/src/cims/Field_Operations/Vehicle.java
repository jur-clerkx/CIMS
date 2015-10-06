/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.Field_Operations;

import Network.User;

/**
 *
 * @author sebas
 */
public class Vehicle extends Material {

    private String license;
    private String type;

    public Vehicle(int vehicleID, String name, String license, String type, String state, User availability) {
        super(vehicleID, name, state, availability);
        this.license = license;
        this.type = type;
    }

}
