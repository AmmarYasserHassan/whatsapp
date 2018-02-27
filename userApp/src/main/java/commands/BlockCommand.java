package commands;

import database.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlockCommand{
    DBHandler dbHandler;
    String blockerNumber, blockedNumber;

    /**
     * Constructor
     * @param dbHandler
     * @param blockerNumber
     * @param blockedNumber
     */
    public BlockCommand(DBHandler dbHandler, String blockerNumber, String blockedNumber){
        this.dbHandler = dbHandler;
        this.blockerNumber = blockerNumber;
        this.blockedNumber = blockedNumber;
    }

    /**
     * Execute the block command
     * @return
     */
    public ResultSet execute() {
        return this.dbHandler.blockUser(blockerNumber, blockedNumber);
    }
}
