package commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DBBroker;
import models.Story;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetAllStories implements Command {

    DBBroker dbBroker;
    String userNumber;

    public GetAllStories(DBBroker handler, JsonObject request) {
        this.dbBroker = handler;
        this.userNumber = request.get("userNumber").getAsString();
    }

    @Override
    public JSONObject call() throws Exception {
        ArrayList<Story> stories = dbBroker.getAllStroies(userNumber);

        String json = new Gson().toJson(stories);
        JSONObject ob = new JSONObject();
        ob.put("data", new JSONArray(json));
        return ob;
    }
}
