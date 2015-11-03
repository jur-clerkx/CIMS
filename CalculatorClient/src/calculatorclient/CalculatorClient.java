/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorclient;


import webservice.NegativeNumberException_Exception;
import webservice.WebCalculator;
/**
 *
 * @author Jense
 */
public class CalculatorClient {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NegativeNumberException_Exception {
        // TODO code application logic here
        System.out.println(add(1, 2));
    }

    private static int add(int arg0, int arg1) throws NegativeNumberException_Exception {
        webservice.WebCalculatorService service = new webservice.WebCalculatorService();
        webservice.WebCalculator port = service.getWebCalculatorPort();
        return port.add(arg0, arg1);
    }
    
}
