package commands;

import com.google.gson.JsonObject;
import database.DBBroker;

import java.sql.SQLException;

import org.json.JSONObject;

public class RemoveMembersFromAGroupChatCommand implements Command {

    DBBroker dbBroker;
    String adminUserNumber;
    String numberOfMemberToBeRemoved;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */

    public RemoveMembersFromAGroupChatCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.adminUserNumber = request.get("adminUserNumber").getAsString();
        this.numberOfMemberToBeRemoved = request.get("numberOfMemberToBeRemoved").getAsString();
        this.groupChatId = request.get("groupChatId").getAsInt();
    }

    /**
     * Execute the remove members from a group chat command
     * Check first that this adminUsernumber is an admin of this group chat and remove the members
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String remove_member_to_a_group_chat = "SELECT remove_members_from_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'" + ", " + "'" + numberOfMemberToBeRemoved + "'" + ");";
        return this.dbBroker.executeSQLQuery(remove_member_to_a_group_chat);
    }

}

