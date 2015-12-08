/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import Field_Operations.Domain.Roadmap;
import java.util.ArrayList;

/**
 *
 * @author Jense Schouten
 */
public interface RoadmapDAO {

    /**
     * Count the number of Roadmaps in the database.
     * @return number of Roadmaps
     */
    public int count();

    /**
     * Create a new Roadmap and add it to the database.
     * @param r Not null
     */
    public void create(Roadmap r);

    /**
     * Edit an existing Roadmap in the database.
     * @param r Not null
     */
    public void edit(Roadmap r);

    /**
     * Remove an existing Roadmap from the database.
     * @param r Not null
     */
    public void remove(Roadmap r);

    /**
     * Find a Roadmap using it's id.
     * @param id
     * @return Roadmap with @param id
     */
    public Roadmap find(int id);

    /**
     * Find all Roadmaps in the database.
     * @return all roadmaps from the database.
     */
    public ArrayList<Roadmap> findAll();
}
