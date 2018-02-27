package commands;

import database.DBHandler;
import database.PostgresConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportCommand{
    DBHandler dbHandler;
    String reporterNumber, reportedNumber;

    /**
     * The constructor
     * @param dbHandler
     * @param reporterNumber
     * @param reportedNumber
     */
    public ReportCommand(DBHandler dbHandler, String reporterNumber, String reportedNumber){
        super();
        this.dbHandler = dbHandler;
        this.reporterNumber = reporterNumber;
        this.reportedNumber = reportedNumber;
    }

    /**
     * Execute the report sql query
     * @return
     */
    public ResultSet execute() {
        return this.dbHandler.reportUser(reporterNumber, reportedNumber);
    }
}
