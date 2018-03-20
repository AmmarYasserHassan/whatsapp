package commands;

import java.sql.SQLException;

import org.json.JSONObject;

import com.google.gson.JsonObject;

import database.DBHandler;
import sender.MqttSender;

public class UpdateMessageStatusCommand implements Command, Runnable {
    DBHandler dbHandler;
    String userNumber;
    String messageId;
    String statusUpdate;
    int chatId;
    boolean isGroupChat;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */
    public UpdateMessageStatusCommand(DBHandler dbHandler, JsonObject request) {
        super();
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();
        this.messageId = request.get("messageId").getAsString();
        this.statusUpdate = request.get("statusUpdate").getAsString();
        this.chatId = request.get("chatId").getAsInt();
        this.isGroupChat = request.get("isGroupChat").getAsBoolean();
    }

    /**
     * Execute the get messages in a chat or group chat command
     * @return JSONObject, if error == false then data is returned successfully, if error == true then further info in error_message
     */
    public JSONObject execute() {  
        if(isGroupChat){
        	String jsonFindDocument = "\"{_id:ObjectId("+messageId+"),"
        			+ "'participants_status':{$elemMatch:{'mobile_number':"+userNumber+"}}";
        	String jsonUpdateDocument = "\"{$set:{'participants_status.$.status':\""+ statusUpdate + "\"}}\"";
        	
        	return this.dbHandler.updateMongoDocument(jsonFindDocument, jsonUpdateDocument, "group_chats");
        	
        }else{
        	String jsonFindDocument = "\"{_id:ObjectId("+messageId+")\"";
        	String jsonUpdateDocument = "\"{$set: {'status':\""+ statusUpdate +"\"}}\"";
        	System.out.println(jsonUpdateDocument);
        	return this.dbHandler.updateMongoDocument(jsonFindDocument, jsonUpdateDocument, "chats");
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
//    public static void main(String[] args) {
//    	String jsonUpdateDocument = "\"{'participants_status': {$elemMatch:{ 'mobile_number': \""+
//    	        0+"\", status:\""+ 0 + "\" }}}\"";
//    	System.out.println(jsonUpdateDocument);
//	}
}

