package commands;

import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import sender.MqttSender;

public class StarAMessageCommand implements Command, Runnable {
    DBHandler dbHandler;
    String userNumber;
    String messageToBeStarred;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     *
     *
     */
    public StarAMessageCommand(DBHandler dbHandler, JsonObject request) {
        super();
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();
        this.messageToBeStarred = request.get("messageToBeStarred").getAsString();
    }


    /**
     * Execute the get messages in a chat or group chat command
     * @return JSONObject, if error == false then data is returned successsfully, if error == true then further info in error_message
     */
    public JSONObject execute() {

        String jsonDocument = "\"{'user_number':" + userNumber+ ",'message':" + messageToBeStarred+" }\"";
        String collectionName = "starred_messages";
        return this.dbHandler.insertMongoDocument(jsonDocument,collectionName);

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