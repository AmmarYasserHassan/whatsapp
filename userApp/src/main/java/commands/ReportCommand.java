package commands;

import database.PostgresConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportCommand extends BaseCommand{
    String reporterNumber, reportedNumber;

    /**
     * The constructor
     * @param dbConnection
     * @param reporterNumber
     * @param reportedNumber
     */
    public ReportCommand(PostgresConnection dbConnection, String reporterNumber, String reportedNumber){
        super(dbConnection);
        this.reporterNumber = reporterNumber;
        this.reportedNumber = reportedNumber;
    }

    /**
     * Execute the report sql query
     * @return
     */
    public ResultSet execute() {
        try {
            // Execute the sql statement
            Statement statement = dbConnection.getConn().createStatement();

            // INSERT INTO blocked VALUES (blocker_mobile_number, blocked_mobile_number);
            String sqlString = "INSERT INTO REPORTED VALUES (DEFAULT, " + "'"+reporterNumber+"'" + ", " + "'"+reportedNumber+"'" + ");";
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
