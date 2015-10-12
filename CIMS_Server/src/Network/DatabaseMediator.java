/**
 *
 */
package Network;

import Field_Operations.Material;
import Field_Operations.Progress;
import Field_Operations.Task;
import Field_Operations.Unit;
import Field_Operations.Vehicle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Database connection class
 *
 * @author Bas Koch
 */
public class DatabaseMediator {

    private static Connection con = null;

    /**
     * IP: 94.211.145.73:3306 username: CIMS password: dreamteam
     */
    private static String url = "jdbc:mysql://94.211.145.73:3306/CIMS";
    private static String user = "CIMS";
    private static String password = "dreamteam";

    /**
     * Opens the connection to the database.
     */
    private static boolean openConnection() {
        try {
            con = DriverManager.getConnection(url, user, password);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Closes the connection to the database if open.
     */
    private static void closeConnection() {
        try {
            if (!con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Execute query's for request data (select statements)
     *
     * @param query Query to be executed
     * @return A resultSet with the requested data
     * @throws SQLException when request fails
     */
    private static ResultSet executeQuery(String query) throws SQLException {
        Statement statement = con.createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Execute nonquery's for update insert and delete statements
     *
     * @param query Query to be executed
     * @throws SQLException when request fails
     */
    private static void executeNonQuery(String query) throws SQLException {
        Statement statement = con.createStatement();
        statement.executeUpdate(query);
    }

    //<editor-fold defaultstate="collapsed" desc="users">
    /**
     * Check if the user information that is entered is correct.
     *
     * @param username The username, not null or empty.
     * @param password The encrypted password of the user, not null or empty.
     * @return True if information is correct
     */
    public static User checkLogin(String username, String password) {
        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.User WHERE username='" + username + "' AND password='" + password + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                int user_ID = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String gender = rs.getString("gender");
                String rank = rs.getString("rank");
                String sector = rs.getString("sector");
                String dateofbirth = rs.getString("dateOfBirth");
                int securityLevel = rs.getInt("level");

                return new User(user_ID, firstname, lastname, gender, rank, sector, dateofbirth, securityLevel);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return new User();
    }

    public static User getUser(int userID) {
        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.User WHERE id='" + userID + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String gender = rs.getString("gender");
                String rank = rs.getString("rank");
                String sector = rs.getString("sector");
                String dateofbirth = rs.getString("dateOfBirth");
                int securityLevel = rs.getInt("level");

                return new User(userID, firstname, lastname, gender, rank, sector, dateofbirth, securityLevel);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return null;
    }
//</editor-fold>

    public static Task getTask(Object o) {
        if (o instanceof Integer) {
            return null;
        }

        Task t = null;
        int i = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Task where id ='" + i + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                int taskID = rs.getInt("id");
                String description = rs.getString("description");
                String status = rs.getString("status");
                String name = rs.getString("name");
                String location = rs.getString("location");
                String urgency;

                switch (rs.getInt("rank")) {
                    case 1:
                        urgency = "high";
                        break;
                    case 2:
                        urgency = "Medium";
                        break;
                    default:
                        urgency = "low";
                        break;
                }
                t = new Task(taskID, name, urgency, status, location, description);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return t;
    }

    public static Task getTaskLists(Task t) {
        if (openConnection()) {
            try {
                String query = "SELECT unitid FROM CIMS.Task_Unit WHERE taskid='" + t.getTaskID() + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    t.addUnit(getUnit(rs.getInt("id")));
                }

                query = "SELECT id FROM CIMS.Progress WHERE taskid='" + t.getTaskID() + "';";
                rs = executeQuery(query);
                while (rs.next()) {
                    t.updateProgress(getProgress(rs.getInt("id")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return t;
    }

    public static ArrayList<Task> getTaskListByUser(int userID) {
        ArrayList<Task> tlist = new ArrayList<>();
        if (openConnection()) {
            try {
                String query = "SELECT tu.taskid FROM CIMS.Task_Unit tu, CIMS.Unit_Containment uc "
                        + "WHERE tu.unitid = uc.unitid AND uc.`type`= 'U' AND uc.containmentid ='" + userID + "';";
                ResultSet rs = executeQuery(query);
                Task t;
                while (rs.next()) {
                    t = getTask(rs.getInt("id"));
                    tlist.add(getTaskLists(t));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return tlist;
    }

    public static boolean updateTaskStatus(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;

        if (openConnection()) {
            try {
                String query = "UPDATE CIMS.Task SET status='" + objects[1] + "' WHERE id='" + objects[0] + "';";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return true;
    }

    public static ArrayList<Task> getActiveInactiveTasks(Object o) {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!(o instanceof Integer)) {
            return tasks;
        }

        int active = (int) o;

        if (openConnection()) {
            try {
                String query = "SELECT id FROM CIMS.Task WHERE active='" + active + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    tasks.add(getTask(rs.getInt("id")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return tasks;
    }

    public static boolean createTask(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;

        if (objects.length != 4) {
            return false;
        }

        if (openConnection()) {
            try {
                String query = "INSERT INTO `CIMS`.`Task` (`description`, `status`, `name`, `urgency`, `location`, `active`) "
                        + "VALUES ('" + objects[0] + "', 'Unassigned', '" + objects[1] + "', '" + objects[2] + "', '" + objects[3] + "', '1');';";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return true;
    }

    public static boolean removeTask(Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }

        int taskID = (Integer) o;

        if (openConnection()) {
            try {
                String query = "DELETE FROM CIMS.Task_Unit WHERE taskid='" + taskID + "';";
                executeNonQuery(query);
                query = "DELETE FROM CIMS.Task WHERE id='" + taskID + "';";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return true;
    }

    public static boolean assignTask(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;

        if (openConnection()) {
            for (int i = 1; i < objects.length; i++) {
                try {
                    String query = "INSERT INTO CIMS.Task_Unit (unitid, taskid) "
                            + "VALUES ('" + objects[i] + "', '" + objects[0] + "');";
                    ResultSet rs = executeQuery(query);
                    if (rs == null) {
                        query = "INSERT INTO CIMS.Task_Unit (unitid, taskid) "
                                + "VALUES ('" + objects[i] + "', '" + objects[0] + "');";
                        executeNonQuery(query);
                    } else {
                        query = "DELETE FROM CIMS.Task_Unit "
                                + "WHERE unitid='" + objects[0] + "' AND taskid = '" + objects[i] + "';";
                        executeNonQuery(query);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            closeConnection();
        }

        return true;
    }

    public static boolean alterLocationTask(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;

        if (objects.length == 2) {
            return false;
        }

        if (openConnection()) {
            try {
                String query = "UPDATE CIMS.Task SET location='" + objects[1] + "' "
                        + "WHERE `id`='" + objects[0] + "';";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        closeConnection();

        return true;
    }

//<editor-fold defaultstate="collapsed" desc="Units">
    public static Unit getUnit(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        Unit u = null;
        int unitID = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Unit WHERE id='" + unitID + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                String name = rs.getString("name");
                String shift = rs.getString("shift");
                String shiftday = rs.getString("shiftday");
                String description = rs.getString("description");

                u = new Unit(unitID, name, description, shiftday + ", " + shift);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return u;
    }

    public static Unit getUnitLists(Unit u) {
        if (openConnection()) {
            try {
                String query = "SELECT unitid FROM CIMS.Task_Unit WHERE taskid='" + u.getUnitID() + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    switch (rs.getString("type")) {
                        case "T":
                            u.acceptTask(getTask(rs.getInt("containmentid")));
                            break;
                        case "M":
                            u.addMaterial(getMaterial(rs.getInt("containmentid")));
                            break;
                        case "U":
                            u.addUser(getUser(rs.getInt("containmentid")));
                            break;
                        case "V":
                            u.addVehicle(getVehicle(rs.getInt("containmentid")));
                            break;
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return u;
    }

    public static ArrayList<Unit> getActiveInactiveUnits(Object b) {
        if (!(b instanceof Boolean)) {
            return null;
        }

        boolean active = (Boolean) b;
        Set<Integer> hs = new HashSet<>();

        for (Network.Connection c : Server.connections) {
            if (active == c.isOpen()) {
                if (openConnection()) {
                    try {
                        String query = "SELECT unitid FROM CIMS.Task_Unit WHERE unitid='" + c.getuser_ID() + "';";
                        ResultSet rs = executeQuery(query);
                        while (rs.next()) {
                            hs.add(rs.getInt("unitid"));
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    closeConnection();
                }
            }
        }
        ArrayList<Unit> uList = new ArrayList<>();
        for (int i : hs) {
            uList.add(getUnit(i));
        }
        return uList;
    }

    public static boolean disbandUnit(Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }

        int unitID = (Integer) o;

        if (openConnection()) {
            try {
                String query = "DELETE FROM CIMS.Unit_Containment WHERE unitid='" + unitID + "';";
                executeNonQuery(query);
                query = "DELETE FROM CIMS.Unit WHERE id='" + unitID + "';";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return true;
    }

    public static boolean createUnit(Object o) {
        //objects[name, location, specials, PoliceCars, FireTrucks, Healthcars, PolicePersons, FirePersons, HealthPersons] 
        if (!(o instanceof Object[])) {
            return false;
        }
        Object[] ob = (Object[]) o;

        if (ob.length != 8) {
            return false;
        }

        if (openConnection()) {
            try {
                String query = "INSERT INTO CIMS.Unit (name, location) "
                        + "VALUES ('" + ob[0] + "', '" + ob[1] + "');";
                executeNonQuery(query);
                query = "SELECT id FROM CIMS.Unit order by id desc;";
                ResultSet rs = executeQuery(query);
                rs.next();

                int unitID = rs.getInt("id");
                boolean s, cp, cf, ch, pp, pf, ph;

                s = setSpecials(ob[2], unitID);
                cp = setCars(ob[3], "P", unitID);
                cf = setCars(ob[4], "F", unitID);
                ch = setCars(ob[5], "H", unitID);
                pp = setPersons(ob[6], "P", unitID);
                pf = setPersons(ob[7], "F", unitID);
                ph = setPersons(ob[8], "H", unitID);

                if (s && cp && cf && ch && pp && pf && ph) {
                    closeConnection();
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return false;
    }

    private static boolean setSpecials(Object o, int unitID) {
        if (!(o instanceof String)) {
            return false;
        }

        for (char ch : o.toString().toCharArray()) {
            int typeid = (int) ch;
            if (openConnection()) {
                try {
                    String query = "SELECT id FROM CIMS.Material "
                            + "WHERE `type`='" + typeid + "';";
                    ResultSet rs = executeQuery(query);
                    while (rs.next()) {
                        query = "INSERT INTO CIMS.Unit_Containment (unitid, containmentid, type) "
                                + "VALUES ('" + unitID + "', '" + rs.getInt("id") + "', 'M');";
                        executeNonQuery(query);
                    }

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
                closeConnection();
            }
        }
        return true;
    }

    private static boolean setCars(Object o, String type, int unitID) {
        if (!(o instanceof Integer)) {
            return false;
        }

        int amount = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT id FROM CIMS.Vehicle "
                        + "WHERE department = '" + type + "' and state='Ready' LIMIT " + amount + ";";
                ResultSet rs = executeQuery(query);
                int i = 0;
                while (rs.next()) {
                    if (i >= amount) {
                        break;
                    }
                    int id = rs.getInt("id");
                    query = "INSERT INTO CIMS.Unit_Containment (unitid, containmentid, type) "
                            + "VALUES ('" + unitID + "', '" + id + "', 'V');";
                    executeNonQuery(query);

                    query = "UPDATE CIMS.Vehicle SET state='Assigned' "
                            + "WHERE id='" + id + "';";
                    executeNonQuery(query);
                    i++;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            closeConnection();
        }

        return true;
    }

    private static boolean setPersons(Object o, String type, int unitID) {
        if (!(o instanceof Integer)) {
            return false;
        }

        int amount = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT u.id FROM CIMS.User u "
                        + "WHERE u.department = '" + type + "' AND u.id NOT IN ("
                        + "SELECT uc.id  FROM CIMS.Unit_Containment uc) limit " + amount + ";";
                ResultSet rs = executeQuery(query);
                int i = 0;
                while (rs.next()) {
                    if (i >= amount) {
                        break;
                    }
                    int id = rs.getInt("id");
                    query = "INSERT INTO CIMS.Unit_Containment (unitid, containmentid, type) "
                            + "VALUES ('" + unitID + "', '" + id + "', 'U');";
                    executeNonQuery(query);
                    i++;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            closeConnection();
        }
        return true;
    }
    //</editor-fold>

    public static Material getMaterial(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        Material m = null;
        int materialID = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Material WHERE id='" + materialID + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                String name = rs.getString("name");
                String state = rs.getString("state");
                int availibility = rs.getInt("availibility");
                int type = rs.getInt("type");

                m = new Material(materialID, name, state, getUser(availibility), type);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return m;
    }

    public static Vehicle getVehicle(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        Vehicle v = null;
        int vehicleID = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT v.*, vt.* FROM CIMS.Vehicle v, CIMS.Vehicle_Type vt "
                        + "WHERE v.Vehicle_Typeid = vt.id AND id='" + vehicleID + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                String name = rs.getString("name");
                String licence = rs.getString("licence");
                String state = rs.getString("state");
                int availibility = rs.getInt("v.availibility");

                v = new Vehicle(vehicleID, name, licence, state, getUser(availibility), 0);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return v;
    }

    public static Progress getProgress(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        Progress p = null;
        int progressID = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Progress WHERE id='" + progressID + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                int userID = rs.getInt("userid");
                int taskID = rs.getInt("taskid");
                String message = rs.getString("message");

                p = new Progress(progressID, getUser(userID), getTask(taskID), message);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return p;
    }
}
