package commands;

import com.google.gson.JsonObject;
import database.DBHandler;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;
import sender.MqttSender;

public class DeleteChatCommand implements Command, Runnable {
    DBHandler dbHandler;
    String userNumber;
    int chatId;
    boolean isGroupChat;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */
    public DeleteChatCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();
        this.chatId = request.get("userNumber").getAsInt();
        this.isGroupChat = request.get("isGroupChat").getAsBoolean();
    }

    /**
     * Execute the delete chat ommand
     * check if it's a group chat or a normal chat then enter the table and delete it
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String delete_chat;
        if (isGroupChat)
            delete_chat = "SELECT delete_group_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'" + ");";
        else
            delete_chat = "SELECT delete_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'" + ");";

        return this.dbHandler.executeSQLQuery(delete_chat);
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
