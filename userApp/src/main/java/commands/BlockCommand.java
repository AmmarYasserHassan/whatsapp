package commands;

import database.PostgresConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlockCommand extends BaseCommand{
    String blockerNumber, blockedNumber;

    /**
     * Constructor
     * @param dbConnection
     * @param blockerNumber
     * @param blockedNumber
     */
    public BlockCommand(PostgresConnection dbConnection, String blockerNumber, String blockedNumber){
        super(dbConnection);
        this.blockerNumber = blockerNumber;
        this.blockedNumber = blockedNumber;
    }

    /**
     * Execute the block sql query
     * @return
     */
    public ResultSet execute() {
        try {
            // Execute the sql statement
            Statement statement = dbConnection.getConn().createStatement();

            // INSERT INTO blocked VALUES (blocker_mobile_number, blocked_mobile_number);
            String sqlString = "INSERT INTO BLOCKED VALUES (DEFAULT, " + "'"+blockerNumber+"'" + ", " + "'"+blockedNumber+"'" + ");";
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
