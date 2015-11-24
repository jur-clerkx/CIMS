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
public class PublicUser implements Serializable {

    private int user_ID;
    private String firstname;
    private String lastname;
    private String BSNnumber;
    private boolean authorized;

    public String getBSNnumber() {
        return BSNnumber;
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

    public boolean authorized() {
        return this.authorized;
    }

    /**
     * Constructor of this class
     */
    public PublicUser() {
        this.authorized = false;
    }

    public PublicUser(int user_ID, String firstname, String lastname, String BSNnumber) {
        this.user_ID = user_ID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.BSNnumber = BSNnumber;
        this.authorized = true;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
