/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Material;
import java.util.List;

/**
 *
 * @author Jense
 */
public interface MaterialDAO {

    /**
     * Count the number of Materials in the database.
     *
     * @return number of Materials
     */
    public long count();

    /**
     * Create a new Material and add it to the database.
     *
     * @param m Not null
     */
    public void create(Material m);

    /**
     * Edit an existing Material in the database.
     *
     * @param m Not null
     */
    public void edit(Material m);

    /**
     * Remove an existing Material from the database.
     * @param m Not null
     */
    public void remove(Material m);

    /**
     * Find a Material using it's id.
     * @param id 
     * @return Material with @param id
     */
    public Material find(long id);

    /**
     * Find all Materials in the database.
     * @return all materials from the database.
     */
    public List<Material> findAll();
    
    public List<Material> findAllByType(int type);
}
