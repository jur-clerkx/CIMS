/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations;

import Network.User;
import java.io.Serializable;

/**
 *
 * @author sebas
 */
public class Vehicle extends Material implements Serializable {

    private String license;
    private int Type;

    public Vehicle(int vehicleID, String name, String license, String state, User availability, int type) {
        super(vehicleID, name, state, availability, type);
        this.license = license;
        this.Type = type;
    }

    public int getCarType() {
        return Type;
    }

}
