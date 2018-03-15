package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler {
    PostgresConnection postgresConnection;

    /**
     * DbHandler constructor
     */
    public DBHandler(PostgresConnection postgresConnection) {
        this.postgresConnection = postgresConnection;
    }

    /**
     * Update user name query
     *
     * @param userNumber
     * @param displayName
     * @return ResultSet containing the new user details
     * @throws SQLException
     */
    public ResultSet updateUserName(String userNumber, String displayName) throws SQLException {
        try {
            // Execute the sql statement
            Statement statement = this.postgresConnection.getConn().createStatement();

            String update_name = "SELECT update_user_name(" + "'" + userNumber + "'" + ", " + "'" + displayName + "'" + ");";
            ResultSet resultSet = statement.executeQuery(update_name);

            // Close the connection
            statement.close();
            resultSet.close();
            postgresConnection.disconnect();
            return resultSet;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    /**
     * Update user status
     *
     * @param userNumber
     * @param updatedStatus
     * @return ResultSet query output
     */
    public ResultSet updateUserStatus(String userNumber, String updatedStatus) {

        try {
            // Execute the sql statement
            Statement statement = this.postgresConnection.getConn().createStatement();

            String updated_status = "SELECT update_user_status(" + "'" + userNumber + "'" + ", " + "'" + updatedStatus + "'" + ");";
            ResultSet resultSet = statement.executeQuery(updated_status);

            // Close the connection
            statement.close();
            resultSet.close();
            postgresConnection.disconnect();
            return resultSet;
        }
        catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * Block a user
     * @param blockerNumber
     * @param blockedNumber
     */
    public ResultSet blockUser(String blockerNumber, String blockedNumber){
        try {
            // Execute the sql statement
            Statement statement = postgresConnection.getConn().createStatement();
            String sqlString = "INSERT INTO BLOCKED VALUES (DEFAULT, " + "'"+blockerNumber+"'" + ", " + "'"+blockedNumber+"'" + ");";
            statement.executeUpdate(sqlString);

            // Close the connection
            statement.close();
            postgresConnection.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Unblock a user
     * @param blockerNumber
     * @param blockedNumber
     */
    public ResultSet unBlockUser(String blockerNumber, String blockedNumber){
        try {
            // Execute the sql statement
            Statement statement = postgresConnection.getConn().createStatement();
            String sqlString = "DELETE FROM blocked WHERE blocker_mobile_number LIKE " + "'"+blockerNumber+"'" +
                               "AND blocked_mobile_number LIKE " + "'"+blockedNumber+"'";
            statement.executeUpdate(sqlString);

            // Close the connection
            statement.close();
            postgresConnection.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Report a user
     * @param reporterNumber
     * @param reportedNumber
     * @return
     */
    public ResultSet reportUser(String reporterNumber, String reportedNumber){
        try {
            // Execute the sql statement
            Statement statement = postgresConnection.getConn().createStatement();
            String sqlString = "INSERT INTO REPORTED VALUES (DEFAULT, " + "'"+reporterNumber+"'" + ", " + "'"+reportedNumber+"'" + ");";
            statement.executeUpdate(sqlString);

            // Close the connection
            statement.close();
            postgresConnection.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
