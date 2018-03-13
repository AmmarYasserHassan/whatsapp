package commands;

import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

public class RemoveMembersFromAGroupChatCommand implements Command{

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

    public RemoveMembersFromAGroupChatCommand(DBHandler dbHandler, String adminUserNumber, String numberOfMemberToBeRemoved, int groupChatId) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = adminUserNumber;
        this.numberOfMemberToBeRemoved = numberOfMemberToBeRemoved;
        this.groupChatId = groupChatId;
    }

    /**
     * Execute the remove members from a group chat command
     * Check first that this adminUsernumber is an admin of this group chat and remove the members
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {


        String remove_member_to_a_group_chat = "SELECT remove_members_from_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'"+", " + "'" + numberOfMemberToBeRemoved + "'" + ");";
        return this.dbHandler.executeSQLQuery(remove_member_to_a_group_chat);
    }
}

