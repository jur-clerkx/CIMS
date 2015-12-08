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
@Table(name = "Roadmap")
@NamedQueries({
    @NamedQuery(name = "Roadmap.count", query = "SELECT COUNT(r) FROM Roadmap AS r"),
    @NamedQuery(name = "Roadmap.getAll", query = "SELECT r FROM Roadmap AS r")
})
public class Roadmap implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    public Roadmap() {
    }

    /**
     * Gets id of this Roadmap
     *
     * @return int with id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name of this Roadmap
     *
     * @return String with name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets description of this Roadmap
     *
     * @return String with description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Constructor of this Roadmap
     *
     * @param id id of this Roadmap
     * @param name name of this Roadmap
     * @param description description of this Roadmap
     */
    public Roadmap(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Override for the toString method
     *
     * @return the name of this Roadmap
     */
    @Override
    public String toString() {
        return this.name;
    }
}
