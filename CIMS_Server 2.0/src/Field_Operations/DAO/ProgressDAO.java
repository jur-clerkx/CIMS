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
public interface ProgressDAO {

    public int count();

    public void create(Process p);

    public void edit(Process p);

    public void remove(Process p);

    public Process find(int id);

    public Process findByTask(Task t);

    public ArrayList<Process> findall();

}
