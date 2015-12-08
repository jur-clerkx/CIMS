/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Task;
import java.util.ArrayList;

/**
 *
 * @author Jense Schouten
 */
public interface TaskDAO {

    public int count();

    public void create(Task t);

    public void edit(Task t);

    public void remove(Task t);

    public Task find(int id);

    public ArrayList<Task> findall();

    public ArrayList<Task> findallActive();

    public ArrayList<Task> findallInactive();
}
