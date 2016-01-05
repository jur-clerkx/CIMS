/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.MGR;

import Field_Operations.DAO.TaskDAO;
import Field_Operations.DAO.TaskDAOImpl;
import Field_Operations.Domain.Task;
import Field_Operations.Domain.Unit;
import Global.Domain.PrivateUser;
import java.util.ArrayList;
import javax.persistence.EntityManager;

/**
 *
 * @author Jense
 */
public class TaskMGR {

    private TaskDAO taskDAO;

    public TaskMGR(EntityManager em) {
        taskDAO = new TaskDAOImpl(em);
    }

    public boolean alterTask(Object o) {
        if (!(o instanceof Task)) {
            return false;
        }
        taskDAO.edit((Task) o);
        return true;
    }

    public boolean removeTask(Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }
        Task task = taskDAO.find((int) o);
        taskDAO.remove(task);
        return true;
    }

    public ArrayList getActiveInactiveTasks(Object o) {
        if (!(o instanceof Integer)) {
            return new ArrayList<>();
        }
        if ((int) o == 1) {
            return (ArrayList) taskDAO.findAllActive();
        }
        return (ArrayList) taskDAO.findAllInactive();
    }

    public boolean createTask(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        String[] objects = (String[]) o;

        if (objects.length != 5) {
            return false;
        }
        if (!objects[1].equals("Low") && !objects[1].equals("Medium") && !objects[1].equals("High")) {
            return false;
        }
        taskDAO.create(new Task(objects[0], objects[1], objects[2], objects[3], objects[4]));
        return true;
    }

    public Task findTask(int id) {
        return taskDAO.find(id);
    }

    public boolean editTaskStatus(Object[] objects) {
        if (objects.length != 2) {
            return false;
        }
        Task task = taskDAO.find((int) objects[0]);
        
        if (task == null) {
            return false;
        }
        
        task.setStatus((String) objects[1]);
        taskDAO.edit(task);
        return true;
    }

    public ArrayList<Task> findAllTasksByUserId(int id) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskDAO.findAll()) {
            for (Unit u : task.getUnits()) {
                for (PrivateUser pu : u.getMembers()) {
                    if (pu.getUserId() == id) {
                        tasks.add(task);
                    }
                }
            }
        }
        return tasks;
    }

    public boolean accepOrDenyTasks(Object[] objects) {
        if (objects.length < 2 || objects.length > 3) {
            return false;
        }
        if (!(objects[0] instanceof Integer) || !(objects[1] instanceof Boolean)) {
            return false;
        }

        Task task = taskDAO.find((int) objects[0]);

        if (task == null) {
            return false;
        }

        task.setAccepted((boolean) objects[1]);

        if (!(boolean) objects[1]) {
            if (!(objects[0] instanceof String)) {
                return false;
            }
            task.setReason((String) objects[2]);
        }
        taskDAO.edit(task);
        return true;
    }
}
