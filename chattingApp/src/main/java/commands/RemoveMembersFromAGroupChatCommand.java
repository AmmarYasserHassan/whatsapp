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

    @Override
    public JSONObject call() throws Exception {
        String remove_member_to_a_group_chat = "SELECT remove_members_from_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'" + ", " + "'" + numberOfMemberToBeRemoved + "'" + ");";
        return this.dbBroker.executeSQLQuery(remove_member_to_a_group_chat);
    }
}

