package commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DBBroker;
import models.Story;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetAllStories implements Command {

    DBBroker dbBroker;
    String userNumber;

    public GetAllStories(DBBroker handler, JsonObject request) {
        this.dbBroker = handler;
        this.userNumber = request.get("userNumber").getAsString();
    }

    public JSONObject execute() {
        ArrayList<Story> stories = dbBroker.getAllStroies(userNumber);

        String json = new Gson().toJson(stories);

        return new JSONObject(json);

    }
}
