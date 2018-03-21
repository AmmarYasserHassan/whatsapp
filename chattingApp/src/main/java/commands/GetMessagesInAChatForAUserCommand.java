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
    boolean isGroupChat;

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

        return this.dbHandler.findAllMongoDocuments(jsonDocument,collectionName);


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

