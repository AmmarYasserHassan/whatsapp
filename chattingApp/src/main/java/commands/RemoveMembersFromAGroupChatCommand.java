package commands;

import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;
import sender.MqttSender;

public class RemoveMembersFromAGroupChatCommand implements Command, Runnable {

    DBHandler dbHandler;
    String adminUserNumber;
    String numberOfMemberToBeRemoved;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */

    public RemoveMembersFromAGroupChatCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
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
        return this.dbHandler.executeSQLQuery(remove_member_to_a_group_chat);
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

