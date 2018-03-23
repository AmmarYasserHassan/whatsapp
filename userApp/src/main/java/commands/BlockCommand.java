package commands;


import com.google.gson.JsonObject;
import database.DBBroker;

import org.json.JSONException;
import org.json.JSONObject;

public class BlockCommand implements Command {
    DBBroker dbBroker;
    String blockerNumber, blockedNumber;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */
    public BlockCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.blockerNumber = request.get("blockerNumber").getAsString();
        this.blockedNumber = request.get("blockedNumber").getAsString();
    }

    /**
     * Execute the block command
     * @return
     */
    public JSONObject execute() {
        String query = "INSERT INTO BLOCKED VALUES (DEFAULT, " + "'"+blockerNumber+"'" + ", " + "'"+blockedNumber+"'" + ");";
        try {
            return this.dbBroker.executeSQLQuery(query);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
