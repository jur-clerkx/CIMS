/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Field_Operations.Progress;
import Field_Operations.Task;
import Field_Operations.Unit;
import Network.User;
import java.util.Iterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebas
 */
public class TestTask {

    Task task;
    Task validTask;
    User serviceUser;

    public TestTask() {

    }

    @Before
    public void setUp() {
        validTask = new Task(1, "Task1", "High", "Open", "Eindhoven", "DONER");

        serviceUser = new User(1, "user1", "lastname", "Male",
                "Slave", "Police", "12-9-1980", 2);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void TestValidConstructor() {
        task = new Task(2, "Task2", "Low", "Gesloten", "Eindhoven", "Help de kat");
        assertNotNull(task);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidIDConstructor() {
        task = new Task(0, "Task2", "Low", "Gesloten", "Eindhoven", "Help de kat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidNullNameConstructor() {
        task = new Task(2, null, "Low", "Gesloten", "Eindhoven", "Help de kat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidLongNameConstructor() {
        task = new Task(2, "Lorem ipsum dolor sit amet orci aliquam. haha", "Low", "Gesloten", "Eindhoven", "Help de kat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidUrgencyConstructor() {
        task = new Task(2, "Task2", "blabla", "Gesloten", "Eindhoven", "Help de kat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidNullLocationConstructor() {
        task = new Task(2, "Task2", "Low", "Gesloten", null, "Help de kat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidLongLocationConstructor() {
        String taskLocation = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";

        task = new Task(2, "Task2", "Low", "Gesloten", taskLocation, "Help de kat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidNullDescriptionConstructor() {
        String taskDescription = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";

        task = new Task(2, "Task2", "Low", "Gesloten", "Eindhoven", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidLongDescriptionConstructor() {
        String taskDescription = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";

        task = new Task(2, "Task2", "Low", "Gesloten", "Eindhoven", taskDescription);
    }

    @Test
    public void TestOperateAcceptance() {
        Task newTask = validTask;
        if (newTask.isAccepted() == false) {
            newTask.operateAcceptance();
            assertTrue(newTask.isAccepted());
        } 
        if (newTask.isAccepted() == true) {
            newTask.operateAcceptance();
            assertFalse(newTask.isAccepted());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestOperateInvalidNullStatus() {
        Task newTask = validTask;
        newTask.operateStatus(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestOperateInvalidLongStatus() {
        Task newTask = validTask;
        String taskStatus = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ante libero, cursus in vulputate quis, dictum ut diam. Pellentesque scelerisque facilisis enim, mattis finibus leo accumsan non. Aliquam pellentesque nisl sed sollicitudin vehicula. In hac sed. ";
        newTask.operateStatus(taskStatus);
    }

    @Test
    public void TestOperateValidStatus() {
        Task newTask = validTask;
        String taskStatus = "New task status!";
        newTask.operateStatus(taskStatus);

        assertEquals(taskStatus, newTask.getStatus()); // Expected , Actual
    }

    @Test
    public void TestUpdateValidProgress() {
        Task newTask = validTask;
        Progress progress = new Progress(1, serviceUser, newTask, "In Progress");
        newTask.updateProgress(progress);

        Iterator<Progress> itrProgress = newTask.getProgressList().iterator();
        boolean added = false;
        while (itrProgress.hasNext()) {
            if (itrProgress.next().equals(progress)) {
                added = true;
            }
        }
        assertTrue(added);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestUpdateNullProgress() {
        Task newTask = validTask;

        newTask.updateProgress(null);
    }

    @Test
    public void TestAddValidUnit() {
        Unit unit1 = new Unit(1, "Naam", "The Beach Boys", "Avond");
        Task newTask = validTask;
        newTask.addUnit(unit1);

        Iterator<Unit> itrUnit = newTask.getUnits().iterator();
        boolean added = false;
        while (itrUnit.hasNext()) {
            if (itrUnit.next().equals(unit1)) {
                added = true;
            }
        }
        assertTrue(added);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestAddInvalidUnit() {
        Unit unit1 = new Unit(1, "Naam", "The Beach Boys", "Avond");
        Task newTask = validTask;
        newTask.addUnit(unit1);
        newTask.addUnit(unit1);
    }

    @Test
    public void TestDelValidUnit() {
        Task newTask = validTask;
        Unit unit1 = new Unit(1, "Naam", "The Beach Boys", "Avond");
        Unit unit2 = new Unit(2, "2", "DreamTeam", "Middag");
        newTask.addUnit(unit1);
        newTask.addUnit(unit2);

        newTask.delUnit(unit1);
        Iterator<Unit> itrUnit = newTask.getUnits().iterator();
        boolean deleted = true;
        while (itrUnit.hasNext()) {
            if (itrUnit.next().equals(unit1)) {
                deleted = false;
            }
        }
        assertTrue(deleted);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestDelInvalidUnit() {
        Task newTask = validTask;
        Unit unit1 = new Unit(1, "Naam", "The Beach Boys", "Avond");

        newTask.delUnit(unit1);

    }

}
