/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness;

import Network.PublicUser;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author sebas
 */
public class Progress implements Serializable {

    private int progressID;
    private PublicUser user;
    private Task task;
    private String message;

    /**
     * Constructs a Progress object
     *
     * @param progressID Greater than 0
     * @param user Not null
     * @param task Not null
     * @param message Not longer than 255 characters or null
     */
    public Progress(int progressID, PublicUser user, Task task, String message) {
        if (progressID > 0 && user != null && task != null && message != null) {
            this.progressID = progressID;
            this.user = user;
            this.task = task;
            if (message.length() > 0 && message.length() <= 255) {
                this.message = message;
            } else {
                throw new IllegalArgumentException("Make sure you fill in every field.");
            }
        } else {
            throw new IllegalArgumentException("Make sure you fill in every field.");
        }

    }
}
