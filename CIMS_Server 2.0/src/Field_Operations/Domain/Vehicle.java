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
@Table(name = "Vehicle")
@NamedQueries({
    @NamedQuery(name = "Vehicle.count", query = "SELECT m FROM Vehicle AS m"),
    @NamedQuery(name = "Vehicle.getAll", query = "SELECT m FROM Vehicle AS m")
})
public class Vehicle extends Material implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String license;
    private int Type;

    public Vehicle(int vehicleID, String name, String license, int type) {
        super(vehicleID, name, type);
        this.license = license;
        this.Type = type;
    }

    public int getCarType() {
        return Type;
    }

}
