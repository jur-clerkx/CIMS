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

    /**
     * Constructs a Material object
     *
     * @param materialID Greater than 0
     * @param name Not longer than 40 characters or null
     * @param state Not longer than 255 characters or null
     * @param availability Not null
     * @param type 1 to 5
     */
    public Material(int materialID, String name, String state, User availability, int type) {
        if (materialID > 0 && (name != null && name.length() <= 40) && (state != null && state.length() <= 255) && availability != null && (type > 0 && type < 6)) {
            this.materialID = materialID;
            this.name = name;
            this.state = state;
            this.availability = availability;
            this.type = type;
        } else {
            throw new IllegalArgumentException("Make sure you fill in every field.");
        }
    }

    /**
     * Changes the state of material
     *
     * @param state Not longer than 255 characters or null
     */
    public void changeState(String state) {
        if (state != null && state.length() <= 255) {
            this.state = state;
        } else {
            throw new IllegalArgumentException("The state of a material can't be longer than 255 characters or null.");
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
