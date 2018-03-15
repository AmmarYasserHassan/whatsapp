package commands;

import database.DBHandler;
import java.sql.ResultSet;


public class UpdateUserStatus extends BaseCommand {
    DBHandler dbHandler;
    String user_number;
    String user_status;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param user_number
     * @param user_status
     */

    public UpdateUserStatus(DBHandler dbHandler, String user_number, String user_status) {
        super();
        this.dbHandler = dbHandler;
        this.user_number = user_number;
        this.user_status = user_status;
    }


    /**
     * Execute the update command
     * @return ResultSet query result
     */
    public ResultSet execute() {
        return this.dbHandler.updateUserStatus(user_number, user_status);
    }
}

