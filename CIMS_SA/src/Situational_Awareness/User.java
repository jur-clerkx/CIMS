/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sebas
 */
public class User implements Serializable {

    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String gender;
    private String password;
    private String rank;
    private String sector;

    public String getSector() {
        return sector;
    }
    private List<Unit> units;

    /**
     *
     * @param firstname Not longer than 30 characters or null
     * @param lastname Not longer than 50 characters or null
     * @param dob
     * @param gender M or F
     */
    public User(String firstname, String lastname, Date dob, String gender) {
        if (firstname != null && firstname.length() < 30 && lastname != null && lastname.length() < 50 && (gender == "M" || gender == "F")) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.dateOfBirth = dob;
            this.gender = gender;
            units = new ArrayList<Unit>();
        } else {
            throw new IllegalArgumentException("Make sure that the firstname isn't longer than 30 characters and the lastname isn't longer than 50 characters.");
        }

    }

    /**
     * Changes the sector where a user works
     *
     * @param newSector not null
     */
    public void changeSector(String newSector) {
        if (newSector != null) {
            this.sector = newSector;
        } else {
            throw new IllegalArgumentException("Fill in the field.");
        }
    }

    /**
     * Changes the rank of a user
     *
     * @param newRank not null
     */
    public void changeRank(String newRank) {
        if (newRank != null) {
            this.rank = rank;
        } else {
            throw new IllegalArgumentException("Fill in the field.");
        }
    }

    /**
     * Adds the user to a unit
     *
     * @param unit not null
     */
    public void addUnit(Unit unit) {
        if (unit != null) {
            this.units.add(unit);
        } else {
            throw new IllegalArgumentException("No unit selected.");
        }

    }

    /**
     * Removes the user from a unit
     *
     * @param unit in units list
     */
    public void delUnit(Unit unit) {
        if (unit != null) {
            if (this.units.contains(unit)) {
                this.units.remove(unit);
            }
        } else {
            throw new IllegalArgumentException("No unit selected.");
        }
    }

    /**
     * Fetches the list of units
     *
     * @return
     */
    public List<Unit> getUnits() {
        return units;
    }
}
