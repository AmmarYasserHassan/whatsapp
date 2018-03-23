package commands;

import org.json.JSONObject;

import com.google.gson.JsonObject;

import database.DBBroker;

public class ReceivePendingMessageCommand implements Command {
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
    public ReceivePendingMessageCommand(DBBroker dbBroker, JsonObject request) {
        super();
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
        this.chatId = request.get("chatId").getAsInt();
        this.isGroupChat = request.get("isGroupChat").getAsBoolean();
    }

    /**
     * Execute the get messages in a chat or group chat command
     * @return JSONObject, if error == false then data is returned successfully, if error == true then further info in error_message
     */
    public JSONObject execute() {  
        if(isGroupChat){
        	String jsonDocument = "\"{'chat_id':\"" + chatId+"\","+ "'sender_mobile_number': {$ne:\"" +userNumber +"\"} "+ ","
        	        +"'participants_status': {$elemMatch:{'mobile_number': \""+userNumber+"\", 'status':\"sent\"}}}\"";
        	return this.dbBroker.findAllMongoDocuments(jsonDocument,"group_chats");
        }else{
        	String jsonDocument = "\"{'chat_id':\"" + chatId+"\","+ "'sender_mobile_number': {$ne:\"" + userNumber +"\"} "+ ","
        	        + "'status': \"" + "sent" +"\"}\"";
        	return this.dbBroker.findAllMongoDocuments(jsonDocument,"chats");
        }
    }
}

