package commands;


import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveAdminsFromAGroupChat {

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

    public RemoveAdminsFromAGroupChat(DBHandler dbHandler, String adminUserNumber, String numberOfMemberToBeRemovedAsAdmin, int groupChatId) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = adminUserNumber;
        this.numberOfMemberToBeRemovedAsAdmin = numberOfMemberToBeRemovedAsAdmin;
        this.groupChatId = groupChatId;
    }

    /**
     * Execute the add members to a group chat command
     * Check first that this adminUsernumber is an admin of this group chat and make the numbers admins
     *
     * @return Result Set
     * @throws SQLException
     */
    public ResultSet execute() throws SQLException {


        String remove_admin_from_a_group_chat = "SELECT remove_admin_from_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'"+", " + "'" + numberOfMemberToBeRemovedAsAdmin + "'" + ");";
        return this.dbHandler.executeSQLQuery(remove_admin_from_a_group_chat);
    }
}
