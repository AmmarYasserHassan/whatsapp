package commands;


import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddAdminsToAGroupChatCommand {

    DBHandler dbHandler;
    String adminUserNumber;
    String numberOfMemberToBeMadeAdmin;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param adminUserNumber
     * @param numberOfMemberToBeMadeAdmin
     * @param groupChatId
     *
     */

    public AddAdminsToAGroupChatCommand(DBHandler dbHandler, String adminUserNumber, String numberOfMemberToBeMadeAdmin, int groupChatId) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = adminUserNumber;
        this.numberOfMemberToBeMadeAdmin = numberOfMemberToBeMadeAdmin;
        this.groupChatId = groupChatId;
    }

    /**
     * Execute the add admins to a group chat command
     * Check first that this adminUsernumber is an admin of this group chat and make the numbers admins
     *
     * @return Result Set
     * @throws SQLException
     */
    public ResultSet execute() throws SQLException {


        String add_admin_to_a_group_chat = "SELECT add_admins_to_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'"+", " + "'" + numberOfMemberToBeMadeAdmin + "'" + ");";
        return this.dbHandler.executeSQLQuery(add_admin_to_a_group_chat);
    }
}
