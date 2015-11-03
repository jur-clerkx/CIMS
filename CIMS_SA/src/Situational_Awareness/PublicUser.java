/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;

/**
 *
 * @author sebas
 */
public class PublicUser {

    private int ID;
    private String bsnNummer;
    private String firstname;
    private String lastname;
    private String password;

    public PublicUser(int ID, String bsnNummer, String firstname, String lastname, String password) {
        if (ID > 0 && bsnNummer != null && bsnNummer.length() < 20 && firstname != null && firstname.length() < 30 && lastname != null && lastname.length() < 50 && password != null && password.length() < 30) {
            this.ID = ID;
            this.bsnNummer = bsnNummer;
            this.firstname = firstname;
            this.lastname = lastname;
            this.password = password;
        } else {
            throw new IllegalArgumentException("PublicUser Exception: A parameter wasn't accepted.");
        }

    }

    public int getID() {
        return ID;
    }

    public String getBsnNummer() {
        return bsnNummer;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    
    
    /**
     * Change the password
     * @param password not null or longer than 30 characters
     * @return True if changed
     */
    public boolean changePassword(String password) {
        if (password != null && password.length() < 30) {
            this.password = password;
            return true;
        }
        return false;
    }

}
