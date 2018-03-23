package commands;


import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import sender.MqttSender;

public class LeaveAGroupChatCommand implements Command, Runnable {

    DBHandler dbHandler;
    String userNumber;
    String groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */

    public LeaveAGroupChatCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();
        this.groupChatId = request.get("groupChatId").getAsString();
    }

    /**
     * Start a chat between two users, insert into the chats table 2 entities
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String leave_group_chat = "SELECT start_chat(" + "'" +userNumber + "'" + ", " + "'" + groupChatId + "'" + ");";
        return this.dbHandler.executeSQLQuery(leave_group_chat);
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

