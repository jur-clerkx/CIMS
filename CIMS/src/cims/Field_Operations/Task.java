/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims.Field_Operations;

/**
 *
 * @author Jense
 */
public class Task {

    private int taskID;
    private String name;
    private String urgency;
    private String status;
    

    public void task(int taskID) {
        this.taskID = taskID;
    }

    public int getTaskID() {
        return this.taskID;
    }

}
