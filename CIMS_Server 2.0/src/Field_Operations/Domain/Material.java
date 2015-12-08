/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.Domain;

import Network.User;
import java.io.Serializable;

/**
 *
 * @author sebas
 */
public class Material implements Serializable {

    private int id;
    private String name;
    private int type;

    public Material(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type + ": " + this.name;
    }

    public int getType() {
        return this.type;
    }
}
