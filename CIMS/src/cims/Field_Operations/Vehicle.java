/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.Field_Operations;

/**
 *
 * @author sebas
 */
public class Vehicle extends Material{
    private String license;
    private String type;
    
    public Vehicle(String name, String license, String type) {
        super(name);
        this.license = license;
        this.type = type;
    }
    
}