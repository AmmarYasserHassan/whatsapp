package commands;

import com.google.gson.JsonObject;
import database.DBHandler;
import org.json.JSONException;
import org.json.JSONObject;
import sender.MqttSender;

public class ReportCommand implements Command, Runnable{

    DBHandler dbHandler;
    String reporterNumber, reportedNumber;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */
    public ReportCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.reporterNumber = request.get("reporterNumber").getAsString();
        this.reportedNumber = request.get("reportedNumber").getAsString();
    }

    /**
     * Execute the report command
     * @return
     */
    public JSONObject execute() {
        String query = "INSERT INTO REPORTED VALUES (DEFAULT, " + "'"+reporterNumber+"'" + ", " + "'"+reportedNumber+"'" + ");";
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
        }
        catch (Exception e){

        }
    }

}
