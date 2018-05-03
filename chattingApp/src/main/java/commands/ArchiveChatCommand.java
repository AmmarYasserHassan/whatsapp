package commands;


import com.google.gson.JsonObject;
import database.DBBroker;

import java.sql.SQLException;

import org.json.JSONObject;

public class ArchiveChatCommand implements Command {

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
    // String userNumber, int chatId, boolean isGroupChat
    public ArchiveChatCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
        this.chatId = request.get("chatId").getAsInt();
        this.isGroupChat = request.get("isGroupChat").getAsBoolean();
    }


    @Override
    public JSONObject call() throws Exception {
        String archive_chat;
        if (isGroupChat)
            archive_chat = "SELECT archive_group_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'" + ");";
        else
            archive_chat = "SELECT archive_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'" + ");";

        return this.dbBroker.executeSQLQuery(archive_chat);
    }
}
