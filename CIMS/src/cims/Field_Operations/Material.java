/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.Field_Operations;

/**
 *
 * @author sebas
 */
public class Material {
    private String name;
    private String state;
    private int availability; // 1 = available | 0 = unavailable
    private Unit unit;
    
    public Material(String name) {
        this.name = name;
        this.state = "Good";
        this.availability = 1;
        this.unit = null;
    }
    /**
     * Changes the state of material
     * @param state 
     */
    public void changeState(String state) {
        if(state != null) {
            this.state = state;
        }
    }
    /**
     * Changes the availability of material
     */
    public void changeAvailability() {
        if (this.availability == 0) {
            this.availability = 1;
        } else {
            this.availability = 0;
        }
    }
    /**
     * Reserves material to a unit
     * @param unit 
     */
    public void setUnit(Unit unit) {
        if (unit != null) {
            this.unit = unit;
        }
    }
}
