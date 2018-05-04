package commands;


import com.google.gson.JsonObject;
import database.DBBroker;

import java.sql.SQLException;

import org.json.JSONObject;

public class RemoveAdminsFromAGroupChatCommand implements Command {

    DBBroker dbBroker;
    String adminUserNumber;
    String numberOfMemberToBeRemovedAsAdmin;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */

    public RemoveAdminsFromAGroupChatCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.adminUserNumber = request.get("adminUserNumber").getAsString();
        this.numberOfMemberToBeRemovedAsAdmin = request.get("numberOfMemberToBeRemovedAsAdmin").getAsString();
        this.groupChatId = request.get("groupChatId").getAsInt();
    }

    @Override
    public JSONObject call() throws Exception {
        String remove_admin_from_a_group_chat = "SELECT remove_admin_from_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'" + ", " + "'" + numberOfMemberToBeRemovedAsAdmin + "'" + ");";
        return this.dbBroker.executeSQLQuery(remove_admin_from_a_group_chat);
    }
}
