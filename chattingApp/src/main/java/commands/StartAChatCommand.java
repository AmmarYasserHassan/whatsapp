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

    @Override
    public JSONObject call() throws Exception {
        String start_chat = "SELECT start_chat(" + "'" + firstUserNumber + "'" + ", " + "'" + secondUerNumber + "'" + ");";
        return this.dbBroker.executeSQLQuery(start_chat);
    }
}

