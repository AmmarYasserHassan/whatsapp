package commands;

import com.google.gson.JsonObject;
import database.DBBroker;


import java.sql.SQLException;

import org.json.JSONObject;

public class CreateAGroupChatCommand implements Command {
    DBBroker dbBroker;
    String userNumber;
    String groupChatName;
    String groupDisplayPicture;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     **/
    public CreateAGroupChatCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
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
        String create_group_chat = "SELECT create_group_chat(" + "'" + groupChatName + "'" + ", " + "'" + groupDisplayPicture + "'" + ", " + "'" + userNumber + "'" + ");";

        return this.dbBroker.executeSQLQuery(create_group_chat);
    }

}
