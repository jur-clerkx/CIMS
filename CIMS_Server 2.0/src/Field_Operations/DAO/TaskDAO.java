/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Task;
import java.util.List;

/**
 *
 * @author Jense Schouten
 */
public interface TaskDAO {

    /**
     * Count the number of Tasks in the database.
     * @return number of Tasks
     */
    public int count();

    /**
     * Create a new Task and add it to the database.
     * @param t Not null
     */
    public void create(Task t);

    /**
     * Edit an existing Task in the database.
     * @param t Not null
     */
    public void edit(Task t);

    /**
     * Remove an existing Task from the database.
     * @param t Not null
     */
    public void remove(Task t);

    /**
     * Find a Task using it's id.
     * @param id
     * @return Task with @param id
     */
    public Task find(long id);

    /**
     * Find all Tasks in the database.
     * @return all tasks from the database.
     */
    public List<Task> findAll();

    /**
     * Find all Active Tasks in the database.
     * @return all active tasks from the database.
     */
    public List<Task> findAllActive();

    /**
     * Find all InativeTasks in the database.
     * @return all active tasks from the database.
     */
    public List<Task> findAllInactive();
}
