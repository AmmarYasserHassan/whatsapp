package commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DBHandler;
import models.Story;
import org.json.JSONObject;
import sender.MqttSender;

public class AddViewerToStory {
    DBHandler dbHandler;
    String id;
    String viewerMobileNumber;

    public AddViewerToStory(DBHandler handler, JsonObject request) {
        this.dbHandler = handler;
        this.id = request.get("storyId").getAsString();
        this.viewerMobileNumber = request.get("ViewerMobileNumber").getAsString();
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
    public JSONObject execute() {
        JSONObject s = this.dbHandler.update(id,viewerMobileNumber);
        return s;
    }

}

