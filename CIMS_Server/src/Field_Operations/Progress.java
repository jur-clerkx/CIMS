/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author sebas
 */
public class Progress implements Serializable {

    private int progressID;
    private Network.User user;
    private Task task;
    private String message;

    public Progress(int progressID, Network.User user, Task task, String message) {
        this.progressID = progressID;
        this.user = user;
        this.task = task;
        this.message = message;
    }
}
