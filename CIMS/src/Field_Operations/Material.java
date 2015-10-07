/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations;

import Network.User;

/**
 *
 * @author sebas
 */
public class Material {

    private int materialID;
    private String name;

    public String getName() {
        return name;
    }
    private String state;
    private User availability;

    public Material(int materialID, String name, String state, User availability) {
        this.materialID = materialID;
        this.name = name;
        this.state = state;
        this.availability = availability;
    }

    /**
     * Changes the state of material
     *
     * @param state
     */
    public void changeState(String state) {
        if (state != null) {
            this.state = state;
        }
    }
}
