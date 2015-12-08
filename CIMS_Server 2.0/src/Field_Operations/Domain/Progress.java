/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.Domain;

import Global.Domain.PrivateUser;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Jense Schouten
 */
@Entity
@Table(name = "Progress")
@NamedQueries({
    @NamedQuery(name = "Progress.count", query = "SELECT m FROM Progress AS m"),
    @NamedQuery(name = "Progress.getAll", query = "SELECT m FROM Progress AS m")
})
public class Progress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private PrivateUser user;
    
    private Task task;
    private String message;

    public int getId() {
        return id;
    }

    public PrivateUser getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }

    public String getMessage() {
        return message;
    }

    public Progress(int id, PrivateUser user, Task task, String message) {
        this.id = id;
        this.user = user;
        this.task = task;
        this.message = message;
    }
}
