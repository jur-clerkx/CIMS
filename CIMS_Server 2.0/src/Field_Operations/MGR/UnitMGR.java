/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.MGR;

import Field_Operations.DAO.MaterialDAO;
import Field_Operations.DAO.MaterialDAOImpl;
import Field_Operations.DAO.TaskDAO;
import Field_Operations.DAO.TaskDAOImpl;
import Field_Operations.DAO.UnitDAO;
import Field_Operations.DAO.UnitDAOImpl;
import Field_Operations.DAO.VehicleDAO;
import Field_Operations.DAO.VehicleDAOImpl;
import Field_Operations.Domain.Material;
import Field_Operations.Domain.Task;
import Field_Operations.Domain.Unit;
import Field_Operations.Domain.Vehicle;
import Global.DAO.PrivateUserDAO;
import Global.DAO.PrivateUserDAOImpl;
import Global.Domain.PrivateUser;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Jense
 */
public class UnitMGR {

    private UnitDAO unitDAO;
    private TaskDAO taskDAO;
    private MaterialDAO materialDAO;
    private VehicleDAO vehicleDAO;
    private PrivateUserDAO privateUserDAO;

    public UnitMGR(EntityManager em) {
        unitDAO = new UnitDAOImpl(em);
        taskDAO = new TaskDAOImpl(em);
        materialDAO = new MaterialDAOImpl(em);
        vehicleDAO = new VehicleDAOImpl(em);
        privateUserDAO = new PrivateUserDAOImpl(em);        
    }

    public boolean editUnit(Object o) {
        if (!(o instanceof Unit)) {
            return false;
        }
        unitDAO.edit((Unit) o);
        return true;
    }

    public boolean disbandUnit(Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }
        Unit u = unitDAO.find((int) o);
        unitDAO.remove(u);
        return true;
    }

    public ArrayList getActiveInactiveUnits(Object o) {
        if (!(o instanceof Integer)) {
            return new ArrayList<>();
        }
        if ((int) o == 1) {
            return (ArrayList) unitDAO.findAllActive();
        }
        return (ArrayList) unitDAO.findAllInactive();
    }

    public boolean assignUnitToTask(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;
        Task task = taskDAO.find((int) objects[0]);

        if (!(task instanceof Task)) {
            return false;
        }

        for (int i = 1; i < objects.length; i++) {
            Unit unit = unitDAO.find((int) objects[i]);
            task.addUnit(unit);
        }
        return true;
    }
    
    public Unit findUnit(int id) {
        return unitDAO.find(id);
    }

    public ArrayList<Unit> findAllUnitsByUserId(int id) {
        ArrayList<Unit> units = new ArrayList<>();
        List<Unit>uList = unitDAO.findAll();
        if (uList.isEmpty()) {
            return units;
        }
        for (Unit unit : uList) {
            for (PrivateUser pu : unit.getMembers()) {
                if (pu.getUserId() == id) {
                    units.add(unit);
                }
            }
        }
        return units;
    }

    public boolean createUnit(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }
        Object[] ob = (Object[]) o;

        if (ob.length != 9) {
            return false;
        }
        if (!(ob[0] instanceof String) || !(ob[1] instanceof String)) {
            return false;
        }
        Unit unit = new Unit((String) ob[0], (String) ob[1]);
        for (Material material : materialDAO.findAllByType((int) ob[2])) {
            unit.addMaterial(material);
        }

        for (int i = 1; i < 4; i++) {
            ArrayList<Vehicle> vehicles = vehicleDAO.findAllByType(i);
            for (int j = vehicles.size() - (int) ob[2 + i]; j < vehicles.size(); j++) {
                unit.addVehicle(vehicles.get(j));
            }
        }
        
        ArrayList<PrivateUser> fire = privateUserDAO.findAllBySector("Fire");
        int amount = fire.size() - (int) ob[6];
        if (amount < 0) {
            amount = 0;
        }
        for (int j = amount; j < fire.size(); j++) {
            unit.addUser(fire.get(j));
        }
        
        ArrayList<PrivateUser> medical = privateUserDAO.findAllBySector("Medical");
        amount = medical.size() - (int) ob[7];
        if (amount < 0) {
            amount = 0;
        }
        for (int j = amount; j < medical.size(); j++) {
            unit.addUser(medical.get(j));
        }
        
        ArrayList<PrivateUser> police = privateUserDAO.findAllBySector("Police");
        amount = police.size() - (int) ob[8];
        if (amount < 0) {
            amount = 0;
        }
        for (int j = amount; j < police.size(); j++) {
            unit.addUser(police.get(j));
        }
        
        return true;
    }

}
