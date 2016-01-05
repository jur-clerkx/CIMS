/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Situational_Awareness.MGR;

import Field_Operations.DAO.TaskDAO;
import Field_Operations.DAO.TaskDAOImpl;
import Field_Operations.Domain.Task;
import Global.Domain.User;
import Situational_Awareness.DAO.InformationDAO;
import Situational_Awareness.DAO.InformationDAOImpl;
import Situational_Awareness.Domain.Information;
import java.util.ArrayList;
import javax.persistence.EntityManager;

/**
 *
 * @author Jense
 */
public class InformationMGR {

    private InformationDAO informationDAO;

    public InformationMGR(EntityManager em) {
        informationDAO = new InformationDAOImpl(em);
    }

    public boolean createInformation(User user, Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] info = (Object[]) o;
        if (info.length != 9) {
            return false;
        }
        if (!(info[0] instanceof Task) || !(info[1] instanceof String)
                || !(info[2] instanceof String) || !(info[3] instanceof String)
                || !(info[4] instanceof Integer) || !(info[5] instanceof Integer)
                || !(info[6] instanceof Integer) || !(info[7] instanceof Integer)
                || !(info[7] instanceof String)) {
            return false;
        }
        informationDAO.create(new Information((Task) info[0], (String) info[1], (String) info[2], (String) info[3], (int) info[4], (int) info[5], (int) info[6], (int) info[7], (String) info[8], user));

        return true;
    }

    public boolean removeInformation(Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }
        Information information = informationDAO.find((int) o);
        informationDAO.remove(information);
        return true;
    }

    public Information getInformationById(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }
        return informationDAO.find((int) o);
    }

    public ArrayList<Information> getInformationByTaskId(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }
        return (ArrayList<Information>) informationDAO.findByTaskId((int) o);
    }

    public ArrayList<Information> GetAllInformation() {
        return (ArrayList<Information>) informationDAO.findAll();
    }

    public boolean sendinformation(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] info = (Object[]) o;
        if (info.length != 2) {
            return false;
        }
        //infoId, p_uId, PublicForAll
        if (!(info[0] instanceof Integer) || !(info[1] instanceof Integer)) {
            return false;
        }

        int i = (int) info[1];
        if (i != 1 && i != 0) {
            return false;
        }
        Information information = informationDAO.find((int) info[0]);

        if (information == null) {
            return false;
        }
        information.setPublicInfo(i);
        return true;
    }

    public Object GetAllPublicInformation(int userId) {
        return (ArrayList<Information>) informationDAO.findAllPublicInformation(userId);
    }
}
