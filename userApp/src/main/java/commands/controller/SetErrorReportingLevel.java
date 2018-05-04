package commands.controller;

import com.google.gson.JsonObject;
import commands.Command;
import database.DBBroker;
import org.json.JSONObject;
import singletons.Logger;

public class SetErrorReportingLevel implements Command {

    private JsonObject request;

    public SetErrorReportingLevel(DBBroker dbBroker, JsonObject request) {
        this.request = request;
    }

    @Override
    public JSONObject call() throws Exception {
        Logger.Level level = null;
        switch (this.request.get("loggingLevel").getAsString()) {
            case "INFO":
                level = Logger.Level.INFO;
                break;
            case "DEBUG":
                level = Logger.Level.DEBUG;
                break;
            case "ERROR":
                level = Logger.Level.ERROR;
                break;
            case "TRACE":
                level = Logger.Level.TRACE;
                break;
            case "WARN":
                level = Logger.Level.WARN;
                break;
            default:
                level = Logger.Level.INFO;
        }
        Logger.getInstance().setLoggingLevel(level);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 200);
        jsonObject.put("error", false);
        return jsonObject;
    }
}
