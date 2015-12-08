/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Material;
import java.util.ArrayList;

/**
 *
 * @author Jense
 */
public interface MaterialDAO {

    public int count();

    public void create(Material m);

    public void edit(Material m);

    public void remove(Material m);

    public Material find(int id);

    public ArrayList<Material> findall();
}
