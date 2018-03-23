package commands;

import com.google.gson.JsonObject;
import database.DBBroker;

import java.sql.SQLException;

import org.json.JSONObject;

public class StartAChatCommand implements Command {

    DBBroker dbBroker;
    String firstUserNumber;
    String secondUerNumber;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */

    public StartAChatCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.firstUserNumber = request.get("firstUserNumber").getAsString();
        this.secondUerNumber = request.get("secondUerNumber").getAsString();
    }

    /**
     * Start a chat between two users, insert into the chats table 2 entities
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String start_chat = "SELECT start_chat(" + "'" +firstUserNumber + "'" + ", " + "'" + secondUerNumber + "'" + ");";
        return this.dbBroker.executeSQLQuery(start_chat);
    }

}

