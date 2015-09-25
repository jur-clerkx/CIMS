/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.Field_Operations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sebas
 */
public class User {
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String gender;
    private String password;
    private String rank;
    private String sector;
    private List<Unit> units;
    
    public User(String firstname, String lastname, Date dob, String gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dob;
        this.gender = gender;
        units = new ArrayList<Unit>();
    }
    /**
     * Changes the sector where a user works
     * @param newSector 
     */
    public void changeSector(String newSector) {
        if (newSector != null)
            this.sector = newSector;
    }
    /**
     * Changes the rank of a user
     * @param newRank 
     */
    public void changeRank(String newRank) {
        if (newRank != null)
            this.rank = rank;
    }
    /**
     * Adds the user to a unit
     * @param unit 
     */
    public void addUnit(Unit unit) {
        this.units.add(unit);
    }
    /**
     * Removes the user from a unit
     * @param unit 
     */
    public void delUnit(Unit unit) {
        if(this.units.contains(unit)){
            this.units.remove(unit);
        }
    }
}
