package commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DBBroker;
import models.Story;
import org.json.JSONObject;

public class GetStory implements Command {

    DBBroker handler;
    String id;

    public GetStory(DBBroker handler, JsonObject request) {
        this.handler = handler;
        this.id = request.get("storyId").getAsString();
    }

    public JSONObject execute() {
        Story s = this.handler.getStory(id);

        String json = new Gson().toJson(s);

        return new JSONObject(json);
    }
}
