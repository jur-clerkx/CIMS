/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public User() {
        this.authorized = false;
    }

    public User(boolean authorized, int userid, String firstname, String lastname,
            String gender, String rank, String sector, String dateofbirth) {
        this.authorized = authorized;
        this.user_ID = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.rank = rank;
        this.sector = sector;
        this.dateofbirth = dateofbirth;
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

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
