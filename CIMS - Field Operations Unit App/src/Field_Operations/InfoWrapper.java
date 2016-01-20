/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations;

import Situational_Awareness.Information;

/**
 * Wrapper class for the information, has other tostring method
 * @author Jur
 */
public class InfoWrapper {
    
    private Information info;
    
    public InfoWrapper(Information info) {
        this.info = info;
    }

    /**
     * 
     * @return The information object in the wrapper
     */
    public Information getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return info.getName();
    }
}
