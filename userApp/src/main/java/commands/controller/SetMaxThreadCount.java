package commands.controller;

import com.google.gson.JsonObject;
import commands.Command;
import database.DBBroker;
import org.json.JSONObject;
import singletons.ThreadPool;

public class SetMaxThreadCount implements Command {
    JsonObject request;

    public SetMaxThreadCount(DBBroker dbBroker, JsonObject request) {
        this.request = request;
    }

    @Override
    public JSONObject call() throws Exception {
        ThreadPool.getInstance().setThreadPoolMaxNumber(this.request.get("maxThreadCount").getAsInt());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 200);
        jsonObject.put("error", false);
        return jsonObject;
    }
}
