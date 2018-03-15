package commands;

import database.DBHandler;
import database.PostgresConnection;
import java.sql.ResultSet;

public class UnBlockCommand{
    DBHandler dbHandler;
    String blockerNumber, blockedNumber;

    /**
     * The constructor
     * @param dbHandler
     * @param blockerNumber
     * @param blockedNumber
     */
    public UnBlockCommand(DBHandler dbHandler, String blockerNumber, String blockedNumber){
        super();
        this.dbHandler = dbHandler;
        this.blockerNumber = blockerNumber;
        this.blockedNumber = blockedNumber;
    }

    /**
     * Execute the unblock command
     * @return
     */
    public ResultSet execute() {
        return this.dbHandler.unBlockUser(blockerNumber, blockedNumber);
    }

}
