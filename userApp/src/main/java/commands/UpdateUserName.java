package commands;

import database.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UpdateUserName {
    DBHandler dbHandler;
    String userNumber;
    String displayName;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param userNumber
     * @param displayName
     */

    /**
     * Update User name constructor
     * @param dbHandler
     * @param userNumber
     * @param displayName
     */
    public UpdateUserName(DBHandler dbHandler, String userNumber, String displayName) {
        super();
        this.dbHandler = dbHandler;
        this.userNumber = userNumber;
        this.displayName = displayName;
    }


    /**
     * Execute the update command
     *
     * @return Result Set
     * @throws SQLException
     */
    public ResultSet execute() throws SQLException {
        return this.dbHandler.updateUserName(userNumber, displayName);
    }
}
