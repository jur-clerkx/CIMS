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
public class Material implements Serializable {

    private int materialID;
    private String name;
    private String state;
    private User availability;
    private int type;

    public Material(int materialID, String name, String state, User availability, int type) {
        this.materialID = materialID;
        this.name = name;
        this.state = state;
        this.availability = availability;
        this.type = type;
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

    @Override
    public String toString() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }
}
