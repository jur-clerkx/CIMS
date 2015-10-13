/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Company;

/**
 *
 * @author Jense
 */
public class Employee {

    private String name;
    private String department;

    public Employee(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String fName) {
        this.name = fName;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String fName) {
        this.department = fName;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
