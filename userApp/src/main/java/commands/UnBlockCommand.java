package commands;

import com.google.gson.JsonObject;
import database.DBHandler;
import org.json.JSONException;
import org.json.JSONObject;
import sender.MqttSender;

public class UnBlockCommand implements Command, Runnable {
    DBHandler dbHandler;
    String blockerNumber, blockedNumber;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */
    public UnBlockCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
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
            return this.dbHandler.executeSQLQuery(query);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
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
