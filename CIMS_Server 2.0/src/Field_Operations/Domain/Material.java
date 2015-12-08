/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.Domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Jense Schouten
 */
@Entity
@Table(name = "Material")
@NamedQueries({
    @NamedQuery(name = "Material.count", query = "SELECT m FROM Material AS m"),
    @NamedQuery(name = "Material.getAll", query = "SELECT m FROM Material AS m")
})
public class Material implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int type;

    /**
     * gets id of this material
     *
     * @return int with id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name of this material
     *
     * @return String with name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of this material
     *
     * @return int with type
     */
    public int getType() {
        return this.type;
    }

    /**
     *
     * @param id id of this material
     * @param name name of this material
     * @param type type of this material
     */
    public Material(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type + ": " + this.name;
    }
}