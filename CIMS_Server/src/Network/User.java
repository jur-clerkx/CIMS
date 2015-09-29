/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Network;

import java.util.Date;
import javax.xml.datatype.DatatypeConstants;

/**
 *
 * @author Jense Schouten
 */
public class User implements Authorization {

    private int user_ID;
    private String firstname;
    private String lastname;
    private String gender;
    private String rank;
    private String sector;
    private Date dateofbirth;
    private boolean authorized;

    public void User() {
        authorized = false;
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

    public Date getDateOfBirth() {
        return this.dateofbirth;
    }

    public boolean authorized() {
        return this.authorized;
    }

    public void setAllData(java.sql.ResultSet rs) {
        this.user_ID = 0;
        this.firstname = "";
        this.lastname = "";
        this.gender = "";
        this.rank = "";
        this.sector = "";
        this.dateofbirth = new java.util.Date();
    }

    @Override
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        java.sql.ResultSet rs = DatabaseMediator.checkLogin(username, password);
        if (rs != null) {
            this.authorized = true;

            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
