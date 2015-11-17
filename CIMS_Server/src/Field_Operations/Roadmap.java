/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Field_Operations;

/**
 *
 * @author Jense
 */
public class Roadmap {
    private int roadmapId;
    private String name;
    private String description;

    public Roadmap(int roadmapId, String name, String description) {
        this.roadmapId = roadmapId;
        this.name = name;
        this.description = description;
    }

    public int getRoadmapId() {
        return roadmapId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

}
