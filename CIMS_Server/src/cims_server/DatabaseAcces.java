/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cims_server;

/**
 *
 * @author Jense
 */
public interface DatabaseAcces {
    public boolean connected();
    public void executeQuery();
    public void executeNonQuery();
    
    
}
