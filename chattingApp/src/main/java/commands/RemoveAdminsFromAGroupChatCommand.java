package commands;


import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

public class RemoveAdminsFromAGroupChatCommand implements Command{

    DBHandler dbHandler;
    String adminUserNumber;
    String numberOfMemberToBeRemovedAsAdmin;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param adminUserNumber
     * @param numberOfMemberToBeRemovedAsAdmin
     * @param groupChatId
     *
     */

    public RemoveAdminsFromAGroupChatCommand(DBHandler dbHandler, String adminUserNumber, String numberOfMemberToBeRemovedAsAdmin, int groupChatId) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = adminUserNumber;
        this.numberOfMemberToBeRemovedAsAdmin = numberOfMemberToBeRemovedAsAdmin;
        this.groupChatId = groupChatId;
    }

    /**
     * Execute the remove admins from a group chat command
     * Check first that this adminUsernumber is an admin of this group chat and remove the numbers as admins
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {


        String remove_admin_from_a_group_chat = "SELECT remove_admin_from_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'"+", " + "'" + numberOfMemberToBeRemovedAsAdmin + "'" + ");";
        return this.dbHandler.executeSQLQuery(remove_admin_from_a_group_chat);
    }
}
