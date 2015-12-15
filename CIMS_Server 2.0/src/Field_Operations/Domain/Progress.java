/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.Domain;

import Global.Domain.PrivateUser;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @NamedQuery(name = "Progress.count", query = "SELECT COUNT(p) FROM Progress AS p"),
    @NamedQuery(name = "Progress.getAll", query = "SELECT p FROM Progress AS p"),
    @NamedQuery(name = "Progress.findByTask", query = "SELECT p FROM Progress AS p WHERE p.task = :task")
})
public class Progress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    private PrivateUser user;
    @OneToOne(fetch = FetchType.EAGER)
    private Task task;
    private String message;

    public Progress() {
    }

    /**
     * Gets id of this Progress
     *
     * @return int with id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets private user of this Progress
     *
     * @return PrivateUser with private user
     */
    public PrivateUser getUser() {
        return user;
    }

    /**
     * Gets task of this Progress
     *
     * @return Task with task
     */
    public Task getTask() {
        return task;
    }

    /**
     * Gets message of this Progress
     *
     * @return String with message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Constructor of progress
     *
     * @param user user of this progress
     * @param task task of this progress
     * @param message message of this progress
     */
    public Progress(PrivateUser user, Task task, String message) {
        this.user = user;
        this.task = task;
        this.message = message;
    }
}
