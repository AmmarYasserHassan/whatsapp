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
     * Update user anme query
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;

    }
}
