package commands;

import com.google.gson.JsonObject;
import database.DBBroker;

import java.sql.SQLException;

import org.json.JSONObject;

public class GetAllChatsForAUserCommand implements Command {

    DBBroker dbBroker;
    String userNumber;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */

    public GetAllChatsForAUserCommand(DBBroker dbBroker, JsonObject request) {
        super();
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
    }

    @Override
    public JSONObject call() throws Exception {
        String get_chats = "SELECT get_chats(" + "'" + userNumber + "'" + ");";
        return this.dbBroker.executeSQLQuery(get_chats);
    }
}

