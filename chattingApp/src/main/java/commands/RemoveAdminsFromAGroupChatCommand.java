package commands;


import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;
import sender.MqttSender;

public class RemoveAdminsFromAGroupChatCommand implements Command, Runnable {

    DBHandler dbHandler;
    String adminUserNumber;
    String numberOfMemberToBeRemovedAsAdmin;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */

    public RemoveAdminsFromAGroupChatCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = request.get("adminUserNumber").getAsString();
        this.numberOfMemberToBeRemovedAsAdmin = request.get("numberOfMemberToBeRemovedAsAdmin").getAsString();
        this.groupChatId = request.get("groupChatId").getAsInt();
    }

    /**
     * Execute the remove admins from a group chat command
     * Check first that this adminUsernumber is an admin of this group chat and remove the numbers as admins
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {


        String remove_admin_from_a_group_chat = "SELECT remove_admin_from_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'" + ", " + "'" + numberOfMemberToBeRemovedAsAdmin + "'" + ");";
        return this.dbHandler.executeSQLQuery(remove_admin_from_a_group_chat);
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
