package commands;


import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import sender.MqttSender;

public class BlockCommand implements Command, Runnable {
    DBHandler dbHandler;
    String blockerNumber, blockedNumber;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */
    public BlockCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
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
