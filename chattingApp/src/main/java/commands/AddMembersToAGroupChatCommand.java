package commands;

import com.google.gson.JsonObject;
import database.DBBroker;

import java.sql.SQLException;

import org.json.JSONObject;

public class AddMembersToAGroupChatCommand implements Command {

    DBBroker dbBroker;
    String adminUserNumber;
    String numberOfMemberToBeAdded;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */
// String adminUserNumber, String numberOfMemberToBeAdded, int groupChatId
    public AddMembersToAGroupChatCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.adminUserNumber = request.get("adminUserNumber").getAsString();
        this.numberOfMemberToBeAdded = request.get("numberOfMemberToBeAdded").getAsString();
        this.groupChatId = request.get("groupChatId").getAsInt();
    }

    @Override
    public JSONObject call() throws Exception {
        String add_member_to_a_group_chat = "SELECT add_members_to_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'" + ", " + "'" + numberOfMemberToBeAdded + "'" + ");";
        return this.dbBroker.executeSQLQuery(add_member_to_a_group_chat);
    }
}
