package commands;

import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoveMembersFromAGroupChat {

    DBHandler dbHandler;
    String adminUserNumber;
    String numberOfMemberToBeRemoved;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param adminUserNumber
     * @param numberOfMemberToBeRemoved
     * @param groupChatId
     *
     */

    public RemoveMembersFromAGroupChat(DBHandler dbHandler, String adminUserNumber, String numberOfMemberToBeRemoved, int groupChatId) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = adminUserNumber;
        this.numberOfMemberToBeRemoved = numberOfMemberToBeRemoved;
        this.groupChatId = groupChatId;
    }

    /**
     * Execute the add members to a group chat command
     * Check first that this adminUsernumber is an admin of this group chat and removed the members
     *
     * @return Result Set
     * @throws SQLException
     */
    public ResultSet execute() throws SQLException {


        String remove_member_to_a_group_chat = "SELECT remove_members_from_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'"+", " + "'" + numberOfMemberToBeRemoved + "'" + ");";
        return this.dbHandler.executeSQLQuery(remove_member_to_a_group_chat);
    }
}

