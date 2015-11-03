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
import Situational_Awareness.Information;

/**
 * Database connection class
 *
 * @author Bas Koch
 */
public class DatabaseMediator {

    private Connection con = null;

    /**
     * IP: 94.211.145.73:3306 username: CIMS, password: dreamteam
     */
    private final String url = "jdbc:mysql://94.211.145.73:3306/CIMS";
    private final String user = "CIMS";
    private final String password = "dreamteam";

    /**
     * Opens the connection to the database.
     */
    private boolean openConnection() {
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
    private void closeConnection() {
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
    private ResultSet executeQuery(String query) throws SQLException {
        Statement statement = con.createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Execute nonquery's for update insert and delete statements
     *
     * @param query Query to be executed
     * @throws SQLException when request fails
     */
    private void executeNonQuery(String query) throws SQLException {
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
    public User checkLogin(String username, String password) {
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
                System.out.println("checkLogin: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return new User();
    }

    /**
     * Check if the user information that is entered is correct.
     *
     * @param username The username, not null or empty.
     * @param password The encrypted password of the user, not null or empty.
     * @return True if information is correct
     */
    public PublicUser checkPublicLogin(String username, String password) {
        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Public_User WHERE username='" + username + "' AND password='" + password + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                int user_ID = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String bsn = rs.getString("BSN_Nr");

                return new PublicUser(user_ID, firstname, lastname, bsn);
            } catch (SQLException e) {
                System.out.println("checkLogin: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return new User();
    }

    public User getUser(int userID) {
        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.User WHERE id='" + userID + "';";
                ResultSet rs = executeQuery(query);
                if (rs.next()) {
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    String gender = rs.getString("gender");
                    String rank = rs.getString("rank");
                    String sector = rs.getString("sector");
                    String dateofbirth = rs.getString("dateOfBirth");
                    int securityLevel = rs.getInt("level");

                    return new User(userID, firstname, lastname, gender, rank, sector, dateofbirth, securityLevel);
                }
            } catch (SQLException e) {
                System.out.println("getUser: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return null;
    }

    public PublicUser getPublicUser(int userID) {
        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Public_User WHERE id='" + userID + "';";
                ResultSet rs = executeQuery(query);
                if (rs.next()) {
                    int user_ID = rs.getInt("id");
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    String bsn = rs.getString("BSN_Nr");

                    return new PublicUser(user_ID, firstname, lastname, bsn);
                }
            } catch (SQLException e) {
                System.out.println("getPublicUser: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return null;
    }

    public boolean createPublicUser(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }
        //String, String, Bsnnr, ww

        Object[] progress = (Object[]) o;

        if (!(progress[0] instanceof String) || !(progress[1] instanceof String)
                || !(progress[2] instanceof String) || !(progress[3] instanceof String)) {
            return false;
        }
        String fn = ((String) progress[0]).substring(0, 1).toUpperCase() + ((String) progress[0]).substring(1);
        String ln = ((String) progress[1]).substring(0, 1).toUpperCase() + ((String) progress[1]).substring(1);
        if (openConnection()) {
            try {
                String query = "INSERT INTO `CIMS`.`Public_User` (`BSN_Nr`, `firstname`, `lastname`, `password`, `username`) "
                        + "VALUES ('" + progress[3] + "', '" + fn + "', '" + ln + "','" + progress[4] + "', '" + fn + ln + "');";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("CreateProgress: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    public ArrayList<PublicUser> GetAllPublicUsers() {
        ArrayList< PublicUser> publicUsers = new ArrayList<>();

        if (openConnection()) {
            try {
                String query = "SELECT id FROM CIMS.Public_User;";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    publicUsers.add(getPublicUser(rs.getInt("id")));
                }
            } catch (SQLException e) {
                System.out.println("getTaskLists: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return publicUsers;
    }

//</editor-fold>
    public Task getTask(Object o) {
        if (!(o instanceof Integer)) {
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

                switch (rs.getInt("urgency")) {
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
                System.out.println("getTask: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return t;
    }

    public Task getTaskLists(Task t) {
        if (openConnection()) {
            try {
                String query = "SELECT unitid FROM CIMS.Task_Unit WHERE taskid='" + t.getTaskID() + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    t.addUnit(getUnit(rs.getInt("unitid")));
                }
            } catch (SQLException e) {
                System.out.println("getTaskLists: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        if (openConnection()) {
            try {
                String query = "SELECT id FROM CIMS.Progress WHERE taskid='" + t.getTaskID() + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    t.updateProgress(getProgressById(rs.getInt("id")));
                }
            } catch (SQLException e) {
                System.out.println("getTaskLists: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return t;
    }

    public ArrayList<Task> getTaskListByUser(int userID) {
        ArrayList<Task> tlist = new ArrayList<>();
        if (openConnection()) {
            try {
                String query = "SELECT tu.taskid "
                        + "FROM CIMS.Task t, CIMS.Task_Unit tu, CIMS.Unit_Containment uc "
                        + "WHERE tu.unitid = uc.unitid AND tu.taskid = t.id AND "
                        + "uc.`type`= 'U' AND t.`status` != 'Completed' AND t.`status` != 'Cancelled' "
                        + "AND tu.accepted IS NULL AND uc.containmentid ='" + userID + "';";
                ResultSet rs = executeQuery(query);
                Task t;
                while (rs.next()) {
                    t = getTask(rs.getInt("taskid"));
                    tlist.add(getTaskLists(t));
                }
            } catch (SQLException e) {
                System.out.println("getTaskListByUser: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return tlist;
    }

    public boolean updateTaskStatus(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;
        int active = 1;

        if (objects[1].equals("Completed") || objects[1].equals("Cancelled")) {
            active = 0;
        }

        if (openConnection()) {
            try {
                String query = "UPDATE CIMS.Task SET status='" + objects[1] + "', active='" + active + "' WHERE id='" + objects[0] + "';";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("updateTaskStatus: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    public ArrayList<Task> getActiveInactiveTasks(Object o) {
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
                System.out.println("getActiveInactiveTasks: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return tasks;
    }

    public boolean createTask(Object o) {
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
                System.out.println("createTask: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    public boolean removeTask(Object o) {
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
                System.out.println("removeTask: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    public boolean assignTask(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;

        if (openConnection()) {
            for (int i = 1; i < objects.length; i++) {
                try {
                    String query = "SELECT * FROM CIMS.Task_Unit "
                            + "WHERE unitid='" + objects[i] + "' AND taskid= '" + objects[0] + "';";
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
                    System.out.println("assignTask: " + e.getMessage());
                }
            }
            closeConnection();
        }

        return true;
    }

    public boolean alterLocationTask(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;

        if (objects.length != 2) {
            return false;
        }

        if (openConnection()) {
            try {
                String query = "UPDATE CIMS.Task SET location='" + objects[1] + "' "
                        + "WHERE `id`='" + objects[0] + "';";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("alterLocationTask: " + e.getMessage());
            }
        }
        closeConnection();

        return true;
    }

    public boolean acceptOrDeniedTask(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;

        if (objects.length != 4) {
            return false;
        }
        if (openConnection()) {
            try {
                String query = "UPDATE CIMS.Task_Unit SET accepted='" + objects[2] + "', reason='" + objects[3] + "' "
                        + "WHERE unitid='" + objects[0] + "' AND taskid='" + objects[1] + "';";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("acceptOrDeniedTask: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

//<editor-fold defaultstate="collapsed" desc="Units">
    public Unit getUnit(Object o) {
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
                System.out.println("getUnit: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return u;
    }

    public Unit getUnitLists(Unit u) {
        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Unit_Containment WHERE unitid='" + u.getUnitID() + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    switch (rs.getString("type")) {
                        case "M":
                            u.addMaterial(getMaterial(rs.getInt("containmentid")));
                            break;
                        case "U":
                            u.addUser(getUser(rs.getInt("containmentid")));
                            break;
                        case "V":
                            u.addVehicle(getVehicle(rs.getInt("containmentid")));
                            break;
                        default:
                            u.acceptTask(getTask(rs.getInt("containmentid")));
                            break;
                    }
                }
            } catch (SQLException e) {
                System.out.println("getUnitLists: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return u;
    }

    public ArrayList<Unit> getActiveInactiveUnits(Object b) {
        if (!(b instanceof Boolean)) {
            return null;
        }

        boolean active = (Boolean) b;
        Set<Integer> hs = new HashSet<>();

        for (Network.Connection c : Server.connections) {
            if (active == c.isOpen()) {
                if (openConnection()) {
                    try {
                        String query = "SELECT containmentid FROM CIMS.Unit_Containment "
                                + "WHERE `type`= 'U' AND containmentid='" + c.getUserId() + "';";
                        ResultSet rs = executeQuery(query);
                        while (rs.next()) {
                            hs.add(rs.getInt("containmentid"));
                        }
                    } catch (SQLException e) {
                        System.out.println("getActiveInactiveUnits: " + e.getMessage());
                    } finally {
                        closeConnection();
                    }
                }
            }
        }
        ArrayList<Unit> uList = new ArrayList<>();
        for (int i : hs) {
            uList.add(getUnit(i));
        }
        return uList;
    }

    public boolean disbandUnit(Object o) {
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
                System.out.println("disbandUnit: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    public boolean createUnit(Object o) {
        //objects[name, location, specials, PoliceCars, FireTrucks, Healthcars, PolicePersons, FirePersons, HealthPersons] 
        if (!(o instanceof Object[])) {
            return false;
        }
        Object[] ob = (Object[]) o;

        if (ob.length != 9) {
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
                System.out.println("createUnit: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return false;
    }

    public Unit getUnitListByUser(int userID) {
        Unit u = null;
        if (openConnection()) {
            try {
                String query = "SELECT unitid FROM CIMS.Unit_Containment "
                        + "WHERE `type`= 'U' AND containmentid='" + userID + "';";
                ResultSet rs = executeQuery(query);

                rs.next();
                u = getUnit(rs.getInt("unitid"));
                u = getUnitLists(u);

            } catch (SQLException e) {
                System.out.println("getTaskListByUser: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return u;
    }

    private boolean setSpecials(Object o, int unitID) {
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
                    System.out.println("setSpecials: " + e.getMessage());
                    return false;
                } finally {
                    closeConnection();
                }
            }
        }
        return true;
    }

    private boolean setCars(Object o, String type, int unitID) {
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
                System.out.println("setCars: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    private boolean setPersons(Object o, String type, int unitID) {
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
                System.out.println("setPersons: " + e.getMessage());
                return false;
            } finally {
                closeConnection();
            }
        }
        return true;
    }
    //</editor-fold>

    public Material getMaterial(Object o) {
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
                System.out.println("getMaterial: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return m;
    }

    public Vehicle getVehicle(Object o) {
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
                System.out.println("getVehicle: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return v;
    }

    public Progress getProgressById(Object o) {
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
                System.out.println("getProgress: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return p;
    }

    public Progress getProgressByTask(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        Progress p = null;
        int taskID = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Progress WHERE taskid='" + taskID + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                int progressID = rs.getInt("id");
                int userID = rs.getInt("userid");
                String message = rs.getString("message");

                p = new Progress(progressID, getUser(userID), getTask(taskID), message);
            } catch (SQLException e) {
                System.out.println("getProgress: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return p;
    }

    public boolean createProgress(int userId, Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }
        //id,id message

        Object[] progress = (Object[]) o;

        if (!(progress[0] instanceof Integer)
                || !(progress[1] instanceof String)) {
            return false;
        }

        if (openConnection()) {
            try {
                String query = "INSERT INTO `CIMS`.`Progress` (`userid`, `taskid`, `message`) "
                        + "VALUES ('" + userId + "', '" + progress[0] + "', '" + progress[1] + "');";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("CreateProgress: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    public boolean createInformation(int userId, Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }
        //task,name,description,location,casualties, toxic, danger, impect, image

        Object[] info = (Object[]) o;

        if (!(info[0] instanceof Integer) || !(info[1] instanceof String)
                || !(info[2] instanceof String) || !(info[3] instanceof String)
                || !(info[4] instanceof Integer) || !(info[5] instanceof Integer)
                || !(info[6] instanceof Integer) || !(info[7] instanceof Integer)
                || !(info[8] instanceof String)) {
            return false;
        }

        if (openConnection()) {
            try {
                String query = "`CIMS`.`Information` (`public_UserId`, `taskId`, "
                        + "`name`, `description`, `location`, `casualties`, "
                        + "`toxic`, `danger`, `impect`, `image`) VALUES ('"
                        + userId + "', '" + info[0] + "', '" + info[1] + "', '"
                        + info[2] + "', '" + info[3] + "', '" + info[4] + "', '"
                        + info[5] + "', '" + info[6] + "', '" + info[7] + "', '"
                        + info[8] + "');";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("createInformation: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }

        return true;
    }

    public boolean removeInformation(Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }

        int informationId = (Integer) o;

        if (openConnection()) {
            try {
                String query = "DELETE FROM CIMS.Information WHERE id='" + informationId + "';";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("removeInformation: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    public Information getInformationById(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        Information info = null;
        int infoId = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Information WHERE id='" + infoId + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                Task task = getTask(rs.getInt("taskId"));
                String name = rs.getString("name");
                String description = rs.getString("description");
                String location = rs.getString("location");
                int casualties = rs.getInt("casualties");
                int toxic = rs.getInt("toxic");
                int danger = rs.getInt("danger");
                int impact = rs.getInt("impect");
                String image = rs.getString("image");
                Network.PublicUser pu = getPublicUser(rs.getInt("public_UserId"));

                info = new Information(infoId, task, name, description, location,
                        casualties, toxic, danger, impact, image, pu);
            } catch (SQLException e) {
                System.out.println("getProgress: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return info;
    }

    public Information getInformationByTaskId(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        Information info = null;
        int taskId = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Information WHERE taskId='" + taskId + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                int infoId = rs.getInt("ïd");
                Task task = getTask(taskId);
                String name = rs.getString("name");
                String description = rs.getString("description");
                String location = rs.getString("location");
                int casualties = rs.getInt("casualties");
                int toxic = rs.getInt("toxic");
                int danger = rs.getInt("danger");
                int impact = rs.getInt("impect");
                String image = rs.getString("image");
                Network.PublicUser pu = getPublicUser(rs.getInt("public_UserId"));

                info = new Information(infoId, task, name, description, location,
                        casualties, toxic, danger, impact, image, pu);
            } catch (SQLException e) {
                System.out.println("getProgress: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return info;
    }

    public ArrayList<Information> GetAllInformation() {
        ArrayList<Information> info = new ArrayList<>();

        if (openConnection()) {
            try {
                String query = "SELECT id FROM CIMS.Information;";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    info.add(getInformationById(rs.getInt("id")));
                }
            } catch (SQLException e) {
                System.out.println("GetAllInformation: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return info;
    }
}
