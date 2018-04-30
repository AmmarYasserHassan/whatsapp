package commands;

import java.sql.SQLException;

import org.json.JSONObject;

import com.google.gson.JsonObject;

import database.DBBroker;

public class InsertMessageCommand  implements Command {
	
    DBBroker dbBroker;
    String messageDocument;
    boolean isGroupChat;
    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */

    public InsertMessageCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.messageDocument = request.get("messageDocument").getAsString();
        this.isGroupChat = request.get("isGroupChat").getAsBoolean();

    }

    /**
     * Start a chat between two users, insert into the chats table 2 entities
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String collectionName;
        if(isGroupChat)
            collectionName = "group_chats";
        else
            collectionName = "chats";
        return this.dbBroker.insertMongoDocument(messageDocument, collectionName);
    }


}
