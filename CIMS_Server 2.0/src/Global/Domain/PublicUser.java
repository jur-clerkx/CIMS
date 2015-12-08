/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.Domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Jense Schouten
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PublicUser.getAll", query = "SELECT a FROM User AS a")
})
public class PublicUser extends User implements Serializable {

    private String BSNnumber;

    public String getBSNnumber() {
        return BSNnumber;
    }

    /**
     * Constructor for unauthorized public users
     */
    public PublicUser() {
    }

    /**
     * Constructor for authorized users
     *
     * @param id id of this user
     * @param firstname firstname of this user
     * @param lastname lastname of this user
     * @param BSNnumber BSN-number of this user
     * @param password password of this user
     */
    public PublicUser(int id, String firstname, String lastname, String BSNnumber, String password) {
        super(id, firstname, lastname, password);
        this.BSNnumber = BSNnumber;

    }
}
