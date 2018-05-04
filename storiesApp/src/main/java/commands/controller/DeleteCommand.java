package commands.controller;

import com.google.gson.JsonObject;
import commands.Command;
import config.ApplicationProperties;
import database.DBBroker;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DeleteCommand implements Command {

    private JsonObject request;

    public DeleteCommand(DBBroker dbBroker, JsonObject request) {
        this.request = request;
    }

    @Override
    public JSONObject call() throws Exception {
        Path path = Paths.get(ApplicationProperties.getProperty("classPathInTarget") + "\\" + this.request.get("className").getAsString()+".class");
        Files.deleteIfExists(path);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 200);
        jsonObject.put("error", false);
        return  jsonObject;
    }
}
