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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
