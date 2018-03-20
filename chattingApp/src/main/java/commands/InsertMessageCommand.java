package commands;

import java.sql.SQLException;

import org.json.JSONObject;

import com.google.gson.JsonObject;

import database.DBHandler;
import sender.MqttSender;

public class InsertMessageCommand  implements Command, Runnable {
	
    DBHandler dbHandler;
    String messageDocument;
    boolean isGroupChat;
    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */

    public InsertMessageCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
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
        
    	return this.dbHandler.insertMongoDocument(messageDocument, collectionName);
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
