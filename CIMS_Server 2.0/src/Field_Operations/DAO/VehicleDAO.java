/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Vehicle;
import java.util.ArrayList;

/**
 *
 * @author Jense
 */
public interface VehicleDAO {

    public int count();

    public void create(Vehicle v);

    public void edit(Vehicle v);

    public void remove(Vehicle v);

    public Vehicle find(int id);

    public ArrayList<Vehicle> findall();
}
