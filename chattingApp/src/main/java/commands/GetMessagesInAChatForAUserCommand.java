package commands;

import com.google.gson.JsonObject;
import database.DBBroker;

import org.json.JSONObject;

public class GetMessagesInAChatForAUserCommand implements Command {
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
    public GetMessagesInAChatForAUserCommand(DBBroker dbBroker, JsonObject request) {
        super();
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
        this.chatId = request.get("chatId").getAsInt();
        this.isGroupChat = request.get("isGroupChat").getAsBoolean();
    }


    /**
     * Execute the get messages in a chat or group chat command
     * @return JSONObject, if error == false then data is returned successsfully, if error == true then further info in error_message
     */
    public JSONObject execute() {

        String jsonDocument = "\"{'chat_id':" + chatId+ " }\"";
        String collectionName;
        if(isGroupChat)
            collectionName = "group_chats";
        else
            collectionName = "chats";

        return this.dbBroker.findAllMongoDocuments(jsonDocument,collectionName);


    }

}

