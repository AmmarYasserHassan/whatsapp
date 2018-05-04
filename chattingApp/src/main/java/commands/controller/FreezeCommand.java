package commands.controller;


import com.google.gson.JsonObject;
import commands.Command;
import database.DBBroker;
import org.json.JSONObject;
import singletons.Mqtt;

public class FreezeCommand implements Command {


    private JsonObject request;

    public FreezeCommand(DBBroker dbBroker, JsonObject request) {
        this.request = request;
    }

    @Override
    public JSONObject call() throws Exception {
        Mqtt.getInstance().setFreeze(true);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 200);
        jsonObject.put("error", false);
        return jsonObject;
    }
}
