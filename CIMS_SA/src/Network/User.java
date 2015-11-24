/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import Situational_Awareness.PublicUser;
import java.io.Serializable;

/**
 *
 * @author Jense Schouten
 */
public class User extends PublicUser implements Serializable {

    private String gender;
    private String rank;
    private String sector;
    private String dateofbirth;
    private int securityLevel;

    /**
     * Constructor of this class
     */
    public User() {
    }

    /**
     * secondary constructor of this class
     *
     * @param authorized boolean if user is authorized
     * @param userid id of this user
     * @param firstname firstname of this user
     * @param lastname lastname of this user
     * @param gender gender of this user
     * @param rank rank of this user
     * @param sector sector of working
     * @param dateofbirth date of birth of this user
     */
    public User(int userid, String firstname, String lastname, String gender,
            String rank, String sector, String dateofbirth, int securityLevel) {
        super(userid, firstname, lastname, "");
        this.gender = gender;
        this.rank = rank;
        this.sector = sector;
        this.dateofbirth = dateofbirth;
        this.securityLevel = securityLevel;
    }

    public String getGender() {
        return this.gender;
    }

    public String getRank() {
        return this.rank;
    }

    public String getSector() {
        return this.sector;
    }

    public String getDateOfBirth() {
        return this.dateofbirth;
    }

    public int getSecurityLevel() {
        return this.securityLevel;
    }
}
