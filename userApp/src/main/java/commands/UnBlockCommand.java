package commands;

import database.PostgresConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UnBlockCommand extends BaseCommand {
    String blockerNumber, blockedNumber;

    /**
     * Constructor
     * @param dbConnection
     * @param blockerNumber
     * @param blockedNumber
     */
    public UnBlockCommand(PostgresConnection dbConnection, String blockerNumber, String blockedNumber){
        super(dbConnection);
        this.blockerNumber = blockerNumber;
        this.blockedNumber = blockedNumber;
    }

    /**
     * Execute the unblock sql query
     * @return
     */
    public ResultSet execute() {
        try {
            // Execute the sql statement
            Statement statement = dbConnection.getConn().createStatement();

            // INSERT INTO blocked VALUES (blocker_mobile_number, blocked_mobile_number);
            String sqlString = "DELETE FROM blocked WHERE blocker_mobile_number LIKE " + "'"+blockerNumber+"'" +
                               "AND blocked_mobile_number LIKE " + "'"+blockedNumber+"'";
            statement.executeUpdate(sqlString);

            // Close the connection
            statement.close();
            dbConnection.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
