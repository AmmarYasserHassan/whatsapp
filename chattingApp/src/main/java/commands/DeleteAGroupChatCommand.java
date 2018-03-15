package commands;


import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;
import sender.MqttSender;

public class DeleteAGroupChatCommand implements Command, Runnable {

    DBHandler dbHandler;
    String adminUserNumber;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */

    public DeleteAGroupChatCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = request.get("adminUserNumber").getAsString();
        this.groupChatId = request.get("groupChatId").getAsInt();
    }

    /**
     * Execute the delete group chat command
     * Check first that this adminUsernumber is an admin of this group chat and delete the group chat
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String delete_a_group_chat = "SELECT delete_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + ");";
        return this.dbHandler.executeSQLQuery(delete_a_group_chat);
    }

    public void run() {
        JSONObject res = this.execute();
        try {
            MqttSender sender = new MqttSender();
            sender.send(res);
            sender.close();
        } catch (Exception e) {

        }
    }
}
