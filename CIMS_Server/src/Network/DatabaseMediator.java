/**
 *
 */
package Network;

import Field_Operations.Material;
import Field_Operations.Task;
import Field_Operations.Unit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

                    Task t = new Task(taskID, name, urgency, status, location, description);
                    query = "SELECT unitid FROM CIMS.Task_Unit where taskid='" + i + "';";
                    rs = executeQuery(query);
                    while (rs.next()) {
                        t.addUnit(getUnit(rs.getInt("id")));
                    }
                    return t;
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                closeConnection();
            }
        }
        return null;
    }

    private static Unit getUnit(int unitID) {
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
        return null;
    }

    private static Material getMaterial(int materialID) {
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
        return null;
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
}
