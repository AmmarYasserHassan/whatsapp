package commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DBHandler;
import models.Story;
import org.json.JSONObject;
import sender.MqttSender;

public class GetStory implements Command,Runnable {

    DBHandler handler;
    String id;

    public GetStory(DBHandler handler, JsonObject request) {
        this.handler = handler;
        this.id = request.get("storyId").getAsString();
    }

    public JSONObject execute() {
        Story s = this.handler.getStory(id);

        String json = new Gson().toJson(s);

        return new JSONObject(json);
    }

    public void run() {
        JSONObject res = this.execute();
        try {
            MqttSender sender = new MqttSender();
            sender.send(res);
            sender.close();
        }catch (Exception e){

        }
    }
}
