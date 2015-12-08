/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Progress;
import Field_Operations.Domain.Task;
import java.util.ArrayList;

/**
 *
 * @author Jense Schouten
 */
public interface ProgressDAO {

    /**
     * Count the number of Progresses in the database.
     * @return number of Progresses
     */
    public int count();

    /**
     * Create a new Progress and add it to the database.
     * @param p Not null
     */
    public void create(Progress p);

    /**
     * Edit an existing Progress in the database.
     * @param p Not null
     */
    public void edit(Progress p);

    /**
     * Remove an existing Progress from the database.
     * @param p Not null
     */
    public void remove(Progress p);

    /**
     * Find a Progress using it's id.
     * @param id
     * @return Progress with @param id
     */
    public Progress find(int id);

    /**
     * Find a Progress using a task
     * @param t Not null
     * @return Progress with @param task
     */
    public Progress findByTask(Task t);

     /**
     * Find all Progresses in the database.
     * @return all vehicles from the database.
     */
    public ArrayList<Progress> findAll();

}
