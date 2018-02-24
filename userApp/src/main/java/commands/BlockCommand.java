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
            String sqlString = "SELECT * FROM block_user(" + "\""+blockerNumber+"\"" + ", " + "\""+blockedNumber+"\"" + ");";
            ResultSet resultSet = statement.executeQuery(sqlString);

            // Close the connection
            statement.close();
            resultSet.close();
            dbConnection.disconnect();
            return resultSet;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
