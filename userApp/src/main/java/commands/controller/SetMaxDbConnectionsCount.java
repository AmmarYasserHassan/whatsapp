package commands.controller;


import com.google.gson.JsonObject;
import commands.Command;
import database.DBBroker;
import org.json.JSONObject;

public class SetMaxDbConnectionsCount implements Command {

    public SetMaxDbConnectionsCount(DBBroker dbBroker, JsonObject request) {

    }

    @Override
    public JSONObject call() throws Exception {
        return null;
    }
}
