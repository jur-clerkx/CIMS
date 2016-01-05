/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.MGR;

import Field_Operations.DAO.ProgressDAO;
import Field_Operations.DAO.ProgressDAOImpl;
import Field_Operations.DAO.TaskDAO;
import Field_Operations.DAO.TaskDAOImpl;
import Field_Operations.Domain.Progress;
import Field_Operations.Domain.Task;
import Global.Domain.PrivateUser;
import javax.persistence.EntityManager;

/**
 *
 * @author Jense
 */
public class ProgressMGR {

    private ProgressDAO progressDAO;
    private TaskDAO taskDAO;

    public ProgressMGR(EntityManager em) {
        progressDAO = new ProgressDAOImpl(em);
        taskDAO = new TaskDAOImpl(em);
    }

    public boolean createProgress(Object[] objects, PrivateUser user) {
        if (!(objects[0] instanceof Integer) || !(objects[1] instanceof String)) {
            return false;
        }

        Task task = taskDAO.find((int) objects[0]);
        if (task == null) {
            return false;
        }
        Progress progress = new Progress(user, task, (String) objects[1]);
        progressDAO.create(progress);
        return true;
    }
}
