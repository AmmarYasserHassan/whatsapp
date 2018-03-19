package commands;

import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;
import sender.MqttSender;

public class AddMembersToAGroupChatCommand implements Command, Runnable {

    DBHandler dbHandler;
    String adminUserNumber;
    String numberOfMemberToBeAdded;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */
// String adminUserNumber, String numberOfMemberToBeAdded, int groupChatId
    public AddMembersToAGroupChatCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = request.get("adminUserNumber").getAsString();
        this.numberOfMemberToBeAdded = request.get("numberOfMemberToBeAdded").getAsString();
        this.groupChatId = request.get("groupChatId").getAsInt();
    }

    /**
     * Execute the add members to a group chat command
     * Check first that this adminUsernumber is an admin of this group chat and add the members
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String add_member_to_a_group_chat = "SELECT add_members_to_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'" + ", " + "'" + numberOfMemberToBeAdded + "'" + ");";
        return this.dbHandler.executeSQLQuery(add_member_to_a_group_chat);
    }

    public void run() {
        JSONObject res = this.execute();
        try {
            MqttSender sender = new MqttSender();
            sender.send(res);
            sender.close();
        }catch (Exception e){

        }
    }
}
