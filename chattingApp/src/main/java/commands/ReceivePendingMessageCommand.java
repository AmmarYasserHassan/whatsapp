package commands;

import org.json.JSONObject;

import com.google.gson.JsonObject;

import database.DBHandler;
import sender.MqttSender;

public class ReceivePendingMessageCommand implements Command, Runnable {
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
    public ReceivePendingMessageCommand(DBHandler dbHandler, JsonObject request) {
        super();
        this.dbHandler = dbHandler;
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
        	return this.dbHandler.findAllMongoDocuments(jsonDocument,"group_chats");
        }else{
        	String jsonDocument = "\"{'chat_id':\"" + chatId+"\","+ "'sender_mobile_number': {$ne:\"" + userNumber +"\"} "+ ","
        	        + "'status': \"" + "sent" +"\"}\"";
        	return this.dbHandler.findAllMongoDocuments(jsonDocument,"chats");
        }
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

