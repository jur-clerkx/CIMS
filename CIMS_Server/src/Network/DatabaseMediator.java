/**
 *
 */
package Network;

import Field_Operations.Material;
import Field_Operations.Progress;
import Field_Operations.Roadmap;
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
 * @author Jense Schouten
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

    //<editor-fold defaultstate="collapsed" desc="user">
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

    /**
     * gets a user by id
     *
     * @param userID id of the user to be load
     * @return a user
     */
    public User getUserById(int userID) {
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

    /**
     * gets a public user
     *
     * @param userID gets public user by UserId
     * @return a public user
     */
    public PublicUser getPublicUserById(int userID) {
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

    /**
     * Creates a public user
     *
     * @param o a object array, format: //firstname, lastname, Bsn-number and
     * password
     * @return boolean if success or not
     */
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

    /**
     * Gets all public users
     *
     * @return a array list with all public users
     */
    public ArrayList<PublicUser> GetAllPublicUsers() {
        ArrayList< PublicUser> publicUsers = new ArrayList<>();

        if (openConnection()) {
            try {
                String query = "SELECT id FROM CIMS.Public_User;";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    publicUsers.add(getPublicUserById(rs.getInt("id")));
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
    
    //<editor-fold defaultstate="collapsed" desc="Task">
    /**
     * Gets Task by id
     *
     * @param o TaskId of a task
     * @return a task
     */
    public Task getTaskById(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        Task t = null;
        int taskId = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Task where id ='" + taskId + "';";
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

    /**
     * Loads the lists of a task
     *
     * @param t Specific task where we need the lists from
     * @return a compleet task with lists
     */
    public Task getTaskLists(Task t) {
        if (openConnection()) {
            try {
                String query = "SELECT unitid FROM CIMS.Task_Unit WHERE taskid='" + t.getTaskID() + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    t.addUnit(getUnitById(rs.getInt("unitid")));
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

    /**
     * Gets all tasks of a user
     *
     * @param userId userId of a user
     * @return array list with tasks
     */
    public ArrayList<Task> getTaskListByUser(int userId) {
        ArrayList<Task> tlist = new ArrayList<>();
        if (openConnection()) {
            try {
                String query = "SELECT tu.taskid "
                        + "FROM CIMS.Task t, CIMS.Task_Unit tu, CIMS.Unit_Containment uc "
                        + "WHERE tu.unitid = uc.unitid AND tu.taskid = t.id AND "
                        + "uc.`type`= 'U' AND t.`status` != 'Completed' AND t.`status` != 'Cancelled' "
                        + "AND tu.accepted IS NULL AND uc.containmentid ='" + userId + "';";
                ResultSet rs = executeQuery(query);
                Task t;
                while (rs.next()) {
                    t = getTaskById(rs.getInt("taskid"));
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

    /**
     * Updates a status of a task
     *
     * @param o object[] with taskId and string status
     * @return if success or not
     */
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

    /**
     * gets all active and inactive tasks
     *
     * @param o Integer active or inactive
     * @return array list of tasks
     */
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
                    tasks.add(getTaskById(rs.getInt("id")));
                }
            } catch (SQLException e) {
                System.out.println("getActiveInactiveTasks: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return tasks;
    }

    /**
     * Creates a task
     *
     * @param o object array format: `description`, `name`, `urgency` and
     * `location`
     * @return boolean if success or not
     */
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

    /**
     * Removes a task from db
     *
     * @param o TaskId of task to remove
     * @return if success or not
     */
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

    /**
     * Assign a task to multiple unit
     *
     * @param o object array with taskId first records units other records
     * @return if success or not
     */
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
                                + "WHERE unitid='" + objects[i] + "' AND taskid = '" + objects[0] + "';";
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

    /**
     * alter location of a task
     *
     * @param o object array with id of task and location
     * @return boolean if success or not
     */
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

    /**
     * accept or deny a task
     *
     * @param o object array with unitId, taskId, acceptOrDeny and a reason
     * @return boolean if success
     */
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Unit">
    /**
     * gets unit by UnitId
     *
     * @param o UnitId witch unit you want.
     * @return returns a unit item
     */
    public Unit getUnitById(Object o) {
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

    /**
     * Loads all lists of a unit
     *
     * @param u unit where the lists need to be load from
     * @return a compleet unit.
     */
    public Unit getUnitLists(Unit u) {
        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Unit_Containment WHERE unitid='" + u.getUnitID() + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    switch (rs.getString("type")) {
                        case "M":
                            u.addMaterial(getMaterialById(rs.getInt("containmentid")));
                            break;
                        case "U":
                            u.addUser(getUserById(rs.getInt("containmentid")));
                            break;
                        case "V":
                            u.addVehicle(getVehicleById(rs.getInt("containmentid")));
                            break;
                        default:
                            u.acceptTask(getTaskById(rs.getInt("containmentid")));
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

    /**
     * Gets all active units
     *
     * @param b boolean for active or inactive
     * @return a arraylist of units
     */
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
            uList.add(getUnitById(i));
        }
        return uList;
    }

    /**
     * disbands a unit
     *
     * @param o unitId of unit to disband
     * @return if succes or not
     */
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

    /**
     * Creates a unit
     *
     * @param o objectarray with, format: name, location, specials, PoliceCars,
     * FireTrucks, Healthcars, PolicePersons, FirePersons, HealthPersons
     * @return returns if success
     */
    public boolean createUnit(Object o) {
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

    /**
     * gets a specific unit
     *
     * @param o a userId
     * @return a unit
     */
    public Unit getUnitListByUserId(int userId) {
        Unit u = null;
        if (openConnection()) {
            try {
                String query = "SELECT UnitID FROM CIMS.Unit_Containment "
                        + "WHERE `type`= 'U' AND containmentid='" + userId + "';";
                ResultSet rs = executeQuery(query);

                rs.next();
                u = getUnitById(rs.getInt("UnitID"));
                u = getUnitLists(u);

            } catch (SQLException e) {
                System.out.println("getUnitListByUser: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return u;
    }

    /**
     * Sets specialslist of task
     *
     * @param o type of special
     * @param unitID id of the unit the special should be in
     * @return boolean if success.
     */
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

    /**
     * Sets carslist of task
     *
     * @param o int limit off cars to load
     * @param type department of car
     * @param unitID id of the unit the car is in
     * @return boolean if success.
     */
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

    /**
     * Sets personlist of task
     *
     * @param o int limit off persons to load
     * @param type department of person
     * @param unitID id of the unit the person works in
     * @return boolean if success.
     */
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

    //<editor-fold defaultstate="collapsed" desc="Material">
    /**
     * gets progress by materialId
     *
     * @param o materialId witch material you want.
     * @return returns a material item
     */
    public Material getMaterialById(Object o) {
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

                m = new Material(materialID, name, state, getUserById(availibility), type);
            } catch (SQLException e) {
                System.out.println("getMaterial: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return m;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Vehicle">
    /**
     * gets vehicle by VehicleId
     *
     * @param o VehicleId witch vehicle you want.
     * @return returns a vehicle item
     */
    public Vehicle getVehicleById(Object o) {
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

                v = new Vehicle(vehicleID, name, licence, state, getUserById(availibility), 0);
            } catch (SQLException e) {
                System.out.println("getVehicle: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return v;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Progress">
    /**
     * gets progress by ProgressId
     *
     * @param o ProgressId witch progress you want.
     * @return returns a progress item
     */
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

                p = new Progress(progressID, getUserById(userID), getTaskById(taskID), message);
            } catch (SQLException e) {
                System.out.println("getProgress: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return p;
    }

    /**
     * gets progress by taskId
     *
     * @param o taskId witch progress you want.
     * @return returns a progress item
     */
    public ArrayList<Progress> getProgressByTask(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        ArrayList<Progress> progresses = new ArrayList<>();

        int taskID = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Progress WHERE taskid='" + taskID + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                int progressID = rs.getInt("id");
                int userID = rs.getInt("userid");
                String message = rs.getString("message");

                progresses.add(new Progress(progressID, getUserById(userID), getTaskById(taskID), message));
            } catch (SQLException e) {
                System.out.println("getProgress: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return progresses;
    }

    /**
     * Function for creation of progress
     *
     * @param userId id of current user
     * @param o object array with progress, format: taskId, message
     * @return
     */
    public boolean createProgress(int userId, Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Information">
    /**
     * Creates information.
     *
     * @param userId ID of user that is logged on
     * @param o object array with all the information format:
     * name,description,location,casualties, toxic, danger, impect, image
     * @return a boolean true if success false if not.
     */
    public boolean createInformation(int userId, Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] info = (Object[]) o;

        if (!(info[0] instanceof Integer) || !(info[1] instanceof String)
                || !(info[2] instanceof String) || !(info[3] instanceof String)
                || !(info[4] instanceof Integer) || !(info[5] instanceof Integer)
                || !(info[6] instanceof Integer) || !(info[7] instanceof Integer)) {
            return false;
        }

        if (openConnection()) {
            try {
                String query = "`CIMS`.`Information` (`public_UserId`, "
                        + "`name`, `description`, `location`, `casualties`, "
                        + "`toxic`, `danger`, `impect`, `image`) VALUES ('"
                        + userId + "', '" + info[0] + "', '" + info[1] + "', '"
                        + info[2] + "', '" + info[3] + "', '" + info[4] + "', '"
                        + info[5] + "', '" + info[6] + "', '" + info[7] + "');";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("createInformation: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }

        return true;
    }

    /**
     * Deletes information from db
     *
     * @param o param information id
     * @return gives true if success false so not
     */
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

    /**
     * gets information by id
     *
     * @param o object as int request id
     * @return information
     */
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

                Task task = getTaskById(rs.getInt("taskId"));
                String name = rs.getString("name");
                String description = rs.getString("description");
                String location = rs.getString("location");
                int casualties = rs.getInt("casualties");
                int toxic = rs.getInt("toxic");
                int danger = rs.getInt("danger");
                int impact = rs.getInt("impect");
                String image = rs.getString("image");
                Network.PublicUser pu = getPublicUserById(rs.getInt("public_UserId"));

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

    /**
     * Gets information by taskId
     *
     * @param o
     * @return information
     */
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
                Task task = getTaskById(taskId);
                String name = rs.getString("name");
                String description = rs.getString("description");
                String location = rs.getString("location");
                int casualties = rs.getInt("casualties");
                int toxic = rs.getInt("toxic");
                int danger = rs.getInt("danger");
                int impact = rs.getInt("impect");
                String image = rs.getString("image");
                Network.PublicUser pu = getPublicUserById(rs.getInt("public_UserId"));

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

    /**
     * gets all information
     *
     * @return a arraylist of information
     */
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

    /**
     * Makes information for a specific user public
     *
     * @param o object array as an informationId, a public user id and an int if
     * public for all (1) of only me (0).
     * @return
     */
    public boolean sendinformation(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] info = (Object[]) o;
        if (info.length != 3) {
            return false;
        }
        //infoId, p_uId, PublicForAll
        if (!(info[0] instanceof Integer) || !(info[1] instanceof Integer)
                || !(info[2] instanceof Integer)) {
            return false;
        }

        int i = (int) info[2];
        if (i != 1 && i != 0) {
            return false;
        }

        if (openConnection()) {
            try {
                String query = "`CIMS`.`Public_Info` (`informationid`, `public_userid`, `PublicForAll`) "
                        + "VALUES ('" + info[0] + "', '" + info[1] + "', '" + info[2] + "');";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("sendinformation: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    /**
     * gets all public information.
     *
     * @param userId id of logged in user
     * @return arraylist with information
     */
    public ArrayList<Information> GetAllPublicInformation(int userId) {
        ArrayList<Information> info = new ArrayList<>();

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Public_Info WHERE PublicForAll = 1 "
                        + "OR (public_UserId = '" + userId + "' AND PublicForAll = 0);";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    int infoId = rs.getInt("informationid");
                    info.add(getInformationById(infoId));
                }
            } catch (SQLException e) {
                System.out.println("GetAllPublicInformation: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return info;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Roadmaps">
    /**
     *
     * @param o a taskId
     * @return a arraylist with roadmaps
     */
    public ArrayList<Roadmap> getRoadmapByTaskId(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        ArrayList<Roadmap> roadmaps = new ArrayList<>();
        int taskId = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.AssignedRoadmap WHERE taskId='" + taskId + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    int roadmapId = rs.getInt("roadmapid");
                    roadmaps.add(getRoadmapById(roadmapId));
                }
            } catch (SQLException e) {
                System.out.println("getRoadmapByTaskId: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return roadmaps;
    }

    /**
     * gets roadmap by id
     *
     * @param o a specific roadmapId
     * @return a roadmap
     */
    public Roadmap getRoadmapById(Object o) {
        if (!(o instanceof Integer)) {
            return null;
        }

        Roadmap roadmap = null;
        int roadmapId = (Integer) o;

        if (openConnection()) {
            try {
                String query = "SELECT * FROM CIMS.Roadmap WHERE id='" + roadmapId + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                String name = rs.getString("name");
                String description = rs.getString("description");

                roadmap = new Roadmap(roadmapId, name, description);
            } catch (SQLException e) {
                System.out.println("getProgress: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return roadmap;
    }

    /**
     * gives all roadmaps
     *
     * @return all roadmaps
     */
    public ArrayList<Roadmap> getAllRoadmaps() {
        ArrayList<Roadmap> roadmaps = new ArrayList<>();

        if (openConnection()) {
            try {
                String query = "SELECT id FROM CIMS.Roadmap;";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    roadmaps.add(getRoadmapById(rs.getInt("id")));
                }
            } catch (SQLException e) {
                System.out.println("GetAllRoadmaps: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return roadmaps;
    }

    /**
     * Creates a roadmap
     *
     * @param o objectarray with strings "name", "description"
     * @return returns if success
     */
    public boolean createRoadmap(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] roadmap = (Object[]) o;

        //name,description
        if (!(roadmap[0] instanceof String) || !(roadmap[1] instanceof String)) {
            return false;
        }

        if (openConnection()) {
            try {
                String query = "INSERT INTO `CIMS`.`Roadmap` (`name`, `description`) "
                        + "VALUES ('" + roadmap[0] + "', '" + roadmap[1] + "');";
                executeNonQuery(query);
            } catch (SQLException e) {
                System.out.println("createRoadmap: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return true;
    }

    /**
     * function to assign a Roadmap
     *
     * @param o object array value 0 taskId other values RoadmapIds
     * @return boolean if success
     */
    public boolean assignRoadmap(Object o) {
        if (!(o instanceof Object[])) {
            return false;
        }

        Object[] objects = (Object[]) o;

        if (objects[0] instanceof Integer) {
            return false;
        }

        if (openConnection()) {
            for (int i = 1; i < objects.length; i++) {
                try {
                    String query = "SELECT * FROM CIMS.AssignedRoadmap "
                            + "WHERE roadmapid='" + objects[i] + "' AND taskid= '" + objects[0] + "';";
                    ResultSet rs = executeQuery(query);
                    if (rs == null) {
                        query = "INSERT INTO CIMS.AssignedRoadmap (roadmapId, taskid) "
                                + "VALUES ('" + objects[i] + "', '" + objects[0] + "');";
                        executeNonQuery(query);
                    } else {
                        query = "DELETE FROM CIMS.AssignedRoadmap "
                                + "WHERE roadmapid='" + objects[i] + "' AND taskid = '" + objects[0] + "';";
                        executeNonQuery(query);
                    }
                } catch (SQLException e) {
                    System.out.println("assignRoadmap: " + e.getMessage());
                }
            }
            closeConnection();
        }

        return true;
    }
//</editor-fold>
}