/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Company;

import java.io.File;
import javafx.scene.image.Image;

/**
 *
 * @author Jense
 */
public class Employee {

    private String name;
    private String department;
    private Image image;

    public Employee(String name, String department) {
        this.name = name;
        this.department = department;
        this.image = new Image(new File("Unknown-person.gif").toURI().toString());
    }

    public Employee(String name, String department, Image image) {
        this(name, department);
        this.image = image;
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

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
