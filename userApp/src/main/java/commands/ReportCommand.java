package commands;

import com.google.gson.JsonObject;
import database.DBBroker;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;

public class ReportCommand implements Command{

    DBBroker dbBroker;
    String reporterNumber, reportedNumber;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */
    public ReportCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.reporterNumber = request.get("reporterNumber").getAsString();
        this.reportedNumber = request.get("reportedNumber").getAsString();
    }

    @Override
    public JSONObject call() throws Exception {
        String query = "INSERT INTO REPORTED VALUES (DEFAULT, " + "'"+reporterNumber+"'" + ", " + "'"+reportedNumber+"'" + ");";
        try {
            return this.dbBroker.executeSQLQuery(query);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
