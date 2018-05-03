package commands;

import database.DBBroker;

import java.sql.ResultSet;


public class UpdateUserStatus implements Command{
    DBBroker dbBroker;
    String user_number;
    String user_status;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param user_number
     * @param user_status
     */

    public UpdateUserStatus(DBBroker dbBroker, String user_number, String user_status) {
        super();
        this.dbBroker = dbBroker;
        this.user_number = user_number;
        this.user_status = user_status;
    }


    /**
     * Execute the update command
     * @return ResultSet query result
     */
    public ResultSet execute() {
//
        return null;
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}

