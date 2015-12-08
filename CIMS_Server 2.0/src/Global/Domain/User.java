/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Global.Domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Jense Schouten
 */
@Entity @Table(name = "User")
@NamedQueries({
    @NamedQuery(name = "User.count", query = "SELECT a FROM User AS a"),
    @NamedQuery(name = "User.login", query = "SELECT a FROM User AS a WHERE a.username = :username AND a.password = :password")
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    @Transient
    private boolean authorized;

    /**
     * Gets user ID of this user.
     *
     * @return int, the id of this user.
     */
    public int getUserId() {
        return this.id;
    }

    /**
     * Gets firstname of this user.
     *
     * @return string, the firstname of this user.
     */
    public String getFirstname() {
        return this.firstname;
    }

    /**
     * Gets lastname of this user.
     *
     * @return String, the lastname of this user.
     */
    public String getLastname() {
        return this.lastname;
    }

    /**
     * function, checks if user is authorized.
     *
     * @return boolean, true or false if authorized
     */
    public boolean authorized() {
        return this.authorized;
    }

    /**
     * Constructor for unauthorized users.
     */
    public User() {
        this.authorized = false;
    }

    /**
     * Constructor for authorized users
     *
     * @param id Int, id of this user
     * @param firstname String, firstname of this user.
     * @param lastname String, lastname of this user.
     * @param password String, password of this user
     */
    public User(int id, String firstname, String lastname, String password) {
        this.id = id;
        this.username = (firstname + lastname.trim()).toLowerCase();
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.authorized = true;
    }

    /**
     * overrides the ToString method
     *
     * @return String with firstname lastname.
     */
    @Override
    public String toString() {
        return lastname + ", " + firstname;
    }
}
