package commands;


import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

public class DeleteAGroupChatCommand implements Command{

    DBHandler dbHandler;
    String adminUserNumber;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param adminUserNumber
     * @param groupChatId
     *
     */

    public DeleteAGroupChatCommand(DBHandler dbHandler, String adminUserNumber, int groupChatId) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = adminUserNumber;
        this.groupChatId = groupChatId;
    }

    /**
     * Execute the delete group chat command
     * Check first that this adminUsernumber is an admin of this group chat and delete the group chat
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {


        String delete_a_group_chat = "SELECT delete_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + ");";
        return this.dbHandler.executeSQLQuery(delete_a_group_chat);
    }
}
