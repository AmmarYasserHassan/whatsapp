package commands;


import com.google.gson.JsonObject;
import database.DBBroker;

import java.sql.SQLException;

import org.json.JSONObject;

public class DeleteAGroupChatCommand implements Command {

    DBBroker dbBroker;
    String adminUserNumber;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */

    public DeleteAGroupChatCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.adminUserNumber = request.get("adminUserNumber").getAsString();
        this.groupChatId = request.get("groupChatId").getAsInt();
    }


    @Override
    public JSONObject call() throws Exception {
        String delete_a_group_chat = "SELECT delete_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + ");";
        return this.dbBroker.executeSQLQuery(delete_a_group_chat);
    }
}
