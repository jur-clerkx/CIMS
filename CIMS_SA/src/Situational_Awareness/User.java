/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;

import java.io.Serializable;

/**
 *
 * @author Jense Schouten
 */
public class User implements Serializable {

    private int user_ID;
    private String firstname;
    private String lastname;
    private String gender;
    private String rank;
    private String sector;
    private String dateofbirth;
    private boolean authorized;
    private int securityLevel;

    /**
     * Constructor of this class
     */
    public User() {
        this.authorized = false;
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
        this.authorized = true;
        this.user_ID = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.rank = rank;
        this.sector = sector;
        this.dateofbirth = dateofbirth;
        this.securityLevel = securityLevel;
    }

    public int getUser_ID() {
        return this.user_ID;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
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

    public boolean authorized() {
        return this.authorized;
    }

    public int getSecurityLevel() {
        return this.securityLevel;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
