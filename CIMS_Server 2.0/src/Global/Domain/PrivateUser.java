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
    @NamedQuery(name = "PrivateUser.getAll", query = "SELECT a FROM User AS a")
})
public class PrivateUser extends User implements Serializable {

    private String gender;
    private String rank;
    private String sector;
    private String dateofbirth;
    private int securityLevel;

    /**
     * Constructor for a unauthorized private user
     */
    public PrivateUser() {
    }

    /**
     * Constructor for a authorized user
     *
     * @param firstname firstname of this user
     * @param lastname lastname of this user
     * @param gender gender of this user
     * @param rank rank of this user
     * @param sector sector of working
     * @param dateofbirth date of birth of this user
     * @param securityLevel security level of this user
     * @param password password of this user
     */
    public PrivateUser(String firstname, String lastname, String gender,
            String rank, String sector, String dateofbirth, int securityLevel, String password) {
        super(firstname, lastname, password);
        this.gender = gender;
        this.rank = rank;
        this.sector = sector;
        this.dateofbirth = dateofbirth;
        this.securityLevel = securityLevel;
    }

    /**
     * Gets gender of this user.
     *
     * @return String M/V.
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Gets the rank of this user.
     *
     * @return String with rank.
     */
    public String getRank() {
        return this.rank;
    }

    /**
     * Gets the sector of this user.
     *
     * @return String with sector.
     */
    public String getSector() {
        return this.sector;
    }

    /**
     * Gets date of birth of this user.
     *
     * @return String with date of birth.
     */
    public String getDateOfBirth() {
        return this.dateofbirth;
    }

    /**
     * gets the security level of this user.
     *
     * @return int with securitylevel.
     */
    public int getSecurityLevel() {
        return this.securityLevel;
    }
}
