/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.MGR;

import Field_Operations.DAO.RoadmapDAO;
import Field_Operations.DAO.RoadmapDAOImpl;
import Field_Operations.DAO.TaskDAO;
import Field_Operations.DAO.TaskDAOImpl;
import Field_Operations.Domain.Roadmap;
import Field_Operations.Domain.Task;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Jense
 */
public class RoadmapMGR {

    private RoadmapDAO roadmapDAO;
    private TaskDAO taskDAO;

    public RoadmapMGR(EntityManager em) {
        roadmapDAO = new RoadmapDAOImpl(em);
        taskDAO = new TaskDAOImpl(em);
    }

    public boolean createRoadmap(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }
        Object[] objects = (Object[]) o;
        if (objects.length != 2) {
            return false;
        }
        if (!(objects[0] instanceof String) || !(objects[1] instanceof String)) {
            return false;
        }

        roadmapDAO.create(new Roadmap((String) objects[0], (String) objects[1]));
        return true;
    }

    public List<Roadmap> getAllRoadmaps() {
        return  roadmapDAO.findAll();
    }

    public Roadmap getRoadmapByTaskId(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }
        return roadmapDAO.find((int) o);
    }

    public boolean assignRoadmap(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }
        Object[] objects = (Object[]) o;
        if (objects.length != 2) {
            return false;
        }
        if (!(objects[0] instanceof Integer) || !(objects[1] instanceof Integer)) {
            return false;
        }
        
        Task task = taskDAO.find((int) objects[0]);
        task.addRoadmap(roadmapDAO.find((int) objects[1]));
        taskDAO.edit(task);
        return true;
    }

}
