package commands;

import com.google.gson.JsonObject;
import database.DBBroker;


import java.sql.SQLException;

import org.json.JSONObject;

public class DeleteAChatCommand implements Command{
    DBBroker dbBroker;
    String userNumber;
    int chatId;
    boolean isGroupChat;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */
    public DeleteAChatCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
        this.chatId = request.get("chatId").getAsInt();
        this.isGroupChat = request.get("isGroupChat").getAsBoolean();
    }


    @Override
    public JSONObject call() throws Exception {
        String delete_chat;
        if (isGroupChat)
            delete_chat = "SELECT delete_group_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'" + ");";
        else
            delete_chat = "SELECT delete_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'" + ");";

        return this.dbBroker.executeSQLQuery(delete_chat);
    }
}
