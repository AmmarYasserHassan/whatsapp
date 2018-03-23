package commands;

import com.google.gson.JsonObject;
import database.DBBroker;
import org.json.JSONException;
import org.json.JSONObject;

public class UnBlockCommand implements Command {
    DBBroker dbBroker;
    String blockerNumber, blockedNumber;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */
    public UnBlockCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.blockerNumber = request.get("blockerNumber").getAsString();
        this.blockedNumber = request.get("blockedNumber").getAsString();
    }

    /**
     * Execute the unblock command
     * @return
     */
    public JSONObject execute() {
        String query = "DELETE FROM blocked WHERE blocker_mobile_number LIKE " + "'"+blockerNumber+"'" +
                       "AND blocked_mobile_number LIKE " + "'"+blockedNumber+"'";
        try {
            return this.dbBroker.executeSQLQuery(query);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
