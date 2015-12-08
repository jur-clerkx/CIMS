/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations.DAO;

import java.util.ArrayList;

/**
 *
 * @author Jense Schouten
 */
public interface Roadmap {

    public int count();

    public void create(Roadmap r);

    public void edit(Roadmap r);

    public void remove(Roadmap r);

    public Roadmap find(int id);

    public ArrayList<Roadmap> findall();
}
