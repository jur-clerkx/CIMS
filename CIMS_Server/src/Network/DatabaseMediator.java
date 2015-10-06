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
                String query = "SELECT * FROM User WHERE username='" + username + "' AND password='" + password + "';";
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

    private static User getUser(int userID) {
        if (openConnection()) {
            try {
                String query = "SELECT * FROM User WHERE id='" + userID + "';";
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
                    return new Task(taskID, name, urgency, status, location, description);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                closeConnection();
            }
        }
        return null;
    }

    public static Task getTaskLists(Task t) {
        if (openConnection()) {
            try {
                String query = "SELECT unitid FROM CIMS.Task_Unit where taskid='" + t.getTaskID() + "';";
                ResultSet rs = executeQuery(query);
                while (rs.next()) {
                    t.addUnit(getUnit(rs.getInt("id")));
                }

                query = "SELECT id FROM CIMS.Progress where taskid='" + t.getTaskID() + "';";
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

    public static boolean updateTaskStatus(Object o) {
        if (o instanceof Object[]) {
            Object[] objects = (Object[]) o;
            if (openConnection()) {
                try {
                    String query = "UPDATE CIMS.Task SET status='" + objects[1] + "' WHERE id='" + objects[0] + "';";
                    executeNonQuery(query);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                closeConnection();
                return true;
            }
        }
        return false;
    }

    public static Unit getUnit(Object o) {
        if (o instanceof Integer) {
            int unitID = (Integer) o;
            if (openConnection()) {
                try {
                    String query = "SELECT * FROM unit WHERE id='" + unitID + "';";
                    ResultSet rs = executeQuery(query);
                    rs.next();

                    String name = rs.getString("name");
                    String shift = rs.getString("shift");
                    String shiftday = rs.getString("shiftday");
                    String description = rs.getString("description");

                    Unit u = new Unit(unitID, name, description, shiftday + ", " + shift);
                    return u;
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                closeConnection();
            }
        }
        return null;
    }

    public static Unit getUnitLists(Unit u) {
        if (openConnection()) {
            try {
                String query = "SELECT unitid FROM CIMS.Task_Unit where taskid='" + u.getUnitID() + "';";
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

    public static Material getMaterial(Object o) {
        if (o instanceof Integer) {
            int materialID = (Integer) o;
            if (openConnection()) {
                try {
                    String query = "SELECT * FROM material WHERE id='" + materialID + "';";
                    ResultSet rs = executeQuery(query);
                    rs.next();

                    String name = rs.getString("name");
                    String state = rs.getString("state");
                    int availibility = rs.getInt("availibility");

                    return new Material(materialID, name, state, getUser(availibility));
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                closeConnection();
            }
        }
        return null;
    }

    public static Vehicle getVehicle(Object o) {
        if (o instanceof Integer) {
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

                    return new Vehicle(vehicleID, name, licence, state, getUser(availibility));
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                closeConnection();
            }
        }
        return null;
    }

    public static Progress getProgress(int progresID) {
        if (openConnection()) {
            try {
                String query = "SELECT * FROM progress WHERE id='" + progresID + "';";
                ResultSet rs = executeQuery(query);
                rs.next();

                int userID = rs.getInt("userid");
                int taskID = rs.getInt("taskid");
                String message = rs.getString("message");

                return new Progress(progresID, getUser(userID), getTask(taskID), message);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            closeConnection();
        }
        return null;
    }
}
