/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Field_Operations.Progress;
import Field_Operations.Task;
import Network.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebas
 */
public class TestProgress {

    Progress progress;
    User serviceUser;
    Task validTask;

    public TestProgress() {
    }

    @Before
    public void setUp() {
        serviceUser = new User(1, "user1", "lastname", "Male",
                "Slave", "Police", "12-9-1980", 2);
        validTask = new Task(1, "Task1", "High", "In Progress", "Eindhoven", "DONER");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestValidProgressConstructor() {
        progress = new Progress(2, serviceUser, validTask, "Doner is besteld en onderweg naar het kantoor");
        assertNotNull(progress);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidIDConstructor() {
        progress = new Progress(0, serviceUser, validTask, "Doner is besteld en onderweg naar het kantoor");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidUserNullConstructor() {
        progress = new Progress(2, null, validTask, "Doner is besteld en onderweg naar het kantoor");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidTaskNullConstructor() {
        progress = new Progress(2, serviceUser, null, "Doner is besteld en onderweg naar het kantoor");
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidMessageNullConstructor() {
        progress = new Progress(2, serviceUser, validTask, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestInvalidMessageLongConstructor() {
        String progressMessage = "1Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque maximus at enim nec sagittis. Maecenas at mi mattis, scelerisque ligula vel, bibendum mi. In id nulla eu magna pharetra convallis. Ut ac viverra tortor. Nullam consequat erat cras amet. ";

        progress = new Progress(2, serviceUser, validTask, progressMessage);
    }
}
