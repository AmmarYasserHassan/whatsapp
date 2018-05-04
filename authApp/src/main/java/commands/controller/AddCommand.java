package commands.controller;


import com.google.gson.JsonObject;
import commands.Command;
import config.ApplicationProperties;
import database.DBBroker;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.*;

public class AddCommand implements Command {

    JsonObject request;

    public AddCommand(DBBroker dbBroker, JsonObject request) throws IOException {
        this.request = request;
    }

    @Override
    public JSONObject call() throws Exception {
        byte[] bytes = request.get("classFileContent").getAsString().replaceAll("/", "").getBytes();
        String folderPath = ApplicationProperties.getProperty("classPathInTarget") + "\\" + request.get("className").getAsString() + ".class";
        Path path = Paths.get(folderPath);
        Files.deleteIfExists(path);
        Files.write(path, bytes, new OpenOption[]{StandardOpenOption.CREATE_NEW});
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", 200);
        jsonObject.put("error", false);
        return jsonObject;
    }
}
