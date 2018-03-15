package commands;

import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import sender.MqttSender;

public class GetMessagesInAChatForAUserCommand implements Command, Runnable {
    DBHandler dbHandler;
    String userNumber;
    int chatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */
    public GetMessagesInAChatForAUserCommand(DBHandler dbHandler, JsonObject request) {
        super();
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();
        this.chatId = request.get("userNumber").getAsInt();
    }


    /**
     * Execute the get messages in a chat command
     * Check first that this usernumber is a participator in this chat and get the messages
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {

        String get_messages_in_a_chat = "SELECT get_messages_in_a_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'" + ");";
        return this.dbHandler.executeSQLQuery(get_messages_in_a_chat);
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

