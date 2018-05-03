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


    @Override
    public JSONObject call() throws Exception {
        String create_group_chat = "SELECT create_group_chat(" + "'" + groupChatName + "'" + ", " + "'" + groupDisplayPicture + "'" + ", " + "'" + userNumber + "'" + ");";

        return this.dbBroker.executeSQLQuery(create_group_chat);
    }
}
