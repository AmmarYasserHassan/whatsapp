package commands;


import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;
import sender.MqttSender;

public class ArchiveChatCommand implements Command, Runnable {

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
    // String userNumber, int chatId, boolean isGroupChat
    public ArchiveChatCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();
        this.chatId = request.get("chatId").getAsInt();
        this.isGroupChat = request.get("isGroupChat").getAsBoolean();
    }

    /**
     * Execute the archive chat ommand
     * check if it's a group chat or a normal chat then enter the table and archive it
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String archive_chat;
        if (isGroupChat)
            archive_chat = "SELECT archive_group_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'" + ");";
        else
            archive_chat = "SELECT archive_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'" + ");";

        return this.dbHandler.executeSQLQuery(archive_chat);
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
