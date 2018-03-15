package commands;

import com.google.gson.JsonObject;
import database.DBHandler;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;
import sender.MqttSender;

public class CreateAGroupChatCommand implements Command, Runnable {
    DBHandler dbHandler;
    String userNumber;
    String groupChatName;
    String groupDisplayPicture;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     **/
    public CreateAGroupChatCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();
        this.groupChatName = request.get("groupChatName").getAsString();
        this.groupDisplayPicture = request.get("groupDisplayPicture").getAsString();
    }


    /**
     * Execute the create group chat command
     * Create a group chat with the given name and display picture, add the usernumber to it's members and make the number an admin
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String create_group_chat = "SELECT create_gorup_chat(" + "'" + groupChatName + "'" + ", " + "'" + groupDisplayPicture + "'" + ", " + "'" + userNumber + "'" + ");";

        return this.dbHandler.executeSQLQuery(create_group_chat);
    }

    public void run() {
        JSONObject res = this.execute();
        try {
            MqttSender sender = new MqttSender();
            sender.send(res);
            sender.close();
        }catch (Exception e){

        }
    }
}
