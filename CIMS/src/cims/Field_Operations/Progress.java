/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.Field_Operations;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author sebas
 */
public class Progress {
    private String message;
    private Date date;
    
    public Progress (String message) {
        this.message = message;
        this.date = new Date(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth());
    }
}
